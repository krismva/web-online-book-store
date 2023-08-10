package web.app.onlinebookshop.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.app.onlinebookshop.dto.role.CreateRoleRequestDto;
import web.app.onlinebookshop.dto.role.RoleDto;
import web.app.onlinebookshop.mapper.RoleMapper;
import web.app.onlinebookshop.model.Role;
import web.app.onlinebookshop.repository.role.RoleRepository;
import web.app.onlinebookshop.service.RoleService;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto save(CreateRoleRequestDto requestDto) {
        Role role = roleMapper.toModel(requestDto);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toDto)
                .toList();
    }

    @Override
    public Role getRoleByName(Role.RoleName roleName) {
        return roleRepository.findRoleByName(roleName).orElseThrow(() ->
                new RuntimeException("can't find role by roleName: " + roleName));
    }
}
