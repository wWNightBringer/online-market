package com.app.user.service;

import static com.app.user.util.mapper.UserMapper.USER_MAPPER;

import com.app.common.dto.TokenDTO;
import com.app.common.dto.UserAuthenticationDTO;
import com.app.common.dto.UserDTO;
import com.app.common.enumeration.Exception;
import com.app.common.exception.PasswordException;
import com.app.user.domain.User;
import com.app.user.util.ArgonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenService jwtTokenService;
    private final UserService userService;

    public TokenDTO login(UserAuthenticationDTO authenticationDTO) {
        User user = userService.getUser(authenticationDTO.email());
        verifyPassword(user, authenticationDTO.password());

        UserDTO userDTO = USER_MAPPER.map(user);
        String token = jwtTokenService.createToken(userDTO, user.getRole());

        return new TokenDTO(token);
    }

    private static void verifyPassword(User user, String password) {
        if (!ArgonUtil.matchesUserPassword(password, user.getPassword())) {
            throw new PasswordException(Exception.PASSWORD_INCORRECT.name());
        }
    }
}
