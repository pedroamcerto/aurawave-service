package com.aurawave.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Classe representando um produto.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Entity
@Table(name = "products")
@Getter @Setter @NoArgsConstructor
public class Product extends Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
}