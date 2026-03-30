package com.tien.identity.service.dto.request;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {

    String name;
    String description;
    Set<String> permissions;
}
