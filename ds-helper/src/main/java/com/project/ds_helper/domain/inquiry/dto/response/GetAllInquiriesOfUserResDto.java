package com.project.ds_helper.domain.inquiry.dto.response;

import com.project.ds_helper.domain.inquiry.entity.Inquiry;
import com.project.ds_helper.domain.reply.dto.response.ReplyDto;
import com.project.ds_helper.domain.user.dto.response.UserDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllInquiriesOfUserResDto {

    private String inquiryId;

    private String content;

    private String type;

    private String status;

    private UserDto user;

    private ReplyDto reply;

    public static GetAllInquiriesOfUserResDto toDto(Inquiry inquiry){
        return GetAllInquiriesOfUserResDto.builder()
                .inquiryId(inquiry.getId())
                .content(inquiry.getContent())
                .type(inquiry.getType().getKorean())
                .status(inquiry.getStatus().getKorean())
                .user(UserDto.toDto(inquiry.getUser()))
                .reply(ReplyDto.toDto(inquiry.getReply()))
                .build();
    }

    public static List<GetAllInquiriesOfUserResDto> toDtoList(List<Inquiry> inquiries){
        if(inquiries.isEmpty()){return new ArrayList<>();}
        return inquiries.stream().map(GetAllInquiriesOfUserResDto::toDto).toList();
    }


}
