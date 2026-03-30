package com.tien.identity.service.exception;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.ConstraintViolation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tien.identity.service.dto.request.ApiResponse;

@ControllerAdvice
public class ExceptionGlobalHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException runtimeException) {
        var apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    // xu ly exception bang message cua anootation
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        var enumKey = methodArgumentNotValidException.getFieldError().getDefaultMessage();
        var errorCode = ErrorCode.FIELD_INVALID;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);

            var constraintViolation = methodArgumentNotValidException
                    .getBindingResult()
                    .getAllErrors()
                    .getFirst()
                    .unwrap(ConstraintViolation.class);

            // lay tham so dieu kien annotation
            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
        } catch (IllegalArgumentException e) {

        }

        var apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(
                Objects.nonNull(attributes)
                        ? MapAttributes(errorCode.getMessage(), attributes)
                        : errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }
    // *note: su dung AccessDeniedException cua spring security
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException accessDeniedException) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        System.out.println("author");
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    ResponseEntity<ApiResponse> handlingAuthenticationdException(AuthenticationException accessDeniedException) {
        System.out.println("authen");
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        var apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    private String MapAttributes(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
