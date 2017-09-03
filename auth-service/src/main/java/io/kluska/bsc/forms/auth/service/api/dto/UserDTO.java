package io.kluska.bsc.forms.auth.service.api.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Mateusz Kluska
 */
@Data
public class UserDTO {
    @NotNull(message = "Username is required")
    @Size(min = 3, max = 15, message = "Username length must be between 3 and 15 characters")
    private String username;
    @NotNull(message = "Password is required")
    @Size(min = 6, max = 15, message = "Password length must be between 6 and 15 characters")
    private String password;
    @NotNull(message = "Email is required")
    @Email(message = "Incorrect email")
    private String email;
}
