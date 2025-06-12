package com.aurawave.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe representando um laboratório.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Entity(name = "laboratory")
@Getter @Setter @NoArgsConstructor
@JsonIgnoreProperties({"warehouses"})
public class Laboratory extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  // Anotação para evitar a recursão infinita na serialização
    private List<Warehouse> warehouses;
}