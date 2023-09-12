package com.app.order.util;

import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtil {

    private SecurityUtil() {
    }

    public static String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
