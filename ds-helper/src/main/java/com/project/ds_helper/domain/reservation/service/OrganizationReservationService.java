package com.project.ds_helper.domain.reservation.service;

import com.project.ds_helper.common.util.UserUtil;
import com.project.ds_helper.domain.reservation.dto.request.CreateOrganizationReservationReqDto;
import com.project.ds_helper.domain.reservation.dto.request.DeleteOrganizationReservationReqDto;
import com.project.ds_helper.domain.reservation.dto.request.UpdateOrganizationReservationReqDto;
import com.project.ds_helper.domain.reservation.dto.response.CreateOrganizationReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.GetOrganizationReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.GetPersonalReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.UpdateOrganizationReservationResDto;
import com.project.ds_helper.domain.reservation.entity.OrganizationReservation;
import com.project.ds_helper.domain.reservation.entity.PersonalReservation;
import com.project.ds_helper.domain.reservation.enums.ReservationStatus;
import com.project.ds_helper.domain.reservation.repository.OrganizationReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizationReservationService {

    private final OrganizationReservationRepository organizationReservationRepository;
    private final UserUtil userUtil;
    
    /**
     * 유저의 전체 기관 예약 조회
     * **/
    /**
     * 유저의 개인 예약 전체 조회
     * **/
    public List<GetOrganizationReservationResDto> getAllOrganizationReservation(Authentication authentication) {
        List<OrganizationReservation> organizationReservations = organizationReservationRepository.findAllByUser_Id(userUtil.extractUserId(authentication));
        log.info("organizationReservations selected successfully");

        return GetOrganizationReservationResDto.toDtoList(organizationReservations);
    }

    /**
     * 단건 개인 예약 조회
     * **/
    public GetOrganizationReservationResDto getOneOrganizationReservation(Authentication authentication, String organizationReservationId) {

        String userId = String.valueOf(authentication.getPrincipal());
        log.info("userId : {}", userId);

        log.info("organizationReservationId : {}", organizationReservationId);

        // 개인 예약 조회 및 응답
        return GetOrganizationReservationResDto.toDto(organizationReservationRepository.findById(organizationReservationId).
                orElseThrow(() -> new IllegalArgumentException("없는 기관 예약 입니다. 예약 ID : " + organizationReservationId)));
    }

    /**
     * 신규 개인 예약 생성
     * **/
    public CreateOrganizationReservationResDto createOrganizationReservation(Authentication authentication, CreateOrganizationReservationReqDto dto) {

        // 유저 정보 획득
        String userId = String.valueOf(authentication.getPrincipal());
        log.info("userId : {}", userId);

        // 개인 예약 엔티티 빌드
        OrganizationReservation organizationReservation = dto.toOrganizationReservation(dto);
        log.info("신규 기관 예약 엔티티 빌드 완료");

        // 저장 후 응답 객체 생성 후 반환
        return CreateOrganizationReservationResDto.toDto(organizationReservationRepository.save(organizationReservation));
    }

    public UpdateOrganizationReservationResDto updateOrganizationReservation(Authentication authentication, UpdateOrganizationReservationReqDto dto) {

        // 유저 정보 획득
        String userId = String.valueOf(authentication.getPrincipal());
        log.info("userId : {}", userId);

        // 예약 정보 획득
        String organizationReservationId = dto.getOrganizationReservationId();
        log.info("organizationReservationId : {}", organizationReservationId);

        // 기존 개인 예약 조회
        OrganizationReservation organizationReservation = organizationReservationRepository.findById(organizationReservationId).orElseThrow(() -> new IllegalArgumentException("없는 예약 ID 입니다. : " + organizationReservationId));
        if(!organizationReservation.getUser().getId().equals(userId)){
            throw new IllegalArgumentException("예약자 본인만 예약 수정이 가능하니다. 예약 ID : " + organizationReservationId + "요청 유저 Id : " + userId);
        }

        // 수정
        OrganizationReservation updatedOrganizationReservation = dto.toOrganizationReservation(organizationReservation, dto);
        log.info("수정된 개인 예약 엔티티 빌드");

        // JPA 에서 관리 해주지만 명시적인 저장 메소드 호출
        OrganizationReservation savedOrganizationReservation = organizationReservationRepository.save(updatedOrganizationReservation);
        log.info("수정된 엔티티 저장 완료");

        // 저장 후 응답 객체 생성 후 반환
        return UpdateOrganizationReservationResDto.toDto(savedOrganizationReservation);
    }

    /**
     * 개인 예약 취소
     * **/
    public void cancelOrganizationReservation(Authentication authentication, DeleteOrganizationReservationReqDto dto) {

        String userId = String.valueOf(authentication.getPrincipal());
        log.info("userId : {}", userId);

        String organizationReservationId = dto.getOrganizationReservationId();
        log.info("organizationReservationId : {}", organizationReservationId);

        OrganizationReservation organizationReservation = organizationReservationRepository.findById(organizationReservationId).orElseThrow(() -> new IllegalArgumentException("없는 기관 예약 입니다. 예약 ID : " + organizationReservationId));
        log.info("기관 예약 조회 완료");

        organizationReservation.setReservationStatus(ReservationStatus.CANCELED);

        organizationReservationRepository.save(organizationReservation);
        log.info("기관 예약 삭제 완료");
    }

    /**
     * 요청 상태별 개인 예약 조회
     * userId와 reservationStatus 기반 조회
     * **/
    public List<GetOrganizationReservationResDto> getAllOrganizationReservationByReservationStatus(Authentication authentication, String reservationStatus) {
            String userId = userUtil.extractUserId(authentication);
            ReservationStatus _reservationStatus = ReservationStatus.findStatusByString(reservationStatus);
            log.info("reservationStatus : {}", reservationStatus);

            return GetOrganizationReservationResDto.toDtoList(organizationReservationRepository.findAllByUser_IdAndReservationStatus(userId, _reservationStatus));
    }
}
