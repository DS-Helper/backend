package com.project.ds_helper.domain.reservation.service;

import com.amazonaws.services.ec2.model.Reservation;
import com.project.ds_helper.common.util.UserUtil;
import com.project.ds_helper.domain.reservation.dto.response.GetOrganizationReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.GetPersonalReservationResDto;
import com.project.ds_helper.domain.reservation.dto.response.GetReservationsByReservationStatusResDto;
import com.project.ds_helper.domain.reservation.entity.OrganizationReservation;
import com.project.ds_helper.domain.reservation.entity.PersonalReservation;
import com.project.ds_helper.domain.reservation.enums.ReservationStatus;
import com.project.ds_helper.domain.reservation.repository.OrganizationReservationRepository;
import com.project.ds_helper.domain.reservation.repository.PersonalReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public GetReservationsByReservationStatusResDto getAllReservationByReservationStatus(Authentication authentication, String reservationStatus, int page, int size, String sort, String sortBy) {

        String userId = userUtil.extractUserId(authentication);
        ReservationStatus _reservationStatus = ReservationStatus.findStatusByString(reservationStatus);
        Pageable pageRequest = PageRequest.of(page, size, sort.equalsIgnoreCase("desc")? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);

        Page<PersonalReservation> personalReservations = personalReservationRepository.findAllByUser_IdAndReservationStatus(userId, _reservationStatus, pageRequest);
        Page<OrganizationReservation> organizationReservations  = organizationReservationRepository.findAllByUser_IdAndReservationStatus(userId, _reservationStatus, pageRequest);
        log.info("personalReservations size : {}, organizationReservations size : {} selected successfully", personalReservations.getTotalElements(), organizationReservations.getTotalElements());

        List<Object> reservations = new ArrayList<>();
        personalReservations.stream().peek(reservation -> reservations.add(new GetReservationsByReservationStatusResDto));


        return new GetReservationsByReservationStatusResDto(reservations,  );

        return new GetReservationsByReservationStatusResDto(personalReservations, organizationReservations);
    }
}
