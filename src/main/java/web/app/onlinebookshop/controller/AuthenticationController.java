package web.app.onlinebookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.onlinebookshop.dto.user.UserLoginRequestDto;
import web.app.onlinebookshop.dto.user.UserLoginResponseDto;
import web.app.onlinebookshop.dto.user.UserRegisteredResponseDto;
import web.app.onlinebookshop.dto.user.UserRegistrationRequestDto;
import web.app.onlinebookshop.exception.RegistrationException;
import web.app.onlinebookshop.security.AuthenticationService;
import web.app.onlinebookshop.service.UserService;

@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for login and register user")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);

    }

    @PostMapping("/register")
    @Operation(summary = "Register user")
    public UserRegisteredResponseDto register(@RequestBody @Valid
                                                  UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }
}
