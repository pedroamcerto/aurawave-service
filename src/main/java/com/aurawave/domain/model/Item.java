package com.aurawave.domain.model;

import com.aurawave.domain.enumerated.ItemStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "item")
@Getter @Setter @NoArgsConstructor
public class Item extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "emmit_date")
    private LocalDateTime emmitDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne
    private List<Warehouse> warehouses;

}
