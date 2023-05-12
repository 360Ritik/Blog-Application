package com.example.demo.blogapplication.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String name;
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])(?=.*[A-Z])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@$!%*#?&)")
    private String password;

    @Email(message = "it should be email type")
    private String email;

    private String confirmPassword;

    // Custom validation method for password and confirmPassword
    @AssertTrue(message = "Password and Confirm Password must match")
    public boolean isPasswordConfirmed() {
        return password != null && password.equals(confirmPassword);
    }
}