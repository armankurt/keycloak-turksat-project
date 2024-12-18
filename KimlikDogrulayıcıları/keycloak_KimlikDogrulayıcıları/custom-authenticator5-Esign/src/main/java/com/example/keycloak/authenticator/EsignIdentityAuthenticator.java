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
import java.util.Random;

public class EsignIdentityAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(EsignIdentityAuthenticator.class);
    private static final String SESSION_SECURITY_CODE = "securityCode";

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        logger.info("authenticate: Başlatıldı.");
        try {
            // Dinamik güvenlik kodu oluştur
            String securityCode = generateSecurityCode();
            context.getAuthenticationSession().setAuthNote(SESSION_SECURITY_CODE, securityCode);

            logger.info("authenticate: Güvenlik kodu oluşturuldu: " + securityCode);

            // Formu göster
            String actionUrl = context.getActionUrl(context.generateAccessCode()).toString();
            Response response = context.form()
                    .setAttribute("securityCode", securityCode) // Kod frontend'e gönderiliyor
                    .setAttribute("actionUrl", actionUrl)
                    .createForm("login-esign.ftl");
            context.challenge(response);
        } catch (Exception e) {
            logger.error("authenticate: Bir hata oluştu.", e);
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR, createErrorResponse("Bir hata oluştu."));
        }
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        logger.info("action: Kullanıcı bir işlem yaptı.");
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String tcNumber = formData.getFirst("tcNumber");
        String securityCode = formData.getFirst("securityCode");
        String sessionSecurityCode = context.getAuthenticationSession().getAuthNote(SESSION_SECURITY_CODE);

        // Eksik form verisi kontrolü
        if (tcNumber == null || securityCode == null || tcNumber.isEmpty() || securityCode.isEmpty()) {
            logger.warn("action: Eksik form verisi.");
            authenticate(context);
            return;
        }

        logger.infof("action: T.C. Kimlik No: %s, Güvenlik Kodu: %s", tcNumber, securityCode);

        // Güvenlik kodu doğrulaması
        if (!securityCode.equals(sessionSecurityCode)) {
            logger.warn("action: Güvenlik kodu hatalı. Beklenen: " + sessionSecurityCode + ", Girilen: " + securityCode);
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, createErrorResponse("Hatalı güvenlik kodu."));
            return;
        }

        // T.C. Kimlik doğrulaması (Dummy)
        UserModel user = findUserByTcNumber(context, tcNumber);
        if (user == null) {
            logger.warn("action: Kullanıcı bulunamadı. T.C. Kimlik No: " + tcNumber);
            context.failureChallenge(AuthenticationFlowError.INVALID_USER, createErrorResponse("Kullanıcı bulunamadı."));
            return;
        }

        logger.info("action: Kullanıcı doğrulandı.");
        context.setUser(user);
        context.success();
    }

    private String generateSecurityCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // 4 haneli rastgele kod
        return String.valueOf(code);
    }

    private UserModel findUserByTcNumber(AuthenticationFlowContext context, String tcNumber) {
        return context.getSession()
                .users()
                .searchForUserByUserAttributeStream(context.getRealm(), "tcNumber", tcNumber)
                .findFirst()
                .orElse(null);
    }

    private Response createErrorResponse(String errorMessage) {
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
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {}

    @Override
    public void close() {}
}
