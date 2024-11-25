package dasniko.keycloak.authenticator;

import dasniko.keycloak.authenticator.gateway.SmsServiceFactory;
import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.common.util.SecretGenerator;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.sessions.AuthenticationSessionModel;
import org.keycloak.theme.Theme;

import java.util.Locale;

public class SmsAuthenticator implements Authenticator {

	private static final String MOBILE_NUMBER_FIELD = "mobile_number";

	@Override
	public void authenticate(AuthenticationFlowContext context) {
		// `selectedMethod` değerini kontrol edin
		String selectedMethod = context.getAuthenticationSession().getAuthNote("selectedMethod");
		if (!"sms".equalsIgnoreCase(selectedMethod)) {
			// Seçilen yöntem "sms" değilse, adımı geç ve diğer doğrulama seçeneklerine devam et
			context.attempted();
			return;
		}

		AuthenticatorConfigModel config = context.getAuthenticatorConfig();

		// Konfigürasyon eksikse hata göster
		if (config == null || config.getConfig() == null) {
			context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
				context.form().setError("Authenticator configuration is missing or incomplete.")
					.createErrorPage(Response.Status.INTERNAL_SERVER_ERROR));
			return;
		}

		KeycloakSession session = context.getSession();
		UserModel user = context.getUser();
		String mobileNumber = user.getFirstAttribute(MOBILE_NUMBER_FIELD);

		// Kullanıcının telefon numarası yoksa hata göster
		if (mobileNumber == null || mobileNumber.isEmpty()) {
			context.failureChallenge(AuthenticationFlowError.INVALID_USER,
				context.form().setError("User does not have a mobile number.")
					.createErrorPage(Response.Status.BAD_REQUEST));
			return;
		}

		// OTP kodu oluştur
		int length = Integer.parseInt(config.getConfig().get(SmsConstants.CODE_LENGTH));
		int ttl = Integer.parseInt(config.getConfig().get(SmsConstants.CODE_TTL));
		String code = SecretGenerator.getInstance().randomString(length, SecretGenerator.DIGITS);

		// OTP kodunu oturum notlarında sakla
		AuthenticationSessionModel authSession = context.getAuthenticationSession();
		authSession.setAuthNote(SmsConstants.CODE, code);
		authSession.setAuthNote(SmsConstants.CODE_TTL, Long.toString(System.currentTimeMillis() + (ttl * 1000L)));

		try {
			// SMS metni oluştur ve gönder
			Theme theme = session.theme().getTheme(Theme.Type.LOGIN);
			Locale locale = session.getContext().resolveLocale(user);
			String smsAuthText = theme.getMessages(locale).getProperty("smsAuthText");
			String smsText = String.format(smsAuthText, code, Math.floorDiv(ttl, 60));

			SmsServiceFactory.get(config.getConfig()).send(mobileNumber, smsText);

			// Kullanıcıyı login-sms.ftl formuna yönlendir
			context.challenge(context.form().createForm("login-sms.ftl"));
		} catch (Exception e) {
			context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
				context.form().setError("smsAuthSmsNotSent", e.getMessage())
					.createErrorPage(Response.Status.INTERNAL_SERVER_ERROR));
		}
	}

	@Override
	public void action(AuthenticationFlowContext context) {
		String enteredCode = context.getHttpRequest().getDecodedFormParameters().getFirst(SmsConstants.CODE);

		// Log entered code for debugging
		System.out.println("Entered code: " + enteredCode);

		if (enteredCode == null || enteredCode.isEmpty()) {
			context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS,
				context.form().setError("smsAuthCodeInvalid").createErrorPage(Response.Status.BAD_REQUEST));
			return;
		}

		AuthenticationSessionModel authSession = context.getAuthenticationSession();
		String code = authSession.getAuthNote(SmsConstants.CODE);
		String ttl = authSession.getAuthNote(SmsConstants.CODE_TTL);

		// Log session code and TTL for debugging
		System.out.println("Session code: " + code);
		System.out.println("Session TTL: " + ttl);

		if (code == null || ttl == null) {
			context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
				context.form().createErrorPage(Response.Status.INTERNAL_SERVER_ERROR));
			return;
		}

		if (!enteredCode.equals(code)) {
			context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS,
				context.form().setError("smsAuthCodeInvalid").createErrorPage(Response.Status.BAD_REQUEST));
			return;
		}

		if (Long.parseLong(ttl) < System.currentTimeMillis()) {
			context.failureChallenge(AuthenticationFlowError.EXPIRED_CODE,
				context.form().setError("smsAuthCodeExpired").createErrorPage(Response.Status.BAD_REQUEST));
			return;
		}

		context.success();
	}

	@Override
	public boolean requiresUser() {
		return true;
	}

	@Override
	public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
		return user.getFirstAttribute(MOBILE_NUMBER_FIELD) != null;
	}

	@Override
	public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
		user.addRequiredAction("mobile-number-ra");
	}

	@Override
	public void close() {
	}
}
