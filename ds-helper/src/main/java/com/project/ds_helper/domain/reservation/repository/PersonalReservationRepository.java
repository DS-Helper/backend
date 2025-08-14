package com.project.ds_helper.domain.reservation.repository;

import com.project.ds_helper.domain.reservation.entity.PersonalReservation;
import com.project.ds_helper.domain.reservation.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalReservationRepository extends JpaRepository<PersonalReservation, String> {

    List<PersonalReservation> findAllByUser_Id(String userId);

    List<PersonalReservation> findAllByUser_IdAndReservationStatus(String userId, ReservationStatus reservationStatus);
}
