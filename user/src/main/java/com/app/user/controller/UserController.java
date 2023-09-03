package com.app.user.controller;

import static com.app.user.util.mapper.UserMapper.USER_MAPPER;

import com.app.common.dto.UserDTO;
import com.app.common.dto.UserRegistrationDTO;
import com.app.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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

    @GetMapping("{id}")
    public UserDTO getUserId(@PathVariable("id") int id) {
        return userService.getUserIdById(id);
    }
}
