package com.example.keycloak.authenticator;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;

import java.util.List;

public class MobilsignIdentityProviderFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "mobilsign";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Mobil Sign Display";
    }

    @Override
    public String getHelpText() {
        return "Provides mobile-signature authentication functionality.";
    }

    @Override
    public String getReferenceCategory() {
        return "mobile-signature";
    }

    @Override
    public boolean isConfigurable() {
        return true;
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
    public List<ProviderConfigProperty> getConfigProperties() {
        return ProviderConfigurationBuilder.create()
                .property()
                .name("backendUrl")
                .label("Backend URL")
                .helpText("Override the default Base URL for this identity provider.")
                .type(ProviderConfigProperty.STRING_TYPE)
                .add()
                .build();
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new MobilsignIdentityAuthenticator();
    }



    @Override
    public void init(org.keycloak.Config.Scope config) {
        // Başlangıç işlemleri
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Başlangıç sonrası işlemleri
    }

    @Override
    public void close() {
        // Temizlik işlemleri
    }
}
