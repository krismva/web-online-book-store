package web.app.onlinebookshop.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import web.app.onlinebookshop.dto.user.UserDto;
import web.app.onlinebookshop.dto.user.UserRegisteredResponseDto;
import web.app.onlinebookshop.dto.user.UserRegistrationRequestDto;
import web.app.onlinebookshop.exception.RegistrationException;

public interface UserService {
    UserRegisteredResponseDto register(
            UserRegistrationRequestDto registrationRequest)
            throws RegistrationException;

    List<UserDto> findAll(Pageable pageable);
}
