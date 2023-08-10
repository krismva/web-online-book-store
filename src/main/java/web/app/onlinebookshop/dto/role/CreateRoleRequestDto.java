package web.app.onlinebookshop.dto.role;

import lombok.Data;
import web.app.onlinebookshop.model.Role;

@Data
public class CreateRoleRequestDto {
    private Role.RoleName name;
}
