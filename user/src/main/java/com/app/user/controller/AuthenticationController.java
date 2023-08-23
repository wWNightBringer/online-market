package com.app.user.controller;

import com.app.common.dto.TokenDTO;
import com.app.common.dto.UserAuthenticationDTO;
import com.app.user.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserAuthenticationDTO authenticationDTO) {
        var tokenDto = authenticationService.login(authenticationDTO);
        return ResponseEntity.ok(tokenDto);
    }
}
