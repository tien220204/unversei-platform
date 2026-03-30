package com.tien.identity.service.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.tien.identity.service.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String username;
    String password;
    String firstname;
    String lastname;

    @DobConstraint(min = 18, message = "DOB_INVALID")
    LocalDate dob;

    List<String> roles;
}
