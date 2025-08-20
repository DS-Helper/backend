package com.project.ds_helper.domain.reservation.repository;

import com.project.ds_helper.domain.reservation.entity.OrganizationReservation;
import com.project.ds_helper.domain.reservation.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationReservationRepository extends JpaRepository<OrganizationReservation, String> {

    @EntityGraph(attributePaths = {"user"})
    List<OrganizationReservation> findAllByUser_Id(String userId);

    @EntityGraph(attributePaths = {"user"})
    Page<OrganizationReservation> findAllByUser_IdAndReservationStatus(String userId, ReservationStatus reservationStatus, Pageable pageable);
}
