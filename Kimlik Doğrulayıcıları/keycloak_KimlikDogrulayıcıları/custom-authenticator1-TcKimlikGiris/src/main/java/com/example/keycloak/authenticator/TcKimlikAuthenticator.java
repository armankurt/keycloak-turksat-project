package com.example.keycloak.authenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.PasswordCredentialProvider;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.jboss.logging.Logger;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class TcKimlikAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(TcKimlikAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        logger.info("authenticate: Başlatıldı.");
        try {
            // Retrieve form data
            MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
            String tcNumber = formData.getFirst("tcNumber");
            String password = formData.getFirst("password");

            // Check if form data is missing or empty
            if (tcNumber == null || password == null || tcNumber.isEmpty() || password.isEmpty()) {
                logger.info("authenticate: Form verisi eksik veya boş, login-tc.ftl'ye yönlendiriliyor.");

                String actionUrl = context.getActionUrl(context.generateAccessCode()).toString();
                logger.info("Generated Action URL: " + actionUrl);

                Response response = context.form()
                        .setAttribute("actionUrl", actionUrl)
                        .createForm("login-tc.ftl");
                context.challenge(response);
                return; // End the flow here
            }

            logger.info("authenticate: tcNumber = " + tcNumber);

            // Attempt to find the user by T.C. Kimlik No
            UserModel user = findUserByTcNumber(context, tcNumber);
            if (user == null) {
                logger.warn("authenticate: Kullanıcı bulunamadı - T.C. Kimlik No: " + tcNumber);
                context.failureChallenge(AuthenticationFlowError.INVALID_USER, createErrorResponse("Kullanıcı bulunamadı."));
                return;
            }

            // Validate the password
            if (!validatePassword(context, user, password)) {
                logger.warn("authenticate: Geçersiz şifre.");
                context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, createErrorResponse("Geçersiz şifre."));
                return;
            }

            // User successfully authenticated
            logger.info("authenticate: Kullanıcı doğrulandı, oturum başlatılıyor.");
            context.setUser(user);
            context.success();

        } catch (Exception e) {
            logger.error("authenticate: Bir hata oluştu.", e);
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR, createErrorResponse("Bir hata oluştu."));
        }
    }


    @Override
    public void action(AuthenticationFlowContext context) {
        logger.info("action: Kullanıcı bir işlem yaptı, kontrol ediliyor.");

        if (context.getUser() != null) {
            logger.info("action: Kullanıcı zaten oturum açmış durumda.");
            context.success();
            return;
        }

        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String tcNumber = formData.getFirst("tcNumber");
        String password = formData.getFirst("password");

        if (tcNumber == null || password == null || tcNumber.isEmpty() || password.isEmpty()) {
            logger.error("action: Form verileri eksik veya boş.");
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, createErrorResponse("Form verileri eksik."));
            return;
        }

        logger.info("action: Form verileri alındı, tcNumber = " + tcNumber);

        UserModel user = findUserByTcNumber(context, tcNumber);
        if (user == null) {
            logger.error("action: Kullanıcı bulunamadı. T.C. Kimlik Numarası: " + tcNumber);
            context.failureChallenge(AuthenticationFlowError.INVALID_USER, createErrorResponse("Kullanıcı bulunamadı."));
            return;
        }

        if (!validatePassword(context, user, password)) {
            logger.error("action: Geçersiz şifre.");
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, createErrorResponse("Geçersiz şifre."));
            return;
        }

        logger.info("action: Kullanıcı doğrulandı.");
        context.setUser(user);
        context.success();
    }




    private UserModel findUserByTcNumber(AuthenticationFlowContext context, String tcNumber) {
        logger.info("findUserByTcNumber: T.C. Kimlik Numarası ile kullanıcı aranıyor - " + tcNumber);

        UserModel user = context.getSession()
                .users()
                .searchForUserByUserAttributeStream(context.getRealm(), "tcNumber", tcNumber)
                .findFirst()
                .orElse(null);

        if (user == null) {
            logger.warn("findUserByTcNumber: Kullanıcı bulunamadı. T.C. Kimlik Numarası: " + tcNumber);
        } else {
            logger.info("findUserByTcNumber: Kullanıcı bulundu. Username: " + user.getUsername());
        }

        return user;
    }


    private boolean validatePassword(AuthenticationFlowContext context, UserModel user, String password) {
        logger.info("validatePassword: Şifre doğrulaması yapılıyor.");
        PasswordCredentialProvider passwordProvider = (PasswordCredentialProvider) context.getSession()
                .getProvider(CredentialProvider.class, "keycloak-password");
        return passwordProvider.isValid(context.getRealm(), user, UserCredentialModel.password(password));
    }

    private Response createErrorResponse(String errorMessage) {
        logger.warn("createErrorResponse: Hata yanıtı oluşturuluyor - " + errorMessage);
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\": \"" + errorMessage + "\"}")
                .type("application/json")
                .build();
    }


    @Override
    public boolean requiresUser() {
        logger.info("requiresUser: false döndürüyor.");
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        logger.info("configuredFor: true döndürüyor.");
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        logger.info("setRequiredActions: Gerekli eylem yok.");
    }

    @Override
    public void close() {
        logger.info("close: Authenticator kapatıldı.");
    }
}