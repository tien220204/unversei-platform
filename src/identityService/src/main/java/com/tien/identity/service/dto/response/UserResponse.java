package com.tien.identity.service.dto.response;

import java.time.LocalDate;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String firstname;
    String lastname;
    LocalDate dob;
    Set<RoleResponse> roles;
}
