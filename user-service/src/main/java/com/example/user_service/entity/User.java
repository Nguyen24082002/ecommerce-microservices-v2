package com.example.user_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user")
public class User extends AbstractMappedEntity implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", length = 36, updatable = false, nullable = false)
    String id;

    @Column(name = "username", unique = true, length = 50)
    String username;

    @Column(name = "password", unique = true)
    String password;

    @Column(name = "email", unique = true, length = 50)
    String email;

    @Column(name = "first_name", nullable = false, length = 50)
    String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    String lastName;

    @Column(name = "date_of_birth", nullable = false)
    LocalDate dateOfBirth;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    Set<Role> roles;

}
