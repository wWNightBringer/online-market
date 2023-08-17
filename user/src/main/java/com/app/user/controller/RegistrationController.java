package com.app.user.controller;

import com.app.common.dto.UserDTO;
import com.app.common.dto.UserRegistrationDTO;
import com.app.user.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserDTO userDTO = registrationService.saveUser(userRegistrationDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @DeleteMapping
    public void deleteUser(@PathVariable String email){
        registrationService.deleteUser(email);
    }
}
