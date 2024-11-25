package com.example.keycloak.authenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.UserModel;

public class EsignIdentityProviderAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        // Burada doğrulama mantığını yazın
        context.success(); // Eğer başarılıysa bunu çağırın
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        // Ek işlem gerekliliklerini buraya yazabilirsiniz
    }

    @Override
    public boolean requiresUser() {
        return true; // Eğer kullanıcı doğrulaması gerekiyorsa true döndürün
    }

    @Override
    public boolean configuredFor(org.keycloak.models.KeycloakSession session, org.keycloak.models.RealmModel realm, UserModel user) {
        return true; // Kullanıcı yapılandırması gerekiyorsa bunu kontrol edin
    }

    @Override
    public void setRequiredActions(org.keycloak.models.KeycloakSession session, org.keycloak.models.RealmModel realm, UserModel user) {
        // Kullanıcıya gerekli aksiyonlar eklenebilir
    }

    @Override
    public void close() {
        // Kaynak temizleme işlemleri burada yapılabilir
    }
}
