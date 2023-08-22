package com.app.common.domain;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseModel {
    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();
    @Column(name = "update_date")
    private LocalDateTime updateDate = LocalDateTime.now();
    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
