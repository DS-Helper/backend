package com.project.ds_helper.domain.reservation.controller;

import com.project.ds_helper.domain.reservation.dto.request.*;
import com.project.ds_helper.domain.reservation.dto.response.*;
import com.project.ds_helper.domain.reservation.service.OrganizationReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/organization-reservations")
public class OrganizationReservationController {

    private final OrganizationReservationService organizationReservationService;

    /**
     * 유저의 기관 예약 전체 조회
     * **/
    @GetMapping("/all")
    public ResponseEntity<List<GetOrganizationReservationResDto>> getAllOrganizationReservation(Authentication authentication){
        return new ResponseEntity<List<GetOrganizationReservationResDto>>(organizationReservationService.getAllOrganizationReservation(authentication), HttpStatus.OK);
    }

    /**
     * 스웨거 등록 필요
     * 단건 개인 예약 조회
     * **/
    @GetMapping("/{organizationReservationId}")
    public ResponseEntity<GetOrganizationReservationResDto> getOneOrganizationReservation(Authentication authentication,
                                                                                          @PathVariable("organizationReservationId") String organizationReservationId ){
        return new ResponseEntity<GetOrganizationReservationResDto>(organizationReservationService.getOneOrganizationReservation(authentication, organizationReservationId), HttpStatus.OK);
    }

    /**
     * 스웨거 등록 필요
     * 요청 상태별 조회
     * **/
    @GetMapping("/reservation-status")
    public ResponseEntity<List<GetOrganizationReservationResDto>> getAllOrganizationReservationByReservationStatus(Authentication authentication,
                                                                                                           @RequestParam("reservationStatus") String reservationStatus ){
        return new ResponseEntity<List<GetOrganizationReservationResDto>>(organizationReservationService.getAllOrganizationReservationByReservationStatus(authentication, reservationStatus), HttpStatus.OK);
    }

    /**
     * 스웨거 등록 필요
     * 신규 개인 예약 생성
     * **/
    @PostMapping("")
    public ResponseEntity<CreateOrganizationReservationResDto> createOrganizationReservation(Authentication authentication,
                                                                                     @RequestBody @Valid CreateOrganizationReservationReqDto dto){
        return new ResponseEntity<CreateOrganizationReservationResDto>(organizationReservationService.createOrganizationReservation(authentication, dto), HttpStatus.CREATED);
    }

    /**
     * 스웨거 등록 필요
     * 기존 개인 예약 수정
     * **/
    @PutMapping("")
    public ResponseEntity<UpdateOrganizationReservationResDto> updateOrganizationReservation(Authentication authentication,
                                                                                     @RequestBody @Valid UpdateOrganizationReservationReqDto dto){
        return new ResponseEntity<UpdateOrganizationReservationResDto>(organizationReservationService.updateOrganizationReservation(authentication, dto), HttpStatus.CREATED);
    }

    /**
     * 스웨거 등록 필요
     * 기존 개인 예약 취소
     * **/
    @DeleteMapping("")
    public ResponseEntity<Void> cancelOrganizationReservation(Authentication authentication,
                                                          @RequestBody @Valid DeleteOrganizationReservationReqDto dto){
        organizationReservationService.cancelOrganizationReservation(authentication, dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
