package com.example.user_service.dto.request;

import com.example.user_service.validator.UserConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@UserConstraint
public class UserCreationRequest implements Serializable {
    String username;

    String password;

    String email;

    String firstName;

    String lastName;

    LocalDate dateOfBirth;
}
