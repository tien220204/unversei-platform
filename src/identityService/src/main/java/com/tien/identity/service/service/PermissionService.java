package com.tien.identity.service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tien.identity.service.dto.request.PermissionRequest;
import com.tien.identity.service.dto.response.PermissionResponse;
import com.tien.identity.service.mapper.PermissionMapper;
import com.tien.identity.service.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper mapper;

    public PermissionResponse create(PermissionRequest request) {
        var permission = mapper.toPermission(request);
        return mapper.toPermissionReponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(mapper::toPermissionReponse).toList();
    }

    public void delete(String permissionName) {
        permissionRepository.deleteById(permissionName);
    }
}
