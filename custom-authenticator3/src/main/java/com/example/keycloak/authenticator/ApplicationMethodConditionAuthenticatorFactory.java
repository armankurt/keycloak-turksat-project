package com.example.keycloak.authenticator;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;

import java.util.List;

public class ApplicationMethodConditionAuthenticatorFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "application-method-condition";

    @Override
    public Authenticator create(KeycloakSession session) {
        return new ApplicationMethodConditionAuthenticator();
    }

    @Override
    public void init(Config.Scope config) {
        // Gerek yok
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Gerek yok
    }

    @Override
    public void close() {
        // Gerek yok
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Application Method Condition";
    }

    @Override
    public Requirement[] getRequirementChoices() {
        return new Requirement[]{Requirement.CONDITIONAL, Requirement.DISABLED};
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return null;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getHelpText() {
        return "Proceed to OTP step if 'application' method is selected.";
    }

    @Override
    public String getReferenceCategory() {
        return null; // veya "OTP" gibi uygun bir kategori ismi döndürebilirsiniz
    }
}
