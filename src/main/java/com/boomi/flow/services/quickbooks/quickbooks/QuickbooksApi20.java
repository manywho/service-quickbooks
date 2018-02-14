package com.boomi.flow.services.quickbooks.quickbooks;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class QuickbooksApi20 extends DefaultApi20 {
    @Override
    public String getAccessTokenEndpoint() {
        return "https://oauth.platform.intuit.com/oauth2/v1/tokens/bearer";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://appcenter.intuit.com/connect/oauth2";
    }
}
