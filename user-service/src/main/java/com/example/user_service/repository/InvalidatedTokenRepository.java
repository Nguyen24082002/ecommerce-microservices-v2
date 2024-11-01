package com.example.user_service.repository;

import com.example.user_service.entity.InvalidatedToken;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends R2dbcRepository<InvalidatedToken, String> {

}
