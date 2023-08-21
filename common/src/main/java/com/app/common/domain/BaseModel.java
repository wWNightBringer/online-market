package com.app.common.domain;


import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseModel {
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isDeleted;
}
