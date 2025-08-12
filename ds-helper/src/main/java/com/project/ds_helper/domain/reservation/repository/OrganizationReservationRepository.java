package com.project.ds_helper.domain.reservation.repository;

import com.project.ds_helper.domain.reservation.entity.OrganizationReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationReservationRepository extends JpaRepository<OrganizationReservation, String> {

}
