package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando um laboratório.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Laboratory extends Auditable {
    private Long id;
    private String nmLaboratory;
}
