package com.app.common.dto;

import com.app.common.constraints.ValidEmail;

public record UserAuthenticationDTO(@ValidEmail String email, String password) {

}
