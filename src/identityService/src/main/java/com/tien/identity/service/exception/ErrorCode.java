package com.tien.identity.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username requires more than {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "Password requires more than {min} characters", HttpStatus.BAD_REQUEST),
    FIELD_INVALID(1004, "Invalid field", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "no login status", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(403, "No right to access these resources", HttpStatus.FORBIDDEN),
    DOB_INVALID(1003, "Age must be greater than {min}", HttpStatus.BAD_REQUEST);

    private int code;
    private HttpStatusCode statusCode;
    private String message;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
    }
}
