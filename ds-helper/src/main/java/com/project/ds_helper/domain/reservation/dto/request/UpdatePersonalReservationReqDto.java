package com.project.ds_helper.domain.reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ds_helper.domain.reservation.entity.PersonalReservation;
import com.project.ds_helper.domain.reservation.enums.RecipientGenderType;
import com.project.ds_helper.domain.reservation.enums.ReservationStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class UpdatePersonalReservationReqDto {

    @NotBlank(message = "예약 ID는 필수 항목입니다.")
    private String personalReservationId;

    @NotBlank(message = "예약자 성명은 필수 입력 사항입니다.")
    private String name;

    @NotBlank(message = "연락처는 필수 입력 항목입니다.")
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$\n") // 하이픈 있는 상태만 허용
    private String phoneNumber;

    @NotBlank(message = "방문 날짜는 필수 입력 항목입니다.")
    private LocalDate visitDate;

    @NotBlank(message = "시작 시간은 필수 입력 항목입니다.")
    private LocalTime startTime;

    @NotBlank(message = "종료 시간은 필수 입력 항목입니다.")
    private LocalTime endTime;

    @NotBlank(message = "주소는 필수 입력 항목입니다.")
    private String address;

    private String requirement;

    @NotBlank(message = "성별은 필수 입력 항목입니다.")
    private String recipientGenderType;
@JsonFormat
    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    private int recipientNumber;

    // requirement null 체크 후 null이면 빈 문자열 반환
    public String checkRequirementNullAndReturnValue(String requirement){
        if(requirement == null){
            return "";
        }
        return requirement;
    }

    public PersonalReservation toPersonalReservation(PersonalReservation personalReservation, UpdatePersonalReservationReqDto dto){

                personalReservation.setName(dto.getName());
                personalReservation.setPhoneNumber(dto.getPhoneNumber());
                personalReservation.setVisitDate(dto.getVisitDate());
                personalReservation.setStartTime(dto.getStartTime());
                personalReservation.setEndTime(dto.getEndTime());
                personalReservation.setAddress(dto.getAddress());
                personalReservation.setRequirement(dto.checkRequirementNullAndReturnValue(dto.getRequirement()));
                // 성별 체크
                personalReservation.setRecipientGender(RecipientGenderType.findTypeByKorean(dto.getRecipientGenderType()));
                personalReservation.setRecipientNumber(dto.getRecipientNumber());
                personalReservation.setReservationStatus(ReservationStatus.REQUESTED);
        return personalReservation;
    }
}
