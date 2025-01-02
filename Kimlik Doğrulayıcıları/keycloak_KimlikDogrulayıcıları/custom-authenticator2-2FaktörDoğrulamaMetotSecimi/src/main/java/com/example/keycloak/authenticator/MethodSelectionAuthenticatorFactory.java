package com.example.keycloak.authenticator;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;

import java.util.List;

public class MethodSelectionAuthenticatorFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "method-selection-authenticator";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new MethodSelectionAuthenticator();
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // Başlangıçta yapılacak bir işlem yok
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Başlangıç sonrası yapılacak bir işlem yok
    }

    @Override
    public void close() {
        // Kaynakları kapatma işlemi yok
    }

    @Override
    public String getDisplayType() {
        return "Method Selection Authenticator";
    }

    @Override
    public String getHelpText() {
        return "Kullanıcının doğrulama yöntemi seçmesine izin verir (email, sms veya totp gibi).";
    }

    @Override
    public Requirement[] getRequirementChoices() {
        return new Requirement[]{Requirement.REQUIRED, Requirement.ALTERNATIVE, Requirement.CONDITIONAL, Requirement.DISABLED};
    }

    @Override
    public boolean isConfigurable() {
        return true; // Yapılandırılabilir olması için true olarak ayarladık
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return ProviderConfigurationBuilder.create()
                .property()
                .name("defaultMethod")
                .label("Default Authentication Method")
                .type(ProviderConfigProperty.STRING_TYPE)
                .helpText("Varsayılan doğrulama yöntemini belirleyin (örneğin, email, sms, totp).")
                .defaultValue("email")
                .add()
                .build();
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getReferenceCategory() {
        return "Method Selection";
    }
}
