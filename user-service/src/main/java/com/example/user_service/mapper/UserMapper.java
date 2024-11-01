package com.example.user_service.mapper;

import com.example.user_service.dto.request.UserCreationRequest;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.entity.Role;
import com.example.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "imageUrl", source = "avatar")
    User toUser(UserCreationRequest userCreationRequest);

    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))") // Đảm bảo gọi getRoles()
    @Mapping(target = "avatar", source = "imageUrl")
    UserResponse toUserResponse(User user);

    default Set<String> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }
}
