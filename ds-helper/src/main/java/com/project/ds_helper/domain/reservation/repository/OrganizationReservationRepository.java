package com.project.ds_helper.domain.reservation.repository;

import com.project.ds_helper.domain.reservation.entity.OrganizationReservation;
import com.project.ds_helper.domain.reservation.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationReservationRepository extends JpaRepository<OrganizationReservation, String> {

    List<OrganizationReservation> findAllByUser_Id(String userId);

    List<OrganizationReservation> findAllByUser_IdAndReservationStatus(String userId, ReservationStatus reservationStatus);
}
