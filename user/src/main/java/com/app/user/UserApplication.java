package com.app.user;

import com.app.user.config.SecurityConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(SecurityConfigProperties.class)
@SpringBootApplication(
    exclude = {UserDetailsServiceAutoConfiguration.class},
    scanBasePackages = {"com.app.user", "com.app.common.handler"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
