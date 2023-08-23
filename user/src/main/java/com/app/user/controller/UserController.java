package com.app.user.controller;

import com.app.common.dto.UserDTO;
import com.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @DeleteMapping("{email}")
    public void deleteUser(@PathVariable(name = "email") String email){
        userService.deleteUser(email);
    }

    @GetMapping("{email}")
    public UserDTO getUserByEmail(@PathVariable(name = "email") String email){
        return userService.getUserByEmail(email);
    }
}
