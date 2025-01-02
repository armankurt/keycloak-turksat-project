package com.example.keycloak.authenticator;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.UserModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class MobilsignIdentityAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(MobilsignIdentityAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        logger.info("authenticate: Başlatıldı.");
        try {
            // Form verilerini al
            MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
            String mobileNumber = formData.getFirst("mobile_number");
            String tcNumber = formData.getFirst("tcNumber");
            String operator = formData.getFirst("operator");

            // Eksik form verisi kontrolü
            if (mobileNumber == null || tcNumber == null || operator == null ||
                    mobileNumber.isEmpty() || tcNumber.isEmpty() || operator.isEmpty()) {
                logger.warn("authenticate: Eksik form verisi.");
                String actionUrl = context.getActionUrl(context.generateAccessCode()).toString();
                logger.info("Generated Action URL: " + actionUrl);

                Response response = context.form()
                        .setAttribute("actionUrl", actionUrl)
                        .setError("Lütfen tüm alanları doldurun.")
                        .createForm("mobilsign-authentication.ftl");
                context.challenge(response);
                return;
            }

            logger.infof("authenticate: Telefon Numarası: %s, T.C. Kimlik No: %s, Operatör: %s",
                    mobileNumber, tcNumber, operator);

            // Kullanıcıyı mobil numara ve T.C. kimlik numarasına göre arayın
            UserModel user = findUserByMobileNumberAndTcNumber(context, mobileNumber, tcNumber);
            if (user == null) {
                logger.warn("authenticate: Kullanıcı bulunamadı. Telefon Numarası: " + mobileNumber + ", T.C. Kimlik No: " + tcNumber);
                context.failureChallenge(AuthenticationFlowError.INVALID_USER, createErrorResponse("Kullanıcı bulunamadı."));
                return;
            }

            // Operatör doğrulaması
            if (!validateOperator(user, operator)) {
                logger.warn("authenticate: Geçersiz operatör.");
                context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, createErrorResponse("Geçersiz operatör."));
                return;
            }

            // Başarılı doğrulama
            logger.info("authenticate: Kullanıcı doğrulandı.");
            context.setUser(user);
            context.success();

        } catch (Exception e) {
            logger.error("authenticate: Bir hata oluştu.", e);
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR, createErrorResponse("Bir hata oluştu."));
        }
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        logger.info("action: Kullanıcı bir işlem yaptı.");
        if (context.getUser() != null) {
            context.success();
            return;
        }

        authenticate(context);
    }

    private UserModel findUserByMobileNumberAndTcNumber(AuthenticationFlowContext context, String mobileNumber, String tcNumber) {
        logger.info("findUserByMobileNumberAndTcNumber: Kullanıcı aranıyor. Telefon Numarası: " + mobileNumber + ", T.C. Kimlik No: " + tcNumber);

        UserModel user = context.getSession()
                .users()
                .searchForUserByUserAttributeStream(context.getRealm(), "mobile_number", mobileNumber)
                .filter(u -> tcNumber.equals(u.getFirstAttribute("tcNumber"))) // T.C. Kimlik kontrolü
                .findFirst()
                .orElse(null);

        if (user == null) {
            logger.warn("findUserByMobileNumberAndTcNumber: Kullanıcı bulunamadı. Telefon Numarası: " + mobileNumber + ", T.C. Kimlik No: " + tcNumber);
        } else {
            logger.info("findUserByMobileNumberAndTcNumber: Kullanıcı bulundu. Username: " + user.getUsername());
        }

        return user;
    }

    private boolean validateOperator(UserModel user, String operator) {
        logger.info("validateOperator: Operatör doğrulaması yapılıyor.");
        String userOperator = user.getFirstAttribute("operator");
        return operator.equalsIgnoreCase(userOperator);
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
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // Gerekli işlemleri tanımlayın
    }

    @Override
    public void close() {
        logger.info("close: Authenticator kapatıldı.");
    }
}