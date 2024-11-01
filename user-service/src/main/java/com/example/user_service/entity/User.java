package com.example.user_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "unique_username", columnNames = "username"),
        @UniqueConstraint(name = "unique_email", columnNames = "email"),
        @UniqueConstraint(name = "unique_phone", columnNames = "phone_number")
})
public class User extends AbstractMappedEntity implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    String id;

    @NotBlank(message = "User name must not be blank")
    @Size(min = 3, max = 50, message = "Full name must be betw 3 and 100 characters")
    @Column(name = "username", unique = true, length = 50)
    String username;

    @JsonIgnore
    @NotNull(message = "Password must not be null")
    @Size(min = 6, max = 100, message = "Password mút be between 6  and 100 characters")
    @Column(name = "password")
    String password;

    @NaturalId // thuộc tính này không thay đổi trong suốt vòng đời của đối tượng
    @NotBlank
    @Size(max = 50)
    @Email(message = "Input must be in Email format")
    @Column(name = "email", unique = true, length = 50)
    String email;

    @NotBlank(message = "Full name must not be blank")
    @Size(min = 3, max = 100, message = "Full name must be beetween 3 and 100 characters")
    @Column(name = "full_name")
    String fullName;

    @NotBlank(message = "Gender must not be blank")
    @Column(name = "gender", nullable = false)
    String gender;

    @Pattern(regexp = "^\\+84[0-9]{9,10}$|^0[0-9]{9,10}$", message = "The phone number is not in the correct format")
    @Size(min = 10, max = 11, message = "Phone number must be between 1- and 11 characters")
    @Column(name = "phone_number", unique = true)
    String phoneNumber;

    @Pattern(regexp = "^(http|https)://.*$", message = "Avatar URL must be a valid HTTP or HTTPS URL")
    @Lob
    @Column(name = "image_url")
    String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    Set<Role> roles;

}
