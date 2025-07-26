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

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id; // 식별자 / UUID

    @Column(name = "email", nullable = false)
    private String email; // 이메일

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role; // 권한

    @Column(name = "name")
    private String name; // 이름
}
