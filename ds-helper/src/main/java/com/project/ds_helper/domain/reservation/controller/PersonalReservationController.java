package com.project.ds_helper.domain.reservation.controller;

import com.project.ds_helper.domain.reservation.dto.request.CreatePersonalReservationReqDto;
import com.project.ds_helper.domain.reservation.dto.request.UpdatePersonalReservationReqDto;
import com.project.ds_helper.domain.reservation.dto.response.CreatePersonalReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.UpdatePersonalReservationResDto;
import com.project.ds_helper.domain.reservation.service.PersonalReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation/personal")
public class PersonalReservationController {

    private final PersonalReservationService personalReservationService;
    
    /**
     * 스웨거 등록 필요
     * **/
    @PostMapping("")
    public ResponseEntity<CreatePersonalReservationResDto> createPersonalReservation(Authentication authentication,
                                                       @RequestBody @Valid CreatePersonalReservationReqDto dto){
        return new ResponseEntity<CreatePersonalReservationResDto>(personalReservationService.createPersonalReservation(authentication, dto), HttpStatus.CREATED);
    }

    /**
     * 스웨거 등록 필요
     * **/
    @PatchMapping("")
    public ResponseEntity<UpdatePersonalReservationResDto> updatePersonalReservation(Authentication authentication,
                                                                                    @RequestBody @Valid UpdatePersonalReservationReqDto dto){
        return new ResponseEntity<UpdatePersonalReservationResDto>(personalReservationService.updatePersonalReservation(authentication, dto), HttpStatus.CREATED);
    }
}
