package com.app.storage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "security")
@ConfigurationPropertiesScan
public record SecurityConfigProperties(boolean enabled) {

}
