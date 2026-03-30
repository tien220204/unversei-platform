package com.tien.identity.service.mapper;

import org.mapstruct.Mapper;

import com.tien.identity.service.dto.request.PermissionRequest;
import com.tien.identity.service.dto.response.PermissionResponse;
import com.tien.identity.service.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    //    @Mapping(target = "firstname", expression = "java(UserReponse.getFirstName()+\"
    // \"+UserReponse.getLastName())")
    PermissionResponse toPermissionReponse(Permission permission);
}
