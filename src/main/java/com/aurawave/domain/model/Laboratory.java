package com.aurawave.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Classe representando um laboratório.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Entity(name = "laboratory")
@Getter @Setter @NoArgsConstructor
public class Laboratory extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "laboratory")
    private List<Warehouse> warehouses;
}