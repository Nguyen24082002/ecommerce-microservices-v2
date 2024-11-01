package com.example.user_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name = "role")
public class Role extends AbstractMappedEntity implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue
    String id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    String name;

    @Column(name = "description", length = 255)
    String description;

    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    Set<Permission> permissions;

    @ManyToMany(mappedBy = "roles")
    Set<User> users;

}
