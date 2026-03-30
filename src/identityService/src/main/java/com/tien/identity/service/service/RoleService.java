package com.tien.identity.service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tien.identity.service.dto.request.RoleRequest;
import com.tien.identity.service.dto.response.RoleResponse;
import com.tien.identity.service.mapper.RoleMapper;
import com.tien.identity.service.repository.PermissionRepository;
import com.tien.identity.service.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper mapper;

    public RoleResponse create(RoleRequest request) {
        var role = mapper.toRole(request);
        System.out.println(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        System.out.println("role:" + role.getPermissions());
        return mapper.toRoleReponse(roleRepository.save(role));
    }

    public List<RoleResponse> getAll() {
        var permissions = roleRepository.findAll();
        return permissions.stream().map(mapper::toRoleReponse).toList();
    }

    public void delete(String permissionName) {
        roleRepository.deleteById(permissionName);
    }
}
