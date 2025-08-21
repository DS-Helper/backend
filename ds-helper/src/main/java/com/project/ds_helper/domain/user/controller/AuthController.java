package com.project.ds_helper.domain.user.controller;

import com.project.ds_helper.domain.user.dto.request.OrganizationJoinReqDto;
import com.project.ds_helper.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping("/join/organization")
    public ResponseEntity<?> organizationJoin(@RequestBody @Valid OrganizationJoinReqDto dto) throws IOException {
        userService.organizationJoin(dto);
        return ResponseEntity.accepted().build();
    }
}
