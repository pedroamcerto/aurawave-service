package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando um item.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item extends Auditable {
    private Long id;
    private String nmItem;
    private String status;
    private Long modelId;
    private Long batchId;
}
