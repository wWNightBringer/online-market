package com.app.user.service;

import com.app.common.dto.UserDTO;
import com.app.common.dto.UserRegistrationDTO;
import com.app.common.enumeration.Role;
import com.app.user.domain.User;
import com.app.user.repository.UserRepository;
import com.app.user.util.ArgonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.app.user.util.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;

    public UserDTO saveUser(UserRegistrationDTO userRegistrationDTO) {
        User user = userRepository.save(buildUser(userRegistrationDTO));

        return USER_MAPPER.map(user);
    }

    private User buildUser(UserRegistrationDTO userDTO) {
        return User.builder()
            .name(userDTO.getName())
            .email(userDTO.getEmail())
            .password(ArgonUtil.hashPassword(userDTO.getPassword()))
            .role(Role.CUSTOMER)
            .build();
    }
}
