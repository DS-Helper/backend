package com.project.ds_helper.domain.reservation.controller;

import com.project.ds_helper.domain.reservation.dto.response.GetPersonalReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.GetReservationsByReservationStatusResDto;
import com.project.ds_helper.domain.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    
    /**
     * 
     * 요청 상태별 전체 개인 & 기관 예약 조회
     * */
    @Tag(name = "예약")
    @Operation(summary = "호출 상태별 전체 개인 & 기관 예약 조회")
    @GetMapping("/reservation-status")
    public ResponseEntity<GetReservationsByReservationStatusResDto> getAllReservationByReservationStatus(Authentication authentication,
                                                                                                         @RequestParam("reservationStatus") String reservationStatus ){
        return new ResponseEntity<GetReservationsByReservationStatusResDto>(reservationService.getAllReservationByReservationStatus(authentication, reservationStatus), HttpStatus.OK);
    }
}
