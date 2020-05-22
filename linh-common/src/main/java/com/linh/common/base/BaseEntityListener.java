package com.linh.common.base;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class BaseEntityListener {
    @PrePersist
    public void setCreated(BaseEntity entity) {
    	entity.setCreatedDate(LocalDateTime.now());
    	entity.setIsDeleted(false);
    }
    
    @PreUpdate
    public void setUpdated(BaseEntity entity) {
    	entity.setUpdatedDate(LocalDateTime.now());
    }
}
