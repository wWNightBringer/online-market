package com.app.user.service;

import com.app.common.dto.UserDTO;
import com.app.user.domain.User;
import com.app.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import static com.app.user.util.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void deleteUser(String email) {
        User user = getUser(email);
        userRepository.deleteById(user.getId());
    }

    public UserDTO getUserByEmail(String email){
        return USER_MAPPER.map(getUser(email));
    }
    private User getUser(String email) {
        return userRepository.findUserByEmail(email)
            .orElseThrow(() -> new AccessDeniedException("Email is incorrect"));
    }
}
