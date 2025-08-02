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
@Table(name = "tb_personal_reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PersonalReservation extends BaseTime {

    @PrePersist
    private void perPersistGenerateId(){
        this.id = String.valueOf(UUID.randomUUID());
    }

    @Id
    @Column(name = "personal_reservation_id")
    private String id; // 식별자 / UUID

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "address")
    private String address; // 주소

    @Column(name = "requirement", columnDefinition = "TEXT")
    private String requirement; // 요청 사항

    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_gender")
    private RecipientGenderType recipientGender;

    @Column(name = "recipient_number")
    private int recipientNumber; // 도움 받는 사람 수

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus; // 예약 상태

}
