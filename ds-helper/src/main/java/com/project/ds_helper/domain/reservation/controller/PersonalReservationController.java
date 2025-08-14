package com.project.ds_helper.domain.reservation.controller;

import com.project.ds_helper.domain.reservation.dto.request.CreatePersonalReservationReqDto;
import com.project.ds_helper.domain.reservation.dto.request.DeletePersonalReservationReqDto;
import com.project.ds_helper.domain.reservation.dto.request.UpdatePersonalReservationReqDto;
import com.project.ds_helper.domain.reservation.dto.response.CreatePersonalReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.GetPersonalReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.UpdatePersonalReservationResDto;
import com.project.ds_helper.domain.reservation.service.PersonalReservationService;
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
@RequestMapping("/api/v1/personal-reservations")
public class PersonalReservationController {

    private final PersonalReservationService personalReservationService;
    
    /**
     * 유저의 개인 예약 전체 조회
     * **/
    @GetMapping("/all")
    public ResponseEntity<List<GetPersonalReservationResDto>> getAllPersonalReservation(Authentication authentication){
        return new ResponseEntity<List<GetPersonalReservationResDto>>(personalReservationService.getAllPersonalReservation(authentication), HttpStatus.OK);
    }

    /**
     * 스웨거 등록 필요
     * 단건 개인 예약 조회
     * **/
    @GetMapping("/{personalReservationId}")
    public ResponseEntity<GetPersonalReservationResDto> getOnePersonalReservation(Authentication authentication,
                                                                                  @PathVariable("personalReservationId") String personalReservationId ){
        return new ResponseEntity<GetPersonalReservationResDto>(personalReservationService.getOnePersonalReservation(authentication, personalReservationId), HttpStatus.OK);
    }

    /**
     * 스웨거 등록 필요
     * 요청 상태별 조회
     * **/
    @GetMapping("/reservation-status")
    public ResponseEntity<List<GetPersonalReservationResDto>> getAllPersonalReservationByReservationStatus(Authentication authentication,
                                                                                  @RequestParam("reservationStatus") String reservationStatus ){
        return new ResponseEntity<List<GetPersonalReservationResDto>>(personalReservationService.getAllPersonalReservationByReservationStatus(authentication, reservationStatus), HttpStatus.OK);
    }

    /**
     * 스웨거 등록 필요
     * 신규 개인 예약 생성
     * **/
    @PostMapping("")
    public ResponseEntity<CreatePersonalReservationResDto> createPersonalReservation(Authentication authentication,
                                                       @RequestBody @Valid CreatePersonalReservationReqDto dto){
        return new ResponseEntity<CreatePersonalReservationResDto>(personalReservationService.createPersonalReservation(authentication, dto), HttpStatus.CREATED);
    }

    /**
     * 스웨거 등록 필요
     * 기존 개인 예약 수정
     * **/
    @PutMapping("")
    public ResponseEntity<UpdatePersonalReservationResDto> updatePersonalReservation(Authentication authentication,
                                                                                    @RequestBody @Valid UpdatePersonalReservationReqDto dto){
        return new ResponseEntity<UpdatePersonalReservationResDto>(personalReservationService.updatePersonalReservation(authentication, dto), HttpStatus.CREATED);
    }

    /**
     * 스웨거 등록 필요
     * 기존 개인 예약 취소
     * **/
    @DeleteMapping("")
    public ResponseEntity<Void> cancelPersonalReservation(Authentication authentication,
                                                          @RequestBody @Valid DeletePersonalReservationReqDto dto){
        personalReservationService.cancelPersonalReservation(authentication, dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
