package com.aurawave.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Classe representando um produto.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Entity(name = "product")
@Getter @Setter @NoArgsConstructor
public class Product extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "product")
    private List<Model> models;
}