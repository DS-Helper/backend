package com.project.ds_helper.domain.reservation.dto.request;

import com.project.ds_helper.domain.reservation.entity.OrganizationReservation;
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
public class CreateOrganizationReservationReqDto {

    @NotBlank(message = "기관명은 필수 입력 사항입니다.")
    private String organizationName;

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

    public OrganizationReservation toOrganizationReservation(CreateOrganizationReservationReqDto dto){

        return OrganizationReservation.builder()
                .organizationName(dto.getOrganizationName())
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .visitDate(dto.getVisitDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .address(dto.getAddress())
                .requirement(dto.checkRequirementNullAndReturnValue(dto.getRequirement()))
                // 성별 체크
                .recipientGender(RecipientGenderType.findTypeByKorean(dto.getRecipientGenderType()))
                .recipientNumber(dto.getRecipientNumber())
                .reservationStatus(ReservationStatus.REQUESTED)
                .build();
    }
}
