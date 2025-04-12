package com.matt.dto.request;

import com.matt.enums.UserSearchTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO representing the user credentials used for login")
public class LoginRequestDTO {
    @NotEmpty(message = "Login Type is required.")
    @Schema(description = "Login type of the request",
            example = "EMAIL",
            allowableValues = {"EMAIL", "USERNAME"})
    private UserSearchTypeEnum loginType;

    @Schema(description = "User's name", example = "example")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain letters and digits")
    @Size(min = 1, max = 20, message = "Username must be between 1 and 20 characters")
    private String username;

    @Schema(description = "User's email", example = "example@gmail.com")
    @Email(message = "Email format is invalid")
    @Size(min = 5, max = 20, message = "Email must be between 5 and 20 characters")
    private String email;

    @NotEmpty(message = "Password is required.")
    @Size(min = 2, max = 100, message = "The length of password must be between 2 and 100 characters.")
    @Schema(description = "The password for the user", example = "password123")
    private String password;
}
