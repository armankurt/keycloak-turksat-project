package com.example.keycloak.authenticator;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class ApplicationMethodConditionAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(ApplicationMethodConditionAuthenticator.class.getName());

    static {
        try {
            // Log dosyasını masaüstüne kaydetmek için FileHandler ayarı
            String userHome = System.getProperty("user.home");
            String logPath = userHome + "/Desktop/log.txt";
            FileHandler fileHandler = new FileHandler(logPath, true); // true, dosyaya ekleme yapar
            fileHandler.setFormatter(new SimpleFormatter()); // Basit bir format kullanıyoruz
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // Console loglarını kapatır
            logger.info("Log dosyası başarıyla oluşturuldu: " + logPath);
        } catch (IOException e) {
            System.err.println("Log dosyası oluşturulamadı: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        logger.info("authenticate method called - Checking selected authentication method");

        // Seçilen doğrulama metodunu almak
        String selectedMethod = context.getAuthenticationSession().getAuthNote("selectedMethod");
        logger.info("Retrieved selected method from session: " + selectedMethod);

        // Eğer seçilen metod "totp" ise, OTP doğrulamasına geç
        if ("totp".equals(selectedMethod)) {
            logger.info("Selected method is 'totp'. Proceeding with OTP verification step.");
            context.success(); // TOTP seçildiyse işlem başarılı, OTP adımına geç
        } else {
            logger.info("Selected method is not 'totp'. Skipping OTP step.");
            context.attempted(); // Eğer farklı bir metod seçildiyse, bu adımı atla
        }
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        logger.info("action method called - No additional action required.");
    }

    @Override
    public boolean requiresUser() {
        logger.info("requiresUser method called - Returning false.");
        return false; // Bu metod için kullanıcı doğrulama gerekli değil
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        logger.info("configuredFor method called - Returning true.");
        return true; // Bu metod her durumda etkin
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        logger.info("setRequiredActions method called - No additional actions set.");
    }

    @Override
    public void close() {
        logger.info("close method called - Closing resources if any.");
    }
}
