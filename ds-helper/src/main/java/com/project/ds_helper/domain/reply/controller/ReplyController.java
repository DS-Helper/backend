package com.project.ds_helper.domain.reply.controller;

import com.project.ds_helper.domain.reply.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;

    @Tag(name = "문의 답변")
    @Operation(description = "문의 답변 단건 조회")
    @GetMapping()
    public ResponseEntity<?>  getReplyByInquiryId(Authentication authentication,
                                                  @RequestParam("inquiryId") String inquiryId){
        return ResponseEntity.ofNullable(replyService.getReplyByInquiryId(authentication, inquiryId));
    }

}
