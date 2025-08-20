package com.project.ds_helper.domain.inquiry.dto.request;

import com.project.ds_helper.domain.inquiry.entity.Inquiry;
import com.project.ds_helper.domain.inquiry.enums.InquiryType;
import com.project.ds_helper.domain.user.entity.User;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateInquiryReqDto {


    private String type;

    private String content;

    @Size(max = 2, message = "File may not be more than 2 ")
    private List<MultipartFile> iamges;

    public Inquiry toInquiry(CreateInquiryReqDto dto, User user, List<String> imagesUrl){
        return Inquiry.builder()
                .content(dto.getContent())
                .imagesUrl(imagesUrl)
                .type(InquiryType.findByKorean(dto.getType()))
                .user(user)
                .build();
    }

}
