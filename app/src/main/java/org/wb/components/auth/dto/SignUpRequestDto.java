package org.wb.components.auth.dto;

import org.wb.components.user.User;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignUpRequestDto {
    @Size(min = 3, max = 64, message = "Username must be within [3; 64]")
    @NotBlank(message = "Username must not be empty")
    @NotNull
    private String username;

    @Size(min = 3, max = 255, message = "Password length must be within [5; 255]")
    @NotBlank(message = "Password must not be empty")
    @NotNull
    private String password;

    @NotNull(message = "Role must not be null")
    private User.Role role;
}
