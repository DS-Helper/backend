package com.project.ds_helper.domain.reservation.entity;

import com.project.ds_helper.domain.base.entity.BaseTime;
import com.project.ds_helper.domain.reservation.enums.RecipientGenderType;
import com.project.ds_helper.domain.reservation.enums.ReservationStatus;
import com.project.ds_helper.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tb_organization_reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrganizationReservation extends BaseTime {

    @PrePersist
    private void perPersistGenerateId(){
        this.id = String.valueOf(UUID.randomUUID());
    }

    @Id
    @Column(name = "personal_reservation_id")
    private String id; // 식별자 / UUID

    @Column(name = "organization_name")
    private String organizationName; // 기관명

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user; // 신청한 유저

    @Column(name = "name")
    private String name; // 신청자 이름

    @Column(name = "phone_number")
    private String phoneNumber; // 신청자 연락처

    @Column(name = "visit_date")
    private LocalDate visitDate; // 방문 날짜

    @Column(name = "start_time")
    private LocalTime startTime; // 봉사 시작 시간

    @Column(name = "end_time")
    private LocalTime endTime; // 봉사 종료 시간

    @Column(name = "address")
    private String address; // 주소

    @Column(name = "requirement", columnDefinition = "TEXT")
    private String requirement; // 요청 사항

    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_gender")
    private RecipientGenderType recipientGender; // 도움 받을 사람 성별

    @Column(name = "recipient_number")
    private int recipientNumber; // 도움 받는 사람 수

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus = ReservationStatus.REQUESTED; // 예약 상태

}
