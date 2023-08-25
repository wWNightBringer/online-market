package com.app.user.config.security;

import static com.app.user.Constants.EMAIL;
import static com.app.user.Constants.NAME;
import static com.app.user.Constants.ROLE;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.app.common.enumeration.Role;
import com.app.common.security.JwtTokenUtils;
import com.app.user.domain.User;
import com.app.user.service.UserService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SecurityFilter.class})
class SecurityFilterTest {

    @Autowired
    private SecurityFilter securityFilter;

    @MockBean
    private UserService userService;

    @Test
    void test_doFilter() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        when(userService.getUser(anyString())).thenReturn(buildUser());

        String bearerToken = "Bearer " + JwtTokenUtils.getToken(EMAIL, NAME, ROLE);
        request.addHeader(HttpHeaders.AUTHORIZATION, bearerToken);

        securityFilter.doFilter(request, response, chain);

        Assertions.assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    private User buildUser() {
        return User.builder()
            .name(NAME)
            .password("")
            .email(EMAIL)
            .role(Role.CUSTOMER)
            .build();
    }
}
