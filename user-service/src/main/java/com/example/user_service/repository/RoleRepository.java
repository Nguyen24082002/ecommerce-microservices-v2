package com.example.user_service.repository;

import com.example.user_service.entity.Role;
import com.example.user_service.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Optional<Role> findByName(@Param("name") RoleName name);

    @Query("SELECT u.roles FROM User u WHERE u.id = :id")
    List<Role> findByUserId(@Param("id") Long id);

}
