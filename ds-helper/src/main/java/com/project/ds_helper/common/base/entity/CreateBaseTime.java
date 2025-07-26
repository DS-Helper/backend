package com.project.ds_helper.common.base.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;

import java.time.LocalDateTime;


@MappedSuperclass
@Getter
public class CreateBaseTime {

    private LocalDateTime createdAt;


    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

}
