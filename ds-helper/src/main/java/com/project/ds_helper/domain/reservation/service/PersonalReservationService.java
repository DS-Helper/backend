package com.project.ds_helper.domain.reservation.service;

import com.project.ds_helper.domain.reservation.dto.request.CreatePersonalReservationReqDto;
import com.project.ds_helper.domain.reservation.dto.request.UpdatePersonalReservationReqDto;
import com.project.ds_helper.domain.reservation.dto.response.CreatePersonalReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.UpdatePersonalReservationResDto;
import com.project.ds_helper.domain.reservation.entity.PersonalReservation;
import com.project.ds_helper.domain.reservation.repository.PersonalReservationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonalReservationService {

    private final PersonalReservationRepository personalReservationRepository;

    /**
     * 신규 개인 예약 생성
     * **/
    public CreatePersonalReservationResDto createPersonalReservation(Authentication authentication, CreatePersonalReservationReqDto dto) {

        // 유저 정보 획득
        String userId = String.valueOf(authentication.getPrincipal());
        log.info("userId : {}", userId);

        // 개인 예약 엔티티 빌드
        PersonalReservation personalReservation = dto.toPersonalReservation(dto);

        // 저장 후 응답 객체 생성 후 반환
        return CreatePersonalReservationResDto.toDto(personalReservationRepository.save(personalReservation));
    }

    public UpdatePersonalReservationResDto updatePersonalReservation(Authentication authentication, @Valid UpdatePersonalReservationReqDto dto) {

        // 유저 정보 획득
        String userId = String.valueOf(authentication.getPrincipal());
        log.info("userId : {}", userId);
        
        // 예약 정보 획득
        String personalReservationId = dto.getPersonalReservationId();
        log.info("personalReservationId : {}", personalReservationId);

        PersonalReservation personalReservation = personalReservationRepository.findById(personalReservationId).orElseThrow(() -> new IllegalArgumentException("없는 예약 ID 입니다. : " + personalReservationId));
        if(!personalReservation.getUser().getId().equals(userId)){
            throw new IllegalArgumentException("예약자 본인만 예약 수정이 가능하니다. 예약자 ID : " + personalReservationId + "요청 유저 Id : " + userId);
        }

        // 수정
        PersonalReservation = dto.toPersonalReservation(dto);
        
        // 저장 후 응답 객체 생성 후 반환
        return CreatePersonalReservationResDto.toDto(personalReservationRepository.save(personalReservation));
    }
}
