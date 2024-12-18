package com.example.keycloak;

import org.keycloak.email.EmailException;
import org.keycloak.email.EmailTemplateProvider;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;
import org.jboss.logging.Logger;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class LoginListener implements EventListenerProvider {

    private static final Logger logger = Logger.getLogger(LoginListener.class);
    private final KeycloakSession session;

    public LoginListener(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void onEvent(Event event) {
        String userId = event.getUserId();

        // userId null ise işlemi atla ve log kaydet
        if (userId == null) {
            logger.warn("User ID is null for event: " + event.getType());
            return;
        }

        if (event.getType() == EventType.LOGIN) {
            logger.info("User logged in: " + userId);
            sendEmailNotification(event, "Login Notification", "login-notification.ftl");
        } else if (event.getType() == EventType.LOGOUT) {
            logger.info("User logged out: " + userId);
            sendEmailNotification(event, "Logout Notification", "logout-notification.ftl");
        } else if (event.getType() == EventType.LOGIN_ERROR) {
            logger.info("Login error for user: " + userId);
            sendEmailNotification(event, "Login Error", "login-error-notification.ftl");
        } else {
            logger.info("Unhandled event type: " + event.getType());
        }
    }

    private void sendEmailNotification(Event event, String subject, String template) {
        try {
            String userId = event.getUserId();
            if (userId == null) {
                logger.warn("User ID is null, skipping email notification for event: " + event.getType());
                return;
            }

            UserModel user = session.users().getUserById(session.getContext().getRealm(), userId);

            // Eğer user null ise işlemi durdur
            if (user == null) {
                logger.warn("User not found for ID: " + userId);
                return;
            }

            String email = user.getEmail();
            if (email == null || email.isEmpty()) {
                logger.warn("No email found for user: " + userId);
                return;
            }

            // Email Template Provider'ı ayarla
            EmailTemplateProvider emailTemplateProvider = session.getProvider(EmailTemplateProvider.class);
            emailTemplateProvider.setRealm(session.getContext().getRealm());
            emailTemplateProvider.setUser(user);

            // Şablon için dinamik değişkenleri tanımla
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("user", user);
            attributes.put("realmName", session.getContext().getRealm().getName());

            // E-posta gönder
            emailTemplateProvider.send(subject, List.of(email), template, attributes);

            logger.info(subject + " email sent to: " + email);
        } catch (EmailException e) {
            logger.error("Error sending " + subject.toLowerCase() + " email", e);
        } catch (Exception e) {
            logger.error("General error occurred while sending " + subject.toLowerCase() + " email", e);
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean includeRepresentation) {
        // Admin eventlerini işlemek istemiyorsanız, burayı boş bırakabilirsiniz
    }

    @Override
    public void close() {
        // Kapanış işlemleri yapılacaksa buraya ekleyin
    }
}
