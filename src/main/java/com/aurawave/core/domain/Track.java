package com.aurawave.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
    name = "tracks",
    uniqueConstraints = @UniqueConstraint(
            name = "uk_track_event_session",
            columnNames = {"event_id_event", "session_id_session"}
    )
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Track {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id_event", referencedColumnName = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id_session", referencedColumnName = "session_id", nullable = false)
    private Session session;
}
