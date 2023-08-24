package com.app.common.security;

import static com.app.common.enumeration.SystemEnum.EMAIL_PARAM;
import static com.app.common.enumeration.SystemEnum.NAME_PARAM;
import static com.app.common.enumeration.SystemEnum.ROLE_PARAM;
import static com.app.common.security.SecurityUtils.JWT_ALGORITHM;

import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class JwtTokenUtils {

    private JwtTokenUtils() {
    }

    public static String getToken(String email, String name, String role) {
        return buildToken(email, name, role).getTokenValue();
    }

    private static Jwt buildToken(String email, String name, String role) {
        LocalDateTime expiredTime = LocalDateTime.now().plusDays(1);

        JwtClaimsSet jwtClaims = JwtClaimsSet.builder()
            .claims(c -> buildClaims(c, email, name, role))
            .id(UUID.randomUUID().toString())
            .subject(email)
            .expiresAt(expiredTime.toInstant(ZoneOffset.UTC))
            .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(jwsHeader, jwtClaims);

        return new SecurityJwtUtils().jwtEncoder().encode(parameters);
    }

    private static void buildClaims(Map<String, Object> claims, String email, String name, String role) {
        claims.put(EMAIL_PARAM.getValue(), email);
        claims.put(ROLE_PARAM.getValue(), role);
        claims.put(NAME_PARAM.getValue(), name);
    }

    public static String getToken(String header) {
        return header.split(" ")[1].trim();
    }

    public static boolean hasAuthorizationBearer(String header) {
        return Objects.isNull(header) || !header.startsWith("Bearer ");
    }
}
