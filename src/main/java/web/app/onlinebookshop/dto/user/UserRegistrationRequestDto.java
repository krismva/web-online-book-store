package web.app.onlinebookshop.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import web.app.onlinebookshop.validation.FieldMatch;

@Data
@FieldMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class UserRegistrationRequestDto {
    @NotBlank
    @Size(min = 4, max = 50)
    private String email;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    private String repeatPassword;

    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;

    @Size(min = 3, max = 50)
    private String lastName;

    @Size(min = 6, max = 100)
    private String shippingAddress;
}
