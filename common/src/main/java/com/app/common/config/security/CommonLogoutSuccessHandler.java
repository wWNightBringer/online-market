package com.app.common.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class CommonLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutHandler {

    private final Logger log = LoggerFactory.getLogger(CommonLogoutSuccessHandler.class);

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("User was logout successfully");
    }
}
