package web.app.onlinebookshop.dto.role;

import lombok.Data;
import web.app.onlinebookshop.model.Role;

@Data
public class RoleDto {
    private Long id;
    private Role.RoleName name;
}
