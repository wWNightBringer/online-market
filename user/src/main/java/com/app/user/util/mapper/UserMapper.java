package com.app.user.util.mapper;

import com.app.common.dto.UserDTO;
import com.app.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDTO map(User user);

    User map(UserDTO userDTO);
}
