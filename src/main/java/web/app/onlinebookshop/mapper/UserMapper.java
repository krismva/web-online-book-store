package web.app.onlinebookshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import web.app.onlinebookshop.config.MapperConfig;
import web.app.onlinebookshop.dto.user.UserDto;
import web.app.onlinebookshop.dto.user.UserRegisteredResponseDto;
import web.app.onlinebookshop.dto.user.UserRegistrationRequestDto;
import web.app.onlinebookshop.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);

    UserRegisteredResponseDto toRegisteredDto(User user);

    @Mapping(target = "id", ignore = true)
    User toModel(UserRegistrationRequestDto requestDto);
}
