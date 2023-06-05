package com.mmo.microservice.video.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {
    @Value("${tiktok.save-path}")
    public String APP_PROP_EXCHANGE_SAVE_PATH;

    @Value("${tiktok.driver-path}")
    public String APP_PROP_EXCHANGE_DRIVER_PATH;

    @Value("${tiktok.download-rage}")
    public int APP_PROP_DOWNLOAD_RANGE;

    @Value("${tiktok.url}")
    public String APP_PROP_TIKTOK_URL;

    @Value("${tiktok.snaptik-url}")
    public String APP_PROP_SNAPTIK_URL;
}
