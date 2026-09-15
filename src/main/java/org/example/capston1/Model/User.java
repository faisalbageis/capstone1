package org.example.capston1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    @NotEmpty(message = "user id can not be empty")
    @Pattern(regexp = "U[0-9]*",message = "user id must start with U")
    private String id;

    @NotEmpty(message = "username can not be empty")
    @Size(min = 5,message = "username length must be more than 5")
    private String username;

    @NotEmpty(message = "password cannot be empty")
    @Size(min = 6,message = "password length must be more than 6")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",message = "password must contain characters and digits")
    private String password;

    @NotEmpty(message = "email can not be empty")
    @Email(message = "invalid email")
    private String email;

    @NotEmpty(message = "role can not be empty")
    @Pattern(regexp = "Admin|Customer",message = "role must be ether Admin or customer")
    private String role;

    @NotNull(message = "balance can not be empty")
    @Positive(message = "balance can not be negative")
    private double balance;
}
