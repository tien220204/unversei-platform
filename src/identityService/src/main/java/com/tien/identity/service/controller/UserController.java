package com.tien.identity.service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.tien.identity.service.dto.request.ApiResponse;
import com.tien.identity.service.dto.request.UserCreationRequest;
import com.tien.identity.service.dto.request.UserUpdateRequest;
import com.tien.identity.service.dto.response.UserResponse;
import com.tien.identity.service.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/createUser")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createRequest(request))
                .build();
    }

    @GetMapping("/findAll")
    ApiResponse<List<UserResponse>> selectUser() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/findById/{id}")
    ApiResponse<UserResponse> selectUser(@PathVariable("id") String id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(id))
                .build();
    }

    @PutMapping("/updateUser/{id}")
    ApiResponse<UserResponse> updateUser(@PathVariable String id, @RequestBody UserUpdateRequest user) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateRequest(id, user))
                .build();
    }

    @DeleteMapping("/deleteUser/{id}")
    ApiResponse<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ApiResponse.<String>builder().result("xoa thanh cong").build();
    }

    @GetMapping("/getMyInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
}
