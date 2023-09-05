package com.app.order.config.security;

import static com.app.common.config.security.JwtTokenUtils.getToken;
import static com.app.common.config.security.JwtTokenUtils.hasAuthorizationBearer;

import com.app.common.config.security.SecurityJwtUtils;
import com.app.common.dto.UserDTO;
import com.app.common.enumeration.Role;
import com.app.common.enumeration.SystemEnum;
import com.app.order.client.UserClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends GenericFilterBean {

    private final UserClient userClient;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String requestHeader = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (hasAuthorizationBearer(requestHeader)) {
            chain.doFilter(request, response);
            return;
        }

        String token = getToken(requestHeader);
        SecurityJwtUtils jwtUtils = new SecurityJwtUtils();

        Jwt jwt = jwtUtils.decodeAccessToken(token);

        Map<String, Object> claims = jwt.getClaims();
        var email = (String) claims.get(SystemEnum.EMAIL_PARAM.getValue());

        UserDTO userDTO = userClient.getUserByEmail(email, token);

        UserDetails userDetails = buildUserDetails(userDTO);

        setAuthenticationContext(userDetails);
        chain.doFilter(request, response);
    }

    public static void setAuthenticationContext(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication
            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static UserDetails buildUserDetails(UserDTO userDTO) {
        String role = (userDTO.name().equals("Admin")) ? Role.ADMIN.getValue() : Role.CUSTOMER.getValue();
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        return new User(userDTO.email(), "", authorities);
    }
}
