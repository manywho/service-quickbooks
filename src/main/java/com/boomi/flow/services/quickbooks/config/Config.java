package com.boomi.flow.services.quickbooks.config;

public class Config {
    public static String getClientId() {
        return System.getenv("QUICKBOOKS_CLIENT_ID");
    }

    public static String getClientSecret() {
        return System.getenv("QUICKBOOKS_CLIENT_SECRET");
    }
}
