package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando o relacionamento entre Evento e Sessão (Rastreia).
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rastreia extends Auditable {
    private Long eventId;
    private Long sessionId;
}
