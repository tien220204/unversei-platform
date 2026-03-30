package com.tien.identity.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.tien.identity.service.dto.request.ApiResponse;
import com.tien.identity.service.dto.request.PermissionRequest;
import com.tien.identity.service.dto.response.PermissionResponse;
import com.tien.identity.service.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequestMapping("/permissions")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("/create")
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping("findAll")
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("delete/{permissionName}")
    ApiResponse<Void> delete(@PathVariable String permissionName) {
        permissionService.delete(permissionName);
        return ApiResponse.<Void>builder().build();
    }
}
