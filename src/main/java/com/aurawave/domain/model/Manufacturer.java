package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando um fabricante.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer extends Auditable {
    private Long id;
    private String nmManufacturer;
}
