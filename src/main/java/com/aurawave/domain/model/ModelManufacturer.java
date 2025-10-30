package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando o relacionamento entre Modelo e Fabricante.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelManufacturer {
    private Long manufacturerId;
    private Long modelId;
}
