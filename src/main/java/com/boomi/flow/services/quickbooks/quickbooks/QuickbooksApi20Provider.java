package com.boomi.flow.services.quickbooks.quickbooks;

import com.boomi.flow.services.quickbooks.config.Config;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.inject.Provider;

public class QuickbooksApi20Provider implements Provider<OAuth20Service> {
    @Override
    public OAuth20Service get() {
        return new ServiceBuilder(Config.getClientId())
                .apiSecret(Config.getClientSecret())
                .callback("https://flow.manywho.com/api/run/1/oauth2")
                .scope("openid profile phone address email com.intuit.quickbooks.accounting")
                .build(new QuickbooksApi20());
    }
}
