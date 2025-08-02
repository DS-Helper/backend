package com.project.ds_helper.domain.reservation.dto.response;

import com.project.ds_helper.domain.reservation.entity.PersonalReservation;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreatePersonalReservationResDto {

    private String personalReservationId;

    private String reservationHolderId;

    private String reservationHolder;

    private String reservationPhoneNumber;

    private LocalDate visitDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String address;

    private String requirement;

    private String recipientGender;

    private int recipientNumber;

    private String reservationStatus;

    public static CreatePersonalReservationResDto toDto(PersonalReservation personalReservation){
        return CreatePersonalReservationResDto.builder()
                .personalReservationId(personalReservation.getId())
                .personalReservationId(personalReservation.getUser().getId())
                .reservationHolder(personalReservation.getName())
                .reservationPhoneNumber(personalReservation.getPhoneNumber())
                .visitDate(personalReservation.getVisitDate())
                .startTime(personalReservation.getStartTime())
                .endTime(personalReservation.getEndTime())
                .address(personalReservation.getAddress())
                .requirement(personalReservation.getRequirement())
                .recipientGender(personalReservation.getRecipientGender().getKorean())
                .recipientNumber(personalReservation.getRecipientNumber())
                .reservationStatus(personalReservation.getReservationStatus().getKorean())
                .build();
    }


}
