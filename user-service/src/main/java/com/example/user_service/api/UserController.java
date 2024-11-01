package com.example.user_service.api;

import com.example.user_service.dto.ApiResponse;
import com.example.user_service.dto.request.UserCreationRequest;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.exception.AppException;
import com.example.user_service.exception.ErrorCode;
import com.example.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping("/register")
    public Mono<ApiResponse<UserResponse>> register(@Valid @RequestBody UserCreationRequest request) {
        return userService.createUser(request)
                .map(userResponse -> ApiResponse.<UserResponse>builder()
                        .result(userResponse)
                        .build()
                )
                .onErrorResume(error -> Mono.just(
                        new ApiResponse<>(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode(), error.getMessage(), null)
                ));

    }

}
