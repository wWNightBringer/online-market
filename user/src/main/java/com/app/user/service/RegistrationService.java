package com.app.user.service;

import com.app.common.dto.UserDTO;
import com.app.common.dto.UserRegistrationDTO;
import com.app.common.enumeration.Role;
import com.app.user.domain.User;
import com.app.user.repository.UserRepository;
import com.app.user.util.ArgonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;

    public UserDTO saveUser(UserRegistrationDTO userRegistrationDTO) {
        User save = userRepository.save(buildUser(userRegistrationDTO));

        return getUserDTO(save);
    }

    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("Email is incorrect"));
        userRepository.deleteById(user.getId());
    }

    private UserDTO getUserDTO(User save) {
        return new UserDTO(save.getName(), save.getEmail());
    }

    private User buildUser(UserRegistrationDTO userDTO) {
        return User.builder()
            .name(userDTO.name())
            .email(userDTO.email())
            .password(ArgonUtil.hashPassword(userDTO.password()))
            .role(Role.CUSTOMER)
            .build();
    }
}
