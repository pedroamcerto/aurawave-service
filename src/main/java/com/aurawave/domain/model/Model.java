package com.aurawave.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Classe representando um modelo de produto.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Entity(name = "model")
@Getter @Setter @NoArgsConstructor
public class Model extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "model")
    private List<Item> items;
}