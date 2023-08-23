package com.app.common.dto;

import com.app.common.constraints.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationDTO {
    private String name;
    private @ValidEmail String email;
    private String password;

}
