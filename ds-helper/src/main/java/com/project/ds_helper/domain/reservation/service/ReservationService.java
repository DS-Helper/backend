package com.project.ds_helper.domain.reservation.service;

import com.project.ds_helper.common.util.UserUtil;
import com.project.ds_helper.domain.reservation.dto.response.GetOrganizationReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.GetPersonalReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.GetReservationsByReservationStatusResDto;
import com.project.ds_helper.domain.reservation.enums.ReservationStatus;
import com.project.ds_helper.domain.reservation.repository.OrganizationReservationRepository;
import com.project.ds_helper.domain.reservation.repository.PersonalReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {

    private final PersonalReservationRepository personalReservationRepository;
    private final OrganizationReservationRepository organizationReservationRepository;
    private final UserUtil userUtil;

    /***
     * 요청 상태별 전체 예약 조회 (개인 / 기관)
     * */
    public GetReservationsByReservationStatusResDto getAllReservationByReservationStatus(Authentication authentication, String reservationStatus) {

        String userId = userUtil.extractUserId(authentication);
        ReservationStatus _reservationStatus = ReservationStatus.findStatusByString(reservationStatus);

        List<GetPersonalReservationResDto> personalReservations = GetPersonalReservationResDto.toDtoList(personalReservationRepository.findAllByUser_IdAndReservationStatus(userId, _reservationStatus));
        List<GetOrganizationReservationResDto> organizationReservations  = GetOrganizationReservationResDto.toDtoList(organizationReservationRepository.findAllByUser_IdAndReservationStatus(userId, _reservationStatus));
        log.info("personalReservations size : {}, organizationReservations size : {} selected successfully", personalReservations.size(), organizationReservations.size());

        return new GetReservationsByReservationStatusResDto(personalReservations, organizationReservations);
    }
}
