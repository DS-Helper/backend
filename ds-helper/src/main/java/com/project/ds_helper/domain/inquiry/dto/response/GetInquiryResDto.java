package com.project.ds_helper.domain.inquiry.dto.response;

import com.project.ds_helper.domain.inquiry.entity.Inquiry;
import com.project.ds_helper.domain.reply.dto.response.ReplyDto;
import com.project.ds_helper.domain.user.dto.response.UserDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GetInquiryResDto {

    private String inquiryId;

    private String content;

    private String type;

    private String status;

    private UserDto user;

    private ReplyDto reply;

    public static GetInquiryResDto toDto(Inquiry inquiry){
        return GetInquiryResDto.builder()
                .inquiryId(inquiry.getId())
                .content(inquiry.getContent())
                .type(inquiry.getType().getKorean())
                .status(inquiry.getStatus().getKorean())
                .user(UserDto.toDto(inquiry.getUser()))
                .reply(ReplyDto.toDto(inquiry.getReply()))
                .build();
    }
}
