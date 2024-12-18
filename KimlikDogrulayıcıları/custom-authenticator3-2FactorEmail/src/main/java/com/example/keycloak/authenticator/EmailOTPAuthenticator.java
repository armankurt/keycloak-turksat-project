package com.example.keycloak.authenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.UserModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.services.messages.Messages;
import org.keycloak.services.validation.Validation;
import org.keycloak.email.EmailSenderProvider;
import org.jboss.logging.Logger;

import java.util.Map;
import jakarta.ws.rs.core.Response;
import java.security.SecureRandom;

public class EmailOTPAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(EmailOTPAuthenticator.class);
    private static final int OTP_LENGTH = 5;

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        // `selectedMethod` değerini kontrol edin
        String selectedMethod = context.getAuthenticationSession().getAuthNote("selectedMethod");

        if (!"email".equalsIgnoreCase(selectedMethod)) {
            // Seçilen yöntem "email" değilse, adımı geç ve diğer doğrulama seçeneklerine devam et
            logger.info("Email authentication not selected. Skipping this authenticator.");
            context.attempted();
            return;
        }

        UserModel user = context.getUser();
        String email = user.getEmail();
        logger.info("authenticate method called for user: " + user.getUsername());

        if (Validation.isBlank(email)) {
            logger.warn("User email is blank or null, failing authentication.");
            context.failureChallenge(AuthenticationFlowError.INVALID_USER, context.form()
                    .setError(Messages.INVALID_USER)
                    .createErrorPage(Response.Status.BAD_REQUEST));
            return;
        }

        // OTP kodunu oluştur
        String otpCode = generateOTP();
        logger.info("Generated OTP: " + otpCode);

        // OTP'yi oturum notunda sakla
        context.getAuthenticationSession().setAuthNote("otp", otpCode);
        logger.info("Set authNote 'otp' with value: " + otpCode);

        // OTP doğrulamanın gerekli olduğunu belirtmek için not ekle
        context.getAuthenticationSession().setAuthNote("otpRequired", "true");
        logger.info("Set authNote 'otpRequired' with value: true");

        // OTP kodunu e-posta ile gönder
        boolean emailSent = sendOTPEmail(context, email, otpCode);

        if (emailSent) {
            // Kullanıcıya OTP giriş formunu göster
            logger.info("OTP email sent successfully, displaying OTP form.");
            context.challenge(context.form().createForm("login-email-otp.ftl"));
        } else {
            logger.error("Failed to send OTP email.");
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR, context.form()
                    .setError("emailSendError")
                    .createErrorPage(Response.Status.INTERNAL_SERVER_ERROR));
        }
    }

    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    private boolean sendOTPEmail(AuthenticationFlowContext context, String email, String otpCode) {
        try {
            EmailSenderProvider emailSender = context.getSession().getProvider(EmailSenderProvider.class);
            Map<String, String> smtpConfig = context.getRealm().getSmtpConfig();
            emailSender.send(smtpConfig, email, "Your OTP Code", "Your OTP Code is: " + otpCode, null);
            logger.info("OTP email sent to: " + email);
            return true;
        } catch (Exception e) {
            logger.error("Failed to send OTP email", e);
            return false;
        }
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        logger.info("action method called - Retrieving OTP from form and session");

        String enteredOtp = context.getHttpRequest().getDecodedFormParameters().getFirst("otp");
        String expectedOtp = context.getAuthenticationSession().getAuthNote("otp");

        logger.info("Entered OTP: " + enteredOtp);
        logger.info("Expected OTP from session: " + expectedOtp);

        if (expectedOtp != null && expectedOtp.equals(enteredOtp)) {
            logger.info("OTP matched, authentication successful.");
            context.success();
        } else {
            logger.warn("OTP did not match or is missing.");
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, context.form()
                    .setError("invalidOtp")
                    .createForm("login-email-otp.ftl"));
        }
    }

    @Override
    public boolean requiresUser() {
        logger.info("requiresUser method called");
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        logger.info("configuredFor method called for user: " + user.getUsername());
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        logger.info("setRequiredActions method called for user: " + user.getUsername());
    }

    @Override
    public void close() {
        logger.info("close method called");
    }
}
