package com.aurawave.domain.model;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Classe representando uma sessão.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session extends Auditable {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long warehouseId;
    private Long collaboratorId;
}
