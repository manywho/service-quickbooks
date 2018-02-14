package com.boomi.flow.services.quickbooks.authentication;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.data.UserInfoResponse;
import com.intuit.oauth2.exception.OpenIdException;
import com.manywho.sdk.api.security.AuthenticatedWhoResult;
import com.manywho.sdk.api.security.AuthenticationCredentials;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class AuthenticationManager {
    private final OAuth2PlatformClient quickbooksClient;
    private final OAuth20Service quickbooksOAuth2;

    @Inject
    public AuthenticationManager(OAuth2PlatformClient quickbooksClient, OAuth20Service quickbooksOAuth2) {
        this.quickbooksClient = quickbooksClient;
        this.quickbooksOAuth2 = quickbooksOAuth2;
    }

    public AuthenticatedWhoResult authentication(AuthenticationCredentials credentials) {
        OAuth2AccessToken token;

        try {
            // Request an access token from Quickbooks using the given authorization code
            token = quickbooksOAuth2.getAccessToken(credentials.getCode());
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException("Unable to get the access token from Quickbooks: " + e.getMessage(), e);
        }

        if (token == null) {
            throw new RuntimeException("An empty access token was given back from Quickbooks");
        }

        UserInfoResponse userInfoResponse;
        try {
            // Request the user's profile from Quickbooks
            userInfoResponse = quickbooksClient.getUserInfo(token.getAccessToken());
        } catch (OpenIdException e) {
            throw new RuntimeException("Unable to fetch the current user information from Quickbooks", e);
        }

        // Build up the profile result from the information Quickbooks gives us
        AuthenticatedWhoResult result = new AuthenticatedWhoResult();
        result.setDirectoryId("Quickbooks");
        result.setDirectoryName("Quickbooks");
        result.setEmail(userInfoResponse.getEmail());
        result.setFirstName(userInfoResponse.getGivenName());
        result.setIdentityProvider("?");
        result.setLastName(userInfoResponse.getFamilyName());
        result.setStatus(AuthenticatedWhoResult.AuthenticationStatus.Authenticated);
        result.setTenantName("?");
        result.setToken(token.getAccessToken());
        result.setUserId(userInfoResponse.getSub());
        result.setUsername(userInfoResponse.getEmail());

        return result;
    }
}
