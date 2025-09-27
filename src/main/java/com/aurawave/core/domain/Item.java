package com.aurawave.core.domain;

import com.aurawave.core.domain.enumerated.ItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Classe representando um item no sistema.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Entity
@Table(name = "items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Item extends Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    private LocalDate expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;
}
