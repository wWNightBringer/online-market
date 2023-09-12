package com.app.common.config.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecurityJwtUtils {

    private static final String JWT_KEY =
        "NmE5OTNhYzAwNzc1MjVjZjM3MjQ2MjRlMTZmZGZhNzUyOThkYWZmZjBjMTQxMGI4MTkzYTU4MjM4ZjM0ODgyOWUwNjhjY" +
            "mQyODFjYzc2NmRhOTQzMDc0NjQxMzhlZDQ1MGY0NjVlYWRmYjcyMDdiMzk2ODlhZDQyMDQwMjE4ZjY=";

    public Jwt decodeAccessToken(String token) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder
            .withSecretKey(getSecretKey())
            .macAlgorithm(SecurityUtils.JWT_ALGORITHM)
            .build();

        SecurityMetersService metersService = new SecurityMetersService(new CompositeMeterRegistry());

        try {
            return jwtDecoder.decode(token);
        } catch (Exception e) {
            if (e.getMessage().contains("Invalid signature")) {
                metersService.trackTokenInvalidSignature();
            } else if (e.getMessage().contains("Jwt expired at")) {
                metersService.trackTokenExpired();
            } else if (e.getMessage().contains("Invalid JWT serialization")) {
                metersService.trackTokenMalformed();
            } else if (e.getMessage().contains("Invalid unsecured/JWS/JWE")) {
                metersService.trackTokenMalformed();
            }
            throw e;
        }
    }

    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName(SecurityUtils.AUTHORITIES_KEY);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(JWT_KEY).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, SecurityUtils.JWT_ALGORITHM.getName());
    }
}
