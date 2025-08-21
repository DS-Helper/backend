package com.project.ds_helper.domain.reservation.dto.response;

import com.amazonaws.services.ec2.model.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ds_helper.common.dto.response.PageResDto;
import com.project.ds_helper.domain.reservation.entity.OrganizationReservation;
import com.project.ds_helper.domain.reservation.entity.PersonalReservation;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record GetReservationsByReservationStatusResDto(
//       @JsonProperty("personalReservation") List<GetPersonalReservationResDto> personalReservations,
//       @JsonProperty("organizationReservation") List<GetOrganizationReservationResDto> organizationReservations
        List<Reservation> reservations,
        PageResDto page
) {
    @Builder
    static class Reservation{
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


    }

        public static Reservation fromOrgReservationtoReservation(OrganizationReservation organizationReservation){
            return Reservation.builder()
                    .organizationReservationId(organizationReservation.getId())
                    .organizationName(organizationReservation.getOrganizationName())
                    .reservationHolderId(organizationReservation.getUser().getId())
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

        public static PageResDto toPageResDto(Page<PersonalReservation> per, Page<OrganizationReservation> org){
            int page = per.getNumber(); // 0-based (Slice일 땐 호출자가 0 고정으로 넣어도 OK)
            int size = per.getSize();
            long totalElements = per.getTotalElements();  // Slice면 -1 또는 0
            int totalPages = per.getTotalPages(); // Slice면 -1
            boolean first = per.isFirst();
            boolean last = per.isLast();
            boolean hasNext = per.hasNext();
            boolean hasPrevious = per.hasPrevious();
            Sort sort = per.getSort();    // 정렬 정보 에코백
        }
}
