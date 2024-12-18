package com.example.keycloak.authenticator;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import java.util.List;

public class TcKimlikAuthenticatorFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "tc-kimlik-authenticator";

    @Override
    public Authenticator create(KeycloakSession session) {
        return new TcKimlikAuthenticator();
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "TC Kimlik Authenticator";
    }

    @Override
    public String getHelpText() {
        return "TC Kimlik ile giriş işlemi yapan Authenticator";
    }

    @Override
    public String getReferenceCategory() {
        return "Authentication";
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return ProviderConfigurationBuilder.create().build();
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // Başlangıç ayarları burada yapılabilir
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Post-init işlemleri burada yapılabilir
    }

    @Override
    public void close() {
        // Kaynak temizleme işlemleri burada yapılabilir
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        AuthenticationExecutionModel.Requirement[] requirements = new AuthenticationExecutionModel.Requirement[] {
                AuthenticationExecutionModel.Requirement.REQUIRED,
                AuthenticationExecutionModel.Requirement.ALTERNATIVE,
                AuthenticationExecutionModel.Requirement.DISABLED
        };
        return requirements;
    }
}

