package com.example.user_service.validator;

import com.example.user_service.entity.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UserValidator implements ConstraintValidator<UserConstraint, User> {
    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        // Kiểm tra nếu user không null
        if (user == null) {
            return false; // Nếu là null, trả về false
        }

        // Kiểm tra tên người dùng
        if (user.getUsername() == null || user.getUsername().isEmpty() || user.getUsername().length() < 3) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username must be at least 3 characters long.")
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }

        // Kiểm tra mật khẩu
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must be at least 6 characters long.")
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }

        // Kiểm tra ngày sinh
        if (user.getDateOfBirth() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Birthday must not be null.")
                    .addPropertyNode("birthday")
                    .addConstraintViolation();
            return false;
        }

        // Thêm các điều kiện kiểm tra khác nếu cần

        return true; // Trả về true nếu tất cả các điều kiện đều hợp lệ
    }
}
