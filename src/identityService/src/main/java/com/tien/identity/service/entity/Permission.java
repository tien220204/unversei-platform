package com.tien.identity.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Table
@Entity()
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {
    @Id
    String name;

    String description;
}
