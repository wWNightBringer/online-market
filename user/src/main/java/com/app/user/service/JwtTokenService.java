package com.app.user.service;

import com.app.common.dto.UserDTO;
import com.app.common.enumeration.Role;
import com.app.common.config.security.JwtTokenUtils;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    public String createToken(UserDTO userDTO, Role role) {
        return JwtTokenUtils.getToken(userDTO.email(), userDTO.name(), role.getValue());
    }
}
