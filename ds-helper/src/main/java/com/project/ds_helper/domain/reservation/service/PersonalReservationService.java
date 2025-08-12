package com.project.ds_helper.domain.reservation.service;

import com.project.ds_helper.domain.reservation.dto.request.CreatePersonalReservationReqDto;
import com.project.ds_helper.domain.reservation.dto.request.DeletePersonalReservationReqDto;
import com.project.ds_helper.domain.reservation.dto.request.UpdatePersonalReservationReqDto;
import com.project.ds_helper.domain.reservation.dto.response.CreatePersonalReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.GetPersonalReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.UpdatePersonalReservationResDto;
import com.project.ds_helper.domain.reservation.entity.PersonalReservation;
import com.project.ds_helper.domain.reservation.enums.ReservationStatus;
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
     * 단건 개인 예약 조회
     * **/
    public GetPersonalReservationResDto getOnePersonalReservation(Authentication authentication, String personalReservationId) {
        
        String userId = String.valueOf(authentication.getPrincipal());
        log.info("userId : {}", userId);

        log.info("personalReservationId : {}", personalReservationId);
    
        // 개인 예약 조회 및 응답
        return GetPersonalReservationResDto.toDto(personalReservationRepository.findById(personalReservationId).
                orElseThrow(() -> new IllegalArgumentException("없는 개인 예약 입니다. 예약 ID : " + personalReservationId)));
    }

    /**
     * 신규 개인 예약 생성
     * **/
    public CreatePersonalReservationResDto createPersonalReservation(Authentication authentication, CreatePersonalReservationReqDto dto) {

        // 유저 정보 획득
        String userId = String.valueOf(authentication.getPrincipal());
        log.info("userId : {}", userId);

        // 개인 예약 엔티티 빌드
        PersonalReservation personalReservation = dto.toPersonalReservation(dto);
        log.info("신규 개인 예약 엔티티 빌드 완료");
        
        // 저장 후 응답 객체 생성 후 반환
        return CreatePersonalReservationResDto.toDto(personalReservationRepository.save(personalReservation));
    }

    public UpdatePersonalReservationResDto updatePersonalReservation(Authentication authentication, UpdatePersonalReservationReqDto dto) {

        // 유저 정보 획득
        String userId = String.valueOf(authentication.getPrincipal());
        log.info("userId : {}", userId);
        
        // 예약 정보 획득
        String personalReservationId = dto.getPersonalReservationId();
        log.info("personalReservationId : {}", personalReservationId);
    
        // 기존 개인 예약 조회
        PersonalReservation personalReservation = personalReservationRepository.findById(personalReservationId).orElseThrow(() -> new IllegalArgumentException("없는 예약 ID 입니다. : " + personalReservationId));
        if(!personalReservation.getUser().getId().equals(userId)){
            throw new IllegalArgumentException("예약자 본인만 예약 수정이 가능하니다. 예약자 ID : " + personalReservationId + "요청 유저 Id : " + userId);
        }

        // 수정
        PersonalReservation updatedPersonalReservation = dto.toPersonalReservation(personalReservation, dto);
        log.info("수정된 개인 예약 엔티티 빌드");
        
        // JPA 에서 관리 해주지만 명시적인 저장 메소드 호출
        personalReservationRepository.save(updatedPersonalReservation);
        log.info("수정된 엔티티 저장 완료");
        
        // 저장 후 응답 객체 생성 후 반환
        return UpdatePersonalReservationResDto.toDto(personalReservationRepository.save(personalReservation));
    }
    
    /**
     * 개인 예약 취소
     * **/
    public void cancelPersonalReservation(Authentication authentication, DeletePersonalReservationReqDto dto) {

        String userId = String.valueOf(authentication.getPrincipal());
        log.info("userId : {}", userId);

        String personalReservationId = dto.getPersonalReservationId();
        log.info("personalReservationId : {}", personalReservationId);

        PersonalReservation personalReservation = personalReservationRepository.findById(personalReservationId).orElseThrow(() -> new IllegalArgumentException("없는 개인 예약 입니다. 예약 ID : " + personalReservationId));
        log.info("개인 예약 조회 완료");

        personalReservation.setReservationStatus(ReservationStatus.CANCELED);
        
        personalReservationRepository.save(personalReservation);
        log.info("개인 예약 삭제 완료");
    }

}
