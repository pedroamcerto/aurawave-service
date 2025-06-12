package com.aurawave.domain.model;

import com.aurawave.domain.enumerated.ItemStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe representando um item no sistema.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Entity(name = "item")
@Getter @Setter @NoArgsConstructor
public class Item extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne
    private Warehouse warehouses;
}