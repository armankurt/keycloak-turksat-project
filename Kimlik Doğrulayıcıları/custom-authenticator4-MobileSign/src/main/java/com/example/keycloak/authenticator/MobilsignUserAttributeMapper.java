package com.example.keycloak.authenticator;

import org.keycloak.broker.oidc.mappers.AbstractJsonUserAttributeMapper;

public class MobilsignUserAttributeMapper extends AbstractJsonUserAttributeMapper {

    public static final String PROVIDER_ID = "mobil-sign-user-attribute-mapper";
    private static final String[] cp = new String[] { MobilsignIdentityProviderFactory.PROVIDER_ID };

    @Override
    public String[] getCompatibleProviders() {
        return cp;
    }

    @Override
    public String getId() {
        return PROVIDER_ID ;
    }
}
