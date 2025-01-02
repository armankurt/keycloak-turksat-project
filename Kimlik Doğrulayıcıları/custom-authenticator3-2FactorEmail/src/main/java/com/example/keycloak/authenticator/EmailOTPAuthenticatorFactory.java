package com.example.keycloak.authenticator;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.provider.ServerInfoAwareProviderFactory;

import java.util.List;
import java.util.Map;

public class EmailOTPAuthenticatorFactory implements AuthenticatorFactory, ConfigurableAuthenticatorFactory, ServerInfoAwareProviderFactory {

    public static final String PROVIDER_ID = "email-otp-authenticator";
    private static final EmailOTPAuthenticator SINGLETON = new EmailOTPAuthenticator();

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return SINGLETON;
    }

    @Override
    public String getDisplayType() {
        return "Email OTP Authenticator";
    }

    @Override
    public String getHelpText() {
        return "Sends an OTP code to the user's email for authentication";
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return ProviderConfigurationBuilder.create().build();
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // Gerekli başlatma işlemleri
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Gerekli sonlandırma sonrası işlemler
    }

    @Override
    public void close() {
        // Kaynak temizleme işlemleri
    }

    @Override
    public String getReferenceCategory() {
        return "OTP";
    }

    @Override
    public boolean isUserSetupAllowed() {
        return true;
    }

    @Override
    public Requirement[] getRequirementChoices() {
        return new Requirement[] {
                Requirement.REQUIRED,
                Requirement.ALTERNATIVE,
                Requirement.ALTERNATIVE,
                Requirement.CONDITIONAL
        };
    }

    @Override
    public Map<String, String> getOperationalInfo() {
        return null; // Şu anda bir bilgi döndürmüyorsanız null dönebilirsiniz.
    }
}
