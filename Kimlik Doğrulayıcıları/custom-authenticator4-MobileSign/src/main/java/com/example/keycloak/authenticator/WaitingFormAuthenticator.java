package com.example.keycloak.authenticator;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;
import org.keycloak.models.RealmModel;
import java.net.HttpURLConnection;
import java.net.URL;


import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class WaitingFormAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(WaitingFormAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        logger.info("authenticate: Bekleme sayfasına yönlendiriliyor.");

        // Kullanıcıyı waiting-form.ftl'ye yönlendirme
        String actionUrl = context.getActionUrl(context.generateAccessCode()).toString();
        Response response = context.form()
                .setAttribute("actionUrl", actionUrl) // actionUrl set ediliyor
                .createForm("waiting-form.ftl");
        context.challenge(response);
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        logger.info("action: Bekleme sayfası kontrolü.");

        // API'den kullanıcı onayı kontrolü
        boolean isApproved = checkUserApproval(context);
        if (isApproved) {
            logger.info("action: Kullanıcı onayı alındı, giriş başarılı.");
            context.success();
        } else {
            logger.warn("action: Kullanıcı onayı alınamadı.");
            String actionUrl = context.getActionUrl(context.generateAccessCode()).toString();
            Response response = context.form()
                    .setAttribute("actionUrl", actionUrl) // actionUrl yeniden ayarlanıyor
                    .setError("Henüz onay alınamadı, lütfen bekleyin.")
                    .createForm("waiting-form.ftl");
            context.challenge(response);
        }
    }

    private boolean checkUserApproval(AuthenticationFlowContext context) {
        logger.info("checkUserApproval: Kullanıcı onayı kontrol ediliyor.");

        try {
            URL url = new URL("http://host.docker.internal:8081/mobil-sign/approval");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) { // API onay verdiyse
                logger.info("checkUserApproval: Kullanıcı API onayı alındı.");
                return true;
            } else if (responseCode == 202) { // Bekleme durumu
                logger.info("checkUserApproval: Kullanıcı onayı bekleniyor.");
            } else { // Hata durumu
                logger.warn("checkUserApproval: API hatası. Response code: " + responseCode);
            }
        } catch (Exception e) {
            logger.error("checkUserApproval: API çağrısında hata oluştu.", e);
        }

        return false; // Henüz onay alınmadı
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
        // Gerekli işlemler yok
    }

    @Override
    public void close() {
        logger.info("close: WaitingFormAuthenticator kapatıldı.");
    }
}
