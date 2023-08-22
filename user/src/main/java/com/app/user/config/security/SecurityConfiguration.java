package com.app.user.config.security;

import com.app.common.security.AuthoritiesConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Value("${spring.security.enabled}")
    private boolean securityEnabled;

    @Bean
    public SecurityFilterChain configure(HttpSecurity builder) throws Exception {
        if (securityEnabled) {
            builder
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**").disable())
                .authorizeHttpRequests(authz ->
                    // prettier-ignore
                    authz
                        .requestMatchers("/h2-console/**", "/error/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/authenticate").permitAll()
                        .requestMatchers("/api/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/v3/api-docs/**").hasAuthority(AuthoritiesConstants.ADMIN)
                        .requestMatchers("/management/health").permitAll()
                        .requestMatchers("/management/health/**").permitAll()
                        .requestMatchers("/management/info").permitAll()
                        .requestMatchers("/management/prometheus").permitAll()
                        .requestMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions ->
                    exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        } else {
            builder.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**").disable());
        }
        return builder.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
