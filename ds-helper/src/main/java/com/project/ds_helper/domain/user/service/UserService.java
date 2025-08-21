package com.project.ds_helper.domain.user.service;

import com.project.ds_helper.common.dto.request.S3ImageUploadReqDto;
import com.project.ds_helper.common.util.PasswordUtil;
import com.project.ds_helper.domain.post.util.S3Util;
import com.project.ds_helper.domain.user.dto.request.OrganizationJoinReqDto;
import com.project.ds_helper.domain.user.entity.Organization;
import com.project.ds_helper.domain.user.entity.User;
import com.project.ds_helper.domain.user.enums.UserType;
import com.project.ds_helper.domain.user.repository.OrganizationRepository;
import com.project.ds_helper.domain.user.repository.UserRepository;
import io.jsonwebtoken.security.Password;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordUtil passwordUtil;
    private final S3Util s3Util;

    /**
     * 기관 회원가입
     * **/
    public void organizationJoin(@Valid OrganizationJoinReqDto dto) throws IOException {

        String email = dto.getEmail();
        String password = dto.getPassword();
        String passwordCheck = dto.getPasswordCheck();
        log.info("");

        if(passwordUtil.isPasswordMatch(dto.getPassword(), dto.getPasswordCheck())){throw new IllegalArgumentException("Password Not Match");}

        // 로컬 회원가입 미가입 신규 회원가입 처리
        User newUser = User.builder()
                .email(email)
                .password(password)
                .type(UserType.ORGANIZATION)
                .build();
        log.info("newUser is built");

        // 신규 유저 저장
        User user = userRepository.save(newUser);
        log.info("user saved");

        // 이미지 업로드 후 url 반환
        List<String> imageUrls = s3Util.uploadImages(dto.getCertifications().stream().map(certification -> {
            try {
                return new S3ImageUploadReqDto(certification.getOriginalFilename(), certification.getContentType(), certification.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList());
        
        // 기관 엔티티 빌드
        Organization organization = Organization.builder()
                .organizationName(dto.getOrganizationName())
                .organizationPhoneNumber(dto.getOrganizationPhoneNumber())
                .certificationUrl(imageUrls)
                .userId(user.getId())
                .build();

        organizationRepository.save(organization);
        log.info("Organization Saved Successfully");
    }
}
