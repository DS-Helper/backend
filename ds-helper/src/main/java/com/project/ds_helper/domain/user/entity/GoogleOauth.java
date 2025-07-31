package com.project.ds_helper.domain.user.entity;

import com.project.ds_helper.common.base.entity.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_user_google_oauth")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoogleOauth extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "google_oauth_id")
    private UUID id;

    @OneToOne(optional = false, cascade = {})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "social_oauth_id")
    private String socialOauthId;

    @Column(name = "kakao_oauth_email")
    private String kakaoOauthEmail;

    @Column(name = "name")
    private String name;
}
