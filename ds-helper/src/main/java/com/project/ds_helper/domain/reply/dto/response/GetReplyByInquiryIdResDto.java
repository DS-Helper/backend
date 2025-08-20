package com.project.ds_helper.domain.reply.dto.response;

import com.project.ds_helper.domain.reply.entity.Reply;
import lombok.Builder;

@Builder
public record GetReplyByInquiryIdResDto(
        String replyId,
        String content,
        String inquiryId
) {
    public static Object toEmptyDto(){
        return GetReplyByInquiryIdResDto.builder()
                .replyId("")
                .content("")
                .inquiryId("")
                .build();
    }

    public static GetReplyByInquiryIdResDto toDto(Reply reply){
        return GetReplyByInquiryIdResDto.builder()
                .replyId(reply.getId())
                .content(reply.getContent())
                .inquiryId(reply.getInquiry().getId())
                .build();
    }
}
