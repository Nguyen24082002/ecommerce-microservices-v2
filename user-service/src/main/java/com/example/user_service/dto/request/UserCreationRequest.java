package com.example.user_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest implements Serializable {
    @NotBlank(message = "The username must not be left blank")
    @Size(min = 6, max = 50, message = "The username must be 6 characters or more")
    String username;

    @Size(min = 8, max = 50, message = "Password must be  between 8 and 50 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "Password must contain all uppercase and lowercase letters and numbers")
    String password;

    @NotBlank(message = "The fullname must not be left blank")
    @Size(min = 6, max = 50, message = "The username must be 6 characters or more")
    String fullName;

    @Size(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", message = "Invalid email format")
    String email;

    @NotBlank(message = "Gender must not be left blank")
    String gender;

    @Size(min = 10, max = 11, message = "Phone number must be between 10 and 11 digits")
    @Pattern(regexp = "^\\+84[0-9]{9,10}$|^0[0-9]{9,10}$", message = "The phone number is not in the correct format")
    String phone;

    @Pattern(regexp = "^(http|https)://.*$", message = "Avatar URL must be a valid HTTP or HTTPS URL")
    String avatar;

    Set<String> roles;
}
