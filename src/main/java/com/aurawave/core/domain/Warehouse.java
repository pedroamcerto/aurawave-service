package com.aurawave.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Classe representando um almoxarifado.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Entity
@Table(name = "warehouses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Warehouse extends Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "laboratory_id")
    private Laboratory laboratory;
}