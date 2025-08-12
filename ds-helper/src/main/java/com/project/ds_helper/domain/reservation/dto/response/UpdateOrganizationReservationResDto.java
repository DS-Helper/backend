package com.project.ds_helper.domain.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ds_helper.domain.reservation.entity.OrganizationReservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UpdateOrganizationReservationResDto {

    private String organizationReservationId;

    private String organizationName;

    private String reservationHolderId;

    private String reservationHolder;

    @JsonFormat(pattern = "^01[016789]-\\d{3,4}-\\d{4}$\n") // 하이픈 있는 상태만 허용
    private String reservationPhoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private String address;

    private String requirement;

    private String recipientGender;

    private int recipientNumber;

    private String reservationStatus;

    public static UpdateOrganizationReservationResDto toDto(OrganizationReservation organizationReservation){
        return UpdateOrganizationReservationResDto.builder()
                .organizationReservationId(organizationReservation.getId())
                .organizationName(organizationReservation.getOrganizationName())
                .reservationHolder(organizationReservation.getName())
                .reservationPhoneNumber(organizationReservation.getPhoneNumber())
                .visitDate(organizationReservation.getVisitDate())
                .startTime(organizationReservation.getStartTime())
                .endTime(organizationReservation.getEndTime())
                .address(organizationReservation.getAddress())
                .requirement(organizationReservation.getRequirement())
                .recipientGender(organizationReservation.getRecipientGender().getKorean())
                .recipientNumber(organizationReservation.getRecipientNumber())
                .reservationStatus(organizationReservation.getReservationStatus().getKorean())
                .build();
    }
}
