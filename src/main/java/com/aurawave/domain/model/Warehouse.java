package com.aurawave.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Classe representando um almoxarifado.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Entity(name = "warehouse")
@Getter @Setter @NoArgsConstructor
@JsonIgnoreProperties({"laboratory"})
public class Warehouse extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "laboratory_id")
    @JsonBackReference  // Anotação para indicar que esta é a "referência de volta", prevenindo a recursão infinita
    private Laboratory laboratory;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;
}