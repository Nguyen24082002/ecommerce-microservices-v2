package com.example.user_service.config;

import java.util.HashSet;

import com.example.user_service.constant.PredefinedRole;
import com.example.user_service.entity.Role;
import com.example.user_service.entity.User;
import com.example.user_service.repository.RoleRepository;
import com.example.user_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.r2dbc.Driver") // R2DBC driver
    CommandLineRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> userRepository.findByUsername(ADMIN_USER_NAME)
                .switchIfEmpty(
                        roleRepository.save(Role.builder()
                                        .name(PredefinedRole.USER_ROLE)
                                        .description("User role")
                                        .build())
                                .then(roleRepository.save(Role.builder()
                                                .name(PredefinedRole.ADMIN_ROLE)
                                                .description("Admin role")
                                                .build())
                                        .flatMap(adminRole -> {
                                            var roles = new HashSet<Role>();
                                            roles.add(adminRole);

                                            User user = User.builder()
                                                    .username(ADMIN_USER_NAME)
                                                    .password(passwordEncoder.encode(ADMIN_PASSWORD))
                                                    .roles(roles)
                                                    .build();

                                            return userRepository.save(user)
                                                    .doOnSuccess(savedUser -> log.warn("admin user has been created with default password: admin, please change it"));
                                        })
                                ).subscribe(
                                        success -> log.info("Application initialization completed ....."),
                                        error -> log.error("Failed to initialize application: ", error)
                                );
    }
}
