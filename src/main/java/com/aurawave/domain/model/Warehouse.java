package com.aurawave.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Classe representando um almoxarifado.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Entity(name = "warehouse")
@Data
@JsonIgnoreProperties({"laboratory"})
public class Warehouse extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "laboratory_id", referencedColumnName = "id")
    private Laboratory laboratory;

    @OneToMany(mappedBy = "warehouse")
    private List<Item> items;
}