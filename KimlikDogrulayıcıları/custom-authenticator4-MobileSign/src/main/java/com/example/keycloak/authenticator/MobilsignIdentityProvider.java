package com.example.keycloak.authenticator;

import org.keycloak.broker.oidc.AbstractOAuth2IdentityProvider;
import org.keycloak.models.KeycloakSession;

public class MobilsignIdentityProvider extends AbstractOAuth2IdentityProvider<MobilsignIdentityProviderConfig> {

    public MobilsignIdentityProvider(KeycloakSession session, MobilsignIdentityProviderConfig config) {
        super(session, config);
    }

    // Gerçek sağlayıcıya bağlanarak doğrulama yapılacak metod
    public boolean validateMobileSignature(String mobileNumber, String password, String operator) {
        // Test ortamı için sabit doğrulama
        return "1234567890".equals(mobileNumber) && "password123".equals(password) && "turkcell".equalsIgnoreCase(operator);
    }

    @Override
    protected String getDefaultScopes() {
        // Varsayılan kapsamlar döndürülür. Test için boş bırakabilirsiniz.
        return "";
    }
}
