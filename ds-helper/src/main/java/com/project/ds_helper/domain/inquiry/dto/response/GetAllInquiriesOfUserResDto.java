package com.project.ds_helper.domain.inquiry.dto.response;

import com.project.ds_helper.common.dto.response.PageResDto;
import com.project.ds_helper.domain.inquiry.entity.Inquiry;
import com.project.ds_helper.domain.reply.dto.response.ReplyDto;
import com.project.ds_helper.domain.user.dto.response.UserDto;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public record GetAllInquiriesOfUserResDto(
        List<thisInquiry> inquiries,
        PageResDto page
) {


    @Builder
    static class thisInquiry{
        private String inquiryId;
        private String content;
        private String type;
        private String status;
        private UserDto user;
        private ReplyDto reply;

        public static thisInquiry toThisInquiry(Inquiry inquiry){
            return thisInquiry.builder().inquiryId(inquiry.getId()).content(inquiry.getContent()).type(inquiry.getType().getKorean())
                    .status(inquiry.getStatus().getKorean()).user(UserDto.toDto(inquiry.getUser())).reply(ReplyDto.toDto(inquiry.getReply())).build();
        }
    }

    public static GetAllInquiriesOfUserResDto toDto(Page<Inquiry> inquiries){
        PageResDto page = new PageResDto(inquiries.getNumber(), inquiries.getSize(), inquiries.getTotalElements(), inquiries.getTotalPages(), inquiries.isFirst(), inquiries.isLast(), inquiries.hasNext(), inquiries.hasPrevious(), inquiries.getSort());
        return new GetAllInquiriesOfUserResDto(inquiries.stream().map(thisInquiry::toThisInquiry ).toList(), page);

//        return GetAllInquiriesOfUserResDto.builder()
//                .inquiryId(inquiry.getId())
//                .content(inquiry.getContent())
//                .type(inquiry.getType().getKorean())
//                .status(inquiry.getStatus().getKorean())
//                .user(UserDto.toDto(inquiry.getUser()))
//                .reply(ReplyDto.toDto(inquiry.getReply()))
//                .build();
    }

//    public static Map<String, List<GetAllInquiriesOfUserResDto>> toDtoList(List<Inquiry> inquiries){
//        return inquiries.stream().map(GetAllInquiriesOfUserResDto::toDto).collect(Collectors.groupingBy(GetAllInquiriesOfUserResDto::getType));
//    }


}
