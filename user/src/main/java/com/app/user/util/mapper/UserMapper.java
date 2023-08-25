package com.app.user.util.mapper;

import com.app.common.dto.UserDTO;
import com.app.common.dto.UserRegistrationDTO;
import com.app.common.enumeration.Role;
import com.app.user.domain.User;
import com.app.user.util.ArgonUtil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    default UserDTO map(User user) {
        return new UserDTO(
            user.getName(),
            user.getEmail()
        );
    }

    default User map(UserRegistrationDTO userDTO) {
        return User.builder()
            .name(userDTO.name())
            .email(userDTO.email())
            .password(ArgonUtil.hashPassword(userDTO.password()))
            .role(Role.CUSTOMER)
            .build();
    }
}
