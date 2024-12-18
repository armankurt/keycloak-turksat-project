package com.example.keycloak.authenticator;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class ConsentFormAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(ConsentFormAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        logger.info("authenticate: Aydınlatma formu gösteriliyor.");
        // Aydınlatma metni formunu göster
        Response response = context.form()
                .setAttribute("actionUrl", context.getActionUrl(context.generateAccessCode()))
                .createForm("consent-form.ftl");
        context.challenge(response);
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        logger.info("action: Kullanıcı bir işlem yaptı.");

        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String consentApproval = formData.getFirst("consentApproval");

        if ("true".equals(consentApproval)) {
            logger.info("action: Kullanıcı aydınlatma metnini onayladı.");
            context.success(); // Akışı başarıyla tamamla
        } else {
            logger.warn("action: Kullanıcı aydınlatma metnini onaylamadı.");
            Response response = context.form()
                    .setError("Lütfen aydınlatma metnini onaylayın.")
                    .createForm("consent-form.ftl");
            context.challenge(response);
        }
    }

    @Override
    public boolean requiresUser() {
        return true; // Kullanıcı bağlamı gereklidir
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, org.keycloak.models.UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, org.keycloak.models.UserModel user) {
        // Gerekli bir işlem yok
    }

    @Override
    public void close() {
        logger.info("close: Authenticator kapatıldı.");
    }
}
