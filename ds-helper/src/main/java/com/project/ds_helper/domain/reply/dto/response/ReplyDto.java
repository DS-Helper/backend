package com.project.ds_helper.domain.reply.dto.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.project.ds_helper.domain.inquiry.dto.response.InquiryDto;
import com.project.ds_helper.domain.reply.entity.Reply;
import com.project.ds_helper.domain.user.dto.response.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReplyDto {

    private String replyId;

    private String content;

    private UserDto user;

    private InquiryDto inquiry;

    public static ReplyDto toDto(Reply reply){
        return ReplyDto.builder()
                .replyId(reply.getId())
                .content(reply.getContent())
                .user(UserDto.toDto(reply.getUser()))
                .inquiry(InquiryDto.toDto(reply.getInquiry()))
                .build();
    }
}
