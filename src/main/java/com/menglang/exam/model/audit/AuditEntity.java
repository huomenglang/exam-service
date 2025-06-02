package com.menglang.exam.model.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class AuditEntity<T extends Serializable> extends BaseEntity<T> {

    @Column(name = "created_at",nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "created_by",nullable = false, updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_at",insertable = false)
    @LastModifiedDate
    protected LocalDateTime updated_at;

    @Column(name = "updated_by",insertable = false)
    @LastModifiedBy
    private String updatedBy;

}