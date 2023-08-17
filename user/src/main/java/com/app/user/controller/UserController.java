package com.app.user.controller;

import com.app.common.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping
    public List<UserDto> getUsers() {
        return List.of(new UserDto("Andrew", "andrew@gmail.com"), new UserDto("Anton", "Her@gmail.com"));
    }
}
