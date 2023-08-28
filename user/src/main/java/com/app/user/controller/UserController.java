package com.app.user.controller;

import com.app.common.dto.UserDTO;
import com.app.common.dto.UserRegistrationDTO;
import com.app.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.app.user.util.mapper.UserMapper.USER_MAPPER;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
            UserDTO userDTO = userService.registerUser(userRegistrationDTO);
            return ResponseEntity.created(URI.create("/api/v1/users")).body(userDTO);
    }

    @DeleteMapping("{email}")
    public void deleteUser(@PathVariable(name = "email") String email) {
        userService.deleteUser(email);
    }

    @GetMapping("{email}")
    public UserDTO getUserByEmail(@PathVariable(name = "email") String email) {
        return USER_MAPPER.map(userService.getUser(email));
    }
}
