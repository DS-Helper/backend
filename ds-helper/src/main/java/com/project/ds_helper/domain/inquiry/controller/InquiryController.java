package com.project.ds_helper.domain.inquiry.controller;

import com.project.ds_helper.domain.inquiry.dto.request.CreateInquiryReqDto;
import com.project.ds_helper.domain.inquiry.service.InquiryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/inquires")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;
    
    @Tag( name = "문의")
    @Operation(summary = "유저의 전체 문의 조회")
    @GetMapping("/all")
    public ResponseEntity<?> getAllInquiriesOfUser(Authentication authentication){
        return ResponseEntity.ok(inquiryService.getAllInquiriesOfUser(authentication));
    }
    
    @Tag( name = "문의")
    @Operation(summary = "문의 생성")
    @PostMapping("")
    public ResponseEntity<?> createInquiry(Authentication authentication,
                                           @RequestBody @Valid CreateInquiryReqDto dto) throws IOException {
        inquiryService.createInquiry(authentication, dto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }



}
