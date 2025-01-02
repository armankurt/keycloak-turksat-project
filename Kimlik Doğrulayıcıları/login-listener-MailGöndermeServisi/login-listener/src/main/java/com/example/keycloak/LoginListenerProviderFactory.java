package com.example.keycloak;

import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class LoginListenerProviderFactory implements EventListenerProviderFactory {

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new LoginListener(session);
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // Başlangıç yapılandırması yapılacaksa buraya ekleyin
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Sonraki yapılandırmalar yapılacaksa buraya ekleyin
    }

    @Override
    public void close() {
        // Kaynaklar temizlenebilir
    }

    @Override
    public String getId() {
        return "login-listener";
    }
}
