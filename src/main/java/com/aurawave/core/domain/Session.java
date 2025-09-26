package com.aurawave.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Session extends Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "collaborator_id")
    private Collaborator collaborator;
}
