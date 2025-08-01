package com.project.ds_helper.domain.reservation.entity;

import com.project.ds_helper.common.base.entity.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_organization_reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrganizationReservation extends BaseTime {

    @PrePersist
    private void perPersistGenerateId(){
        this.id = String.valueOf(UUID.randomUUID());
    }

    @Id
    @Column(name = "organization_reservation_id")
    private String id; // 식별자 / UUID
}
