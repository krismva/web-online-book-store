package web.app.onlinebookshop.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.app.onlinebookshop.dto.user.UserDto;
import web.app.onlinebookshop.dto.user.UserRegisteredResponseDto;
import web.app.onlinebookshop.dto.user.UserRegistrationRequestDto;
import web.app.onlinebookshop.exception.EntityNotFoundException;
import web.app.onlinebookshop.exception.RegistrationException;
import web.app.onlinebookshop.mapper.UserMapper;
import web.app.onlinebookshop.model.Role;
import web.app.onlinebookshop.model.ShoppingCart;
import web.app.onlinebookshop.model.User;
import web.app.onlinebookshop.repository.cart.ShoppingCartRepository;
import web.app.onlinebookshop.repository.user.UserRepository;
import web.app.onlinebookshop.service.RoleService;
import web.app.onlinebookshop.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public UserRegisteredResponseDto register(
            UserRegistrationRequestDto registrationRequest)
            throws RegistrationException {
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new RegistrationException("This email already exist");
        }
        User user = userMapper.toModel(registrationRequest);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRoles(new HashSet<>(Set.of(roleService.getRoleByName(Role.RoleName.ROLE_USER))));
        User savedUser = userRepository.save(user);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(savedUser);
        shoppingCartRepository.save(shoppingCart);
        return userMapper.toRegisteredDto(savedUser);
    }

    @Override
    public List<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
                new EntityNotFoundException("Can't find user by name "
                        + authentication.getName()));
    }
}
