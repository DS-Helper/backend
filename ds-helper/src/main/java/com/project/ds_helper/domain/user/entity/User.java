package com.project.ds_helper.domain.user.entity;

import com.project.ds_helper.common.base.entity.BaseTime;
import com.project.ds_helper.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends BaseTime {

    @PrePersist
    private void GenerateUuid(){
        this.id = String.valueOf(UUID.randomUUID()); // 저장 시 uuid 자동 생성
    }

    @Id @Column(name = "user_id")
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;
}
