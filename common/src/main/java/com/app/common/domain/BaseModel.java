package com.app.common.domain;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseModel {

    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate = LocalDateTime.now();

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
