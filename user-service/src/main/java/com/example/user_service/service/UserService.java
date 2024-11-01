package com.example.user_service.service;

import com.example.user_service.constant.PredefinedRole;
import com.example.user_service.dto.request.UserCreationRequest;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.entity.Role;
import com.example.user_service.entity.User;
import com.example.user_service.exception.AppException;
import com.example.user_service.exception.ErrorCode;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.RoleRepository;
import com.example.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    public Mono<UserResponse> createUser(UserCreationRequest request) {
        return userRepository.existsByUsername(request.getUsername())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new AppException(ErrorCode.USER_EXISTED));
                    }
                    User user = UserMapper.INSTANCE.toUser(request);
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    HashSet<Role> roles = new HashSet<>();

                    return roleRepository.findByName(PredefinedRole.USER_ROLE)
                            .doOnNext(roles::add) // Thêm vai trò vào tập hợp
                            .then(Mono.just(user))
                            .doOnNext(u -> u.setRoles(roles))
                            .flatMap(userRepository::save)
                            .map(UserMapper.INSTANCE::toUserResponse);
                });
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.id")
    public Mono<UserResponse> getUser(String userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.USER_NOT_FOUND)))
                .map(UserMapper.INSTANCE::toUserResponse);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Mono<List<UserResponse>> getUsers() {
        return userRepository.findAll()
                .map(UserMapper.INSTANCE::toUserResponse)
                .collectList();
    }


}
