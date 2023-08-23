package com.app.user.service;

import static com.app.user.util.mapper.UserMapper.USER_MAPPER;

import com.app.common.dto.UserDTO;
import com.app.common.dto.UserRegistrationDTO;
import com.app.common.enumeration.Exception;
import com.app.user.domain.User;
import com.app.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDTO registerUser(UserRegistrationDTO userRegistrationDTO) {
        User user = userRepository.save(USER_MAPPER.map(userRegistrationDTO));

        return USER_MAPPER.map(user);
    }

    @Transactional(readOnly = true)
    public User getUser(String email) {
        return userRepository.findUserByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException(Exception.USER_NOT_FOUND.name()));
    }

    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findUserByEmail(email)
            .orElseThrow(() -> new AccessDeniedException("Email is incorrect"));

        userRepository.deleteById(user.getId());
    }
}
