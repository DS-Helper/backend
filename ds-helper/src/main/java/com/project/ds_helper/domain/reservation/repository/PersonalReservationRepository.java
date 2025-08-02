package com.project.ds_helper.domain.reservation.repository;

import com.project.ds_helper.domain.reservation.entity.PersonalReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalReservationRepository extends JpaRepository<PersonalReservation, String> {

}
