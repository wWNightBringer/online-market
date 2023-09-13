package com.app.order;

import com.app.order.config.SecurityConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafka;

@EnableConfigurationProperties(SecurityConfigProperties.class)
@SpringBootApplication(
    exclude = {UserDetailsServiceAutoConfiguration.class},
    scanBasePackages = {
        "com.app.order",
        "com.app.common.handler",
        "com.app.common.micrometr",
        "com.app.common.config.kafka.producer"})
@EnableKafka
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
