package com.boomi.flow.services.quickbooks.quickbooks;

import com.boomi.flow.services.quickbooks.config.Config;
import com.google.inject.Provider;
import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.config.Environment;
import com.intuit.oauth2.config.OAuth2Config;

public class QuickbooksPlatformClientProvider implements Provider<OAuth2PlatformClient> {
    @Override
    public OAuth2PlatformClient get() {
        OAuth2Config oauth2Config = new OAuth2Config.OAuth2ConfigBuilder(Config.getClientId(), Config.getClientSecret())
                .callDiscoveryAPI(Environment.SANDBOX)
                .buildConfig();

        return new OAuth2PlatformClient(oauth2Config);
    }
}
