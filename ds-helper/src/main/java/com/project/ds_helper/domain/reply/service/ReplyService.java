package com.project.ds_helper.domain.reply.service;

import com.project.ds_helper.common.util.UserUtil;
import com.project.ds_helper.domain.reply.dto.response.GetReplyByInquiryIdResDto;
import com.project.ds_helper.domain.reply.entity.Reply;
import com.project.ds_helper.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final UserUtil userUtil;
    
    /**
     * 문의 답변글 조회
     * **/
    public Object getReplyByInquiryId(Authentication authentication, String inquiryId) {
    
        // 답변 조회
        Reply reply = replyRepository.findByInquiry_Id(inquiryId).orElse(null);
        // 답변 글 부재 시 빈 문자열을 채운 dto 응답
        if(reply == null){ return GetReplyByInquiryIdResDto.toEmptyDto();}
        return GetReplyByInquiryIdResDto.toDto(reply);
    }
}
