package com.example.keycloak.authenticator;

import org.keycloak.broker.oidc.OAuth2IdentityProviderConfig;
import org.keycloak.models.IdentityProviderModel;

public class MobilsignIdentityProviderConfig extends OAuth2IdentityProviderConfig {

    private static final String BACKEND_URL = "backendUrl";
    MobilsignIdentityProviderConfig(IdentityProviderModel identityProviderModel){
        super(identityProviderModel);
    }

    MobilsignIdentityProviderConfig(){}

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


//    public static List<ProviderConfigProperty> getConfigProperties(){
//        return ProviderConfigurationBuilder.create()
//                .property().name("backendUrl")
//                .type(ProviderConfigProperty.STRING_TYPE)
//                .label("Backend Url")
//                .helpText("The Backend Url")
//                .add().build();
//    }
}
