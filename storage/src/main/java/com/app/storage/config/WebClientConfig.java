package com.app.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import static com.app.common.constant.ServicesMetadata.USER_SERVICE_URL;

@Configuration
public class WebClientConfig {

    @Bean(name = "webUserClient")
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl(USER_SERVICE_URL)
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
}
