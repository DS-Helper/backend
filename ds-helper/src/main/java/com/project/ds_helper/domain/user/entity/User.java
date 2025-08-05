package com.project.ds_helper.domain.user.entity;

import com.project.ds_helper.domain.base.entity.BaseTime;
import com.project.ds_helper.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tb_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User extends BaseTime {

    @PrePersist
    private void prePersistGenerateId(){
        this.id = String.valueOf(UUID.randomUUID());
    }

    @Id
    @Column(name = "user_id")
    private String id; // 식별자

    @Column(name = "email", nullable = false)
    private String email; // 이메일

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role = UserRole.USER; // 권한

    @Column(name = "name")
    private String name; // 이름

    @Builder.Default
    @Column(name = "local_auth_connected")
    private boolean localAuthConnected = false; // 자체 회원가입 여부

    @Builder.Default
    @Column(name = "kakao_oauth_connected")
    private boolean kakaoOauthConnected = false; // 카카오 소셜 회원가입 여부

    @Builder.Default
    @Column(name = "google_oauth_connected")
    private boolean googleOauthConnected = false; // 구글 소셜 회원가입 여부

    @Builder.Default
    @Column(name = "naver_oauth_connected")
    private boolean naverOauthConnected = false; // 네이버 소셜 회원가입 여부

}
