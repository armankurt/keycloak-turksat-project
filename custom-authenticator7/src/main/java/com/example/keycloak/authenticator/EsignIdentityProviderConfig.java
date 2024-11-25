package com.example.keycloak.authenticator;

import org.keycloak.broker.oidc.OAuth2IdentityProviderConfig;
import org.keycloak.models.IdentityProviderModel;

public class EsignIdentityProviderConfig extends OAuth2IdentityProviderConfig {
    private static final String BACKEND_URL = "backendUrl";
    EsignIdentityProviderConfig(IdentityProviderModel identityProviderModel){
        super(identityProviderModel);
    }

    EsignIdentityProviderConfig(){}

    public  String getBackendUrl() {
        return getConfig().get(BACKEND_URL);
    }

    public void setBackendUrl(String backendUrl) {
        getConfig().put(BACKEND_URL,backendUrl);
    }

    String trimTrailingSlash(String backendUrl){
        if(backendUrl != null & backendUrl.endsWith("/")){
            backendUrl = backendUrl.substring(0,backendUrl.length()-1);
        }
        return backendUrl;
    }
}