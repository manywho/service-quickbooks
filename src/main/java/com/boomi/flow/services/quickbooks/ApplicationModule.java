package com.boomi.flow.services.quickbooks;

import com.boomi.flow.services.quickbooks.quickbooks.QuickbooksApi20Provider;
import com.boomi.flow.services.quickbooks.quickbooks.QuickbooksPlatformClientProvider;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.inject.AbstractModule;
import com.intuit.oauth2.client.OAuth2PlatformClient;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(OAuth2PlatformClient.class).toProvider(QuickbooksPlatformClientProvider.class).asEagerSingleton();
        bind(OAuth20Service.class).toProvider(QuickbooksApi20Provider.class).asEagerSingleton();
    }
}
