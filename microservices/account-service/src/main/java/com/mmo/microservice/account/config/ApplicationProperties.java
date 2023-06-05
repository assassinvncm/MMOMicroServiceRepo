package com.mmo.microservice.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {
    @Value("${tiktok.exchange.grant-type}")
    public String APP_PROP_EXCHANGE_GRANT_TYPE;

    @Value("${tiktok.exchange.client-id}")
    public String APP_PROP_EXCHANGE_CLIENT_ID;

    @Value("${tiktok.exchange.client-secret}")
    public String APP_PROP_EXCHANGE_CLIENT_SECRET;
}
