package com.example.keycloak.authenticator;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class WaitingFormAuthenticatorFactory implements AuthenticatorFactory {

    private static final String PROVIDER_ID = "waiting-form-authenticator";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Waiting Form Authenticator";
    }

    @Override
    public String getHelpText() {
        return "Kullanıcıyı onay bekleme sayfasına yönlendirir ve API üzerinden onay alındığında giriş işlemini tamamlar.";
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new WaitingFormAuthenticator();
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return null; // Ek yapılandırma gerekmez
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[]{
                AuthenticationExecutionModel.Requirement.REQUIRED,
                AuthenticationExecutionModel.Requirement.ALTERNATIVE,
                AuthenticationExecutionModel.Requirement.DISABLED
        };
    }

    @Override
    public String getReferenceCategory() {
        return "authentication";
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // Başlatma işlemleri yok
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Sonrası işlemler yok
    }

    @Override
    public void close() {
        // Kapatma işlemleri yok
    }
}
