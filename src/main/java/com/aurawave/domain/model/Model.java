package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando um modelo de produto.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Model extends Auditable {
    private Long id;
    private String nmModel;
    private Long manufacturerId;
}
