package com.aurawave.core.domain;

import com.aurawave.core.domain.enumerated.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime eventDate;

    @Enumerated(EnumType.STRING)
    private EventType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
