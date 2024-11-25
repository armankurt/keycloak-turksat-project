package com.example.keycloak.authenticator;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;

import java.util.List;

public class EsignIdentityProviderFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "esign";

    @Override
    public String getReferenceCategory() {
        return "e-imza"; // Replace this with a suitable category name for your use case
    }

    @Override
    public String getHelpText() {
        return "Provides e-signature authentication functionality."; // Provide a meaningful description
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "E-Imza Sağlayıcı"; // Uygun bir açıklama ekleyin
    }

    @Override
    public boolean isConfigurable() {
        return true; // Eğer bu factory konfigüre edilebiliyorsa true yapabilirsiniz
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false; // Kullanıcı yapılandırması gerekiyorsa true olarak değiştirin
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
                .helpText("E-Imza işlemleri için kullanılacak backend URL'sini belirtin.")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("")
                .add()
                .build();
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new EsignIdentityProviderAuthenticator(); // Doğru Authenticator sınıfını döndürün
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // Factory'yi başlatırken gereken özel bir yapılandırma varsa burayı kullanabilirsiniz
    }

    @Override
    public void postInit(org.keycloak.models.KeycloakSessionFactory factory) {
        // Factory başlatıldıktan sonra gereken işlemler varsa burayı kullanabilirsiniz
    }

    @Override
    public void close() {
        // Kaynak temizleme işlemleri burada yapılabilir
    }
}
