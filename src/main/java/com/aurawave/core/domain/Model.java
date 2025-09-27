package com.aurawave.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Classe representando um modelo de produto.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Entity
@Table(name = "models")
@Getter @Setter @NoArgsConstructor
public class Model extends Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}