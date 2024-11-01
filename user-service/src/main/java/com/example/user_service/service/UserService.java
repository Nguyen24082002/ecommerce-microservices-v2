package com.example.user_service.service;

import com.example.user_service.dto.request.UserCreationRequest;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.entity.Role;
import com.example.user_service.entity.RoleName;
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
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public Mono<User> register(UserCreationRequest request) {
        return Mono.defer(() -> {
            log.info("Registering user {}", request);
            if (userRepository.existsByUsername(request.getUsername())) {
                return Mono.error(new AppException(ErrorCode.EMAIL_EXISTED));
            }
            if (userRepository.existsByEmail(request.getEmail())) {
                return Mono.error(new AppException(ErrorCode.EMAIL_EXISTED));
            }
            if (userRepository.existsByPhoneNumber(request.getPhone())) {
                return Mono.error(new AppException(ErrorCode.PHONE_EXISTED));
            }

            User user = UserMapper.INSTANCE.toUser(request);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(request.getRoles()
                    .stream()
                    .map(role -> roleService.findByName(mapToRoleName(role))
                            .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)))
                    .collect(Collectors.toSet())
            );

            userRepository.save(user);
            return Mono.just(user);
        });
    }

    private RoleName mapToRoleName(String roleName) {
        return switch (roleName) {
            case "ADMIN", "admin", "Admin" -> RoleName.ADMIN;
            case "USER", "user", "User" -> RoleName.USER;
            default -> null;
        };
    }

}
