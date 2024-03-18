package com.clockin.app.common.mapper;

import com.clockin.app.user.User;
import com.clockin.app.user.dto.UserRequestDTO;
import com.clockin.app.user.dto.UserResponseDTO;
import org.mapstruct.Mapper;

/**
 * This interface defines methods for mapping between the {@link User} and {@link UserResponseDTO} and {@link UserRequestDTO}classes.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO userToUserResponseDTO(User user);
}
