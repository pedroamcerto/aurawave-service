package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando um lote.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch extends Auditable {
    private Long id;
    private String nmBatch;
    private Long supplierId;
}
