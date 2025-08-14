package com.project.ds_helper.domain.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GetReservationsByReservationStatusResDto(
       @JsonProperty("personalReservation") List<GetPersonalReservationResDto> personalReservations,
       @JsonProperty("organizationReservation") List<GetOrganizationReservationResDto> organizationReservations
) {}
