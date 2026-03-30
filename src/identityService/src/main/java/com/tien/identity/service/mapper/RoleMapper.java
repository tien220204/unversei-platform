package com.tien.identity.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tien.identity.service.dto.request.RoleRequest;
import com.tien.identity.service.dto.response.RoleResponse;
import com.tien.identity.service.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    //    @Mapping(target = "firstname", expression = "java(UserReponse.getFirstName()+\"
    // \"+UserReponse.getLastName())")
    RoleResponse toRoleReponse(Role role);
}
