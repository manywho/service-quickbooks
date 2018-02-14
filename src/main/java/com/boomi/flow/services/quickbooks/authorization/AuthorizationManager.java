package com.boomi.flow.services.quickbooks.authorization;

import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.inject.Inject;
import com.manywho.sdk.api.AuthorizationType;
import com.manywho.sdk.api.run.elements.type.ObjectDataRequest;
import com.manywho.sdk.api.run.elements.type.ObjectDataResponse;
import com.manywho.sdk.api.security.AuthenticatedWho;
import com.manywho.sdk.services.types.TypeBuilder;
import com.manywho.sdk.services.types.system.$User;

public class AuthorizationManager {
    private final OAuth20Service quickbooksOAuth2;
    private final TypeBuilder typeBuilder;

    @Inject
    public AuthorizationManager(OAuth20Service quickbooksOAuth2, TypeBuilder typeBuilder) {
        this.quickbooksOAuth2 = quickbooksOAuth2;
        this.typeBuilder = typeBuilder;
    }

    public ObjectDataResponse authorization(AuthenticatedWho authenticatedWho, ObjectDataRequest request) {
        String status;

        switch (request.getAuthorization().getGlobalAuthenticationType()) {
            case AllUsers:
                // If it's a public user (i.e. not logged in) then return a 401
                if (authenticatedWho.getUserId().equals("PUBLIC_USER")) {
                    status = "401";
                } else {
                    status = "200";
                }

                break;
            case Public:
                status = "200";
                break;
            case Specified:
            default:
                status = "401";
                break;
        }

        $User user = new $User();
        user.setDirectoryId("Quickbooks");
        user.setDirectoryName("Quickbooks");
        user.setAuthenticationType(AuthorizationType.Oauth2);
        user.setLoginUrl(quickbooksOAuth2.getAuthorizationUrl());
        user.setStatus(status);
        user.setUserId("");

        return new ObjectDataResponse(typeBuilder.from(user));
    }

    public ObjectDataResponse groupAttributes() {
        throw new RuntimeException("Specifying group restrictions isn't yet supported in the Quickbooks Service");
    }

    public ObjectDataResponse groups() {
        throw new RuntimeException("Specifying group restrictions isn't yet supported in the Quickbooks Service");
    }

    public ObjectDataResponse userAttributes() {
        throw new RuntimeException("Specifying user restrictions isn't yet supported in the Quickbooks Service");
    }

    public ObjectDataResponse users() {
        throw new RuntimeException("Specifying user restrictions isn't yet supported in the Quickbooks Service");
    }
}
