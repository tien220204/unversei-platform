package com.tien.identity.service.dto.response;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {

    String name;
    String description;
    Set<PermissionResponse> permissions;
}
