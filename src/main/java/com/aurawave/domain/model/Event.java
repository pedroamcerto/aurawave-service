package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando um evento.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event extends Auditable {
    private Long id;
    private String eventType;
    private String event;
    private Long itemId;
}
