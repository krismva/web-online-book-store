package web.app.onlinebookshop.service;

import java.util.List;
import web.app.onlinebookshop.dto.role.CreateRoleRequestDto;
import web.app.onlinebookshop.dto.role.RoleDto;
import web.app.onlinebookshop.model.Role;

public interface RoleService {
    RoleDto save(CreateRoleRequestDto requestDto);

    List<RoleDto> findAll();

    Role getRoleByName(Role.RoleName roleName);
}
