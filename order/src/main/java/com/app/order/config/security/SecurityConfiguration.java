package com.app.order.config.security;

import com.app.common.enumeration.Role;
import com.app.common.security.CommonLogoutSuccessHandler;
import com.app.order.config.SecurityConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final SecurityConfigProperties properties;
    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity builder) throws Exception {
        if (properties.enabled()) {
            builder
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**").disable())
                .authorizeHttpRequests(authz ->
                    // prettier-ignore
                    authz
                        .requestMatchers("/h2-console/**", "/error/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/v3/api-docs/**").hasRole(Role.ADMIN.name())
                        .requestMatchers("/management/health").permitAll()
                        .requestMatchers("/management/health/**").permitAll()
                        .requestMatchers("/management/info").permitAll()
                        .requestMatchers("/management/prometheus").permitAll()
                        .requestMatchers("/management/**").hasRole(Role.ADMIN.name())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions ->
                    exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                .addFilterAfter(securityFilter, BasicAuthenticationFilter.class)
                .logout(out -> {
                    out.addLogoutHandler(new CommonLogoutSuccessHandler());
                    out.logoutUrl("/logout");
                    out.invalidateHttpSession(true);
                    out.deleteCookies("JSESSIONID");
                    out.permitAll();
                });
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
