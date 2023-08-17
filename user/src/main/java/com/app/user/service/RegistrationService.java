package com.app.user.service;

import com.app.common.dto.UserDTO;
import com.app.user.domain.User;
import com.app.user.dto.UserRegistrationDTO;
import com.app.user.repository.RoleRepository;
import com.app.user.repository.UserRepository;
import com.app.user.util.ArgonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserDTO saveUser(UserRegistrationDTO userRegistrationDTO) {
        User save = userRepository.save(buildUser(userRegistrationDTO));

        return getUserDTO(save);
    }

    @Transactional
    public void deleteUser(String login, String password) {
        User user = getUserIfEmailAndPasswordAreCorrect(login, password);
        userRepository.deleteById(user.getId());
    }

    private UserDTO getUserDTO(User save) {
        return new UserDTO(save.getName(), save.getEmail());
    }

    private User buildUser(UserRegistrationDTO userDTO) {
        return User.builder()
            .name(userDTO.getName())
            .email(userDTO.getEmail())
            .password(ArgonUtil.hashPassword(userDTO.getPassword()))
            .role(roleRepository.findRoleByRoleName("USER"))
            .build();
    }

    private User getUserIfEmailAndPasswordAreCorrect(String email, String password) {
        Optional<User> optionalUser = userRepository.findUserByEmailOptional(email);

        optionalUser.ifPresent(user -> {
            boolean equals = ArgonUtil.matchesUserPassword(password, user.getPassword());
            if (!equals) {
                throw new AccessDeniedException("Password is incorrect");
            }
        });
        return optionalUser.orElseThrow(() -> new AccessDeniedException("Login is incorrect"));
    }
}
