package com.project.ds_helper.domain.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ds_helper.domain.reservation.entity.PersonalReservation;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GetPersonalReservationResDto {

    private String personalReservationId;

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

    public static GetPersonalReservationResDto toDto(PersonalReservation personalReservation){
        return GetPersonalReservationResDto.builder()
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

    public static List<GetPersonalReservationResDto> toDtoList(List<PersonalReservation> personalReservations){
        if(personalReservations.isEmpty()) {
            return new ArrayList<GetPersonalReservationResDto>();
        }
        return personalReservations.stream().map(GetPersonalReservationResDto::toDto).toList();


    }

}
