package com.example.user_service.repository;

import com.example.user_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends R2dbcRepository<Permission, String> {
}
