package com.tien.identity.service.domain;

import java.time.LocalDate;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class Auditable<T> {
    @CreatedBy
    T createdBy;

    @CreatedDate
    LocalDate createdDate;

    @LastModifiedBy
    T lastModifiedBy;

    @LastModifiedDate
    LocalDate lastModifiedDate;
}
