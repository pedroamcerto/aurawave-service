package com.aurawave.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "warehouse")
@Getter @Setter @NoArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "laboratory_id")
    private Laboratory laboratory;

    @OneToMany(mappedBy = "warehouses")
    private List<Item> items;
}
