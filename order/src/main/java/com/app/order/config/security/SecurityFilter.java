package com.app.order.config.security;

import static com.app.common.security.JwtTokenUtils.getToken;
import static com.app.common.security.JwtTokenUtils.hasAuthorizationBearer;

import com.app.common.enumeration.SystemEnum;
import com.app.common.security.SecurityJwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends GenericFilterBean {

   // private final UserService userService;

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

       // com.app.user.domain.User user = userService.getUser(email);

       // UserDetails userDetails = buildUserDetails(user);

       // setAuthenticationContext(userDetails);
        chain.doFilter(request, response);
    }

    public static void setAuthenticationContext(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication
            = new UsernamePasswordAuthenticationToken(userDetails, null, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

//    private static UserDetails buildUserDetails(com.app.user.domain.User user) {
//        List<SimpleGrantedAuthority> authorities =
//            Stream.of(Role.values())
//                .filter(r -> r.equals(user.getRole()))
//                .map(role -> new SimpleGrantedAuthority(role.getValue()))
//                .toList();
//
//        return new User(user.getEmail(), user.getPassword(), authorities);
//    }
}
