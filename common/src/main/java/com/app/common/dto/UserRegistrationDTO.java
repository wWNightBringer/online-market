package com.app.common.dto;

import com.app.common.constraints.ValidEmail;

public record UserRegistrationDTO(String name, @ValidEmail String email, String password) {

}
