package com.project.ds_helper.domain.reservation.entity;

import com.project.ds_helper.common.base.entity.BaseTime;
import com.project.ds_helper.domain.reservation.enums.RecipientGenderType;
import com.project.ds_helper.domain.reservation.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tb_personal_reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonalReservation extends BaseTime {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "personal_reservation_id")
    private UUID id; // 식별자 / UUID

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    @Column(name = "visit_time")
    private LocalTime visitTime;

    @Column(name = "address")
    private String address; // 주소

    @Column(name = "requirement")
    private String requirement; // 요청 사항

    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_gender")
    private RecipientGenderType recipientGender;

    @Column(name = "recipient_number")
    private int recipientNumber; // 주소

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus; // 주소

}
