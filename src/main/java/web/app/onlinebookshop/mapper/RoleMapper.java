package web.app.onlinebookshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import web.app.onlinebookshop.config.MapperConfig;
import web.app.onlinebookshop.dto.role.CreateRoleRequestDto;
import web.app.onlinebookshop.dto.role.RoleDto;
import web.app.onlinebookshop.model.Role;

@Mapper(config = MapperConfig.class)
public interface RoleMapper {
    RoleDto toDto(Role role);

    @Mapping(target = "id", ignore = true)
    Role toModel(CreateRoleRequestDto requestDto);
}
