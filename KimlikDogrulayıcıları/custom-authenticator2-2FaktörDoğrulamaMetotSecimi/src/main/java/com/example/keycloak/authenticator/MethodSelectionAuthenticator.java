package com.example.keycloak.authenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.jboss.logging.Logger;

import jakarta.ws.rs.core.Response;

public class MethodSelectionAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(MethodSelectionAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        // Kullanıcıya doğrulama yöntemi seçme sayfasını gösterir
        logger.info("authenticate method called - Rendering method selection page");
        context.challenge(context.form().createForm("login-method-selection.ftl"));
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        logger.info("action method called - Retrieving selected method");

        // Kullanıcı tarafından seçilen doğrulama yöntemini al
        String method = context.getHttpRequest().getDecodedFormParameters().getFirst("method");

        // Eğer method boşsa veya geçersiz bir karakter içeriyorsa, hata döndür
        if (method == null || method.trim().isEmpty()) {
            logger.error("Doğrulama yöntemi seçilmedi.");
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
                    context.form().setError("Doğrulama yöntemi seçilmedi").createErrorPage(Response.Status.BAD_REQUEST));
            return;
        }

        if (!method.matches("^[a-zA-Z]+$")) {
            logger.error("Geçersiz doğrulama yöntemi seçildi.");
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
                    context.form().setError("Geçersiz doğrulama yöntemi seçildi").createErrorPage(Response.Status.BAD_REQUEST));
            return;
        }

        // Seçilen yöntemi oturum notuna kaydet
        context.getAuthenticationSession().setAuthNote("selectedMethod", method);
        logger.info("Selected method saved in auth note: " + method);

        // Doğrulama yöntemi sayfasını belirle ve auth-method-page oturum notuna kaydet
        String formPage;
        switch (method.toLowerCase()) {
            case "email":
                formPage = "login-email-otp.ftl";
                break;
            case "phone":
                formPage = "login-sms.ftl";
                break;
            case "totp":
                formPage = "login-otp.ftl";
                break;
            default:
                logger.error("Geçersiz doğrulama yöntemi seçildi.");
                context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
                        context.form().setError("Geçersiz doğrulama yöntemi seçildi").createErrorPage(Response.Status.BAD_REQUEST));
                return;
        }

        // Seçilen doğrulama yöntemi sayfasını kaydet
        context.getAuthenticationSession().setAuthNote("auth-method-page", formPage);
        logger.info("Authentication method page set to: " + formPage);

        // Başarı durumu çağrısı yap, bir sonraki adımda doğrulama yöntemine geçilecek
        context.success();
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // Gerekli bir işlem yok
    }

    @Override
    public void close() {
        // Kaynakları kapatma işlemi gerekmiyor
    }
}
