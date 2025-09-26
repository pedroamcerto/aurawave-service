package com.aurawave.core.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Classe representando um laboratório.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Entity(name = "laboratory")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Laboratory extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String address;
}