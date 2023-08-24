package com.app.common.security;

import static com.app.common.security.SecurityUtils.JWT_ALGORITHM;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class SecurityJwtConfigurationTest {

    @Test
    void test_jwtEncoder() {
        User user = new User("test", "testPassword", new HashSet<>());
        Authentication authentication = mock(Authentication.class);

        when(authentication.getPrincipal()).thenReturn(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        LocalDateTime expiredTime = LocalDateTime.now().plusHours(2);

        JwtClaimsSet jwtClaims = JwtClaimsSet.builder()
            .claims(SecurityJwtConfigurationTest::buildClaims)
            .id(UUID.randomUUID().toString())
            .subject(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
            .expiresAt(expiredTime.toInstant(ZoneOffset.UTC))
            .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(jwsHeader, jwtClaims);
        SecurityJwtUtils jwtConfiguration = new SecurityJwtUtils();
        Assertions.assertFalse(jwtConfiguration.jwtEncoder().encode(parameters).getTokenValue().isEmpty());
    }

    private static Map<String, Object> buildClaims(Map<String, Object> claims) {
        claims.put("email", "test@gmail.com");
        claims.put(JwtClaimNames.SUB, "test");
        return claims;
    }
}
