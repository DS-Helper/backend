package com.project.ds_helper.domain.user.dto.request;

import jakarta.mail.Message;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class OrganizationJoinReqDto {

    @NotEmpty
    @Email(message = "이메일 형식에 맞지 않습니다.", regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordCheck;

    @NotEmpty
    private String organizationName;

    @NotEmpty
    @Pattern(
            regexp = "^01[0-9]-\\d{3,4}-\\d{4}$",
            message = "올바른 휴대폰 번호 형식이 아닙니다."
    )
    private String organizationPhoneNumber;

    @Size(max = 2, message = "Certifications Cannot Be More Than 2")
    private List<MultipartFile> certifications;
}
