package com.aurawave.core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Classe representando um laboratório.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Entity
@Table(name = "laboratorys")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Laboratory extends Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "address_id", nullable = false, unique = true)
    private Address address;
}