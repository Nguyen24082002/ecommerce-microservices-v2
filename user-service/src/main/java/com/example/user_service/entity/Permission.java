package com.example.user_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permission")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission extends AbstractMappedEntity implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    String id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    String name;

    @Column(name = "description", length = 255)
    String description;

    @ManyToMany(mappedBy = "permissions")
    Set<Role> roles;
}
