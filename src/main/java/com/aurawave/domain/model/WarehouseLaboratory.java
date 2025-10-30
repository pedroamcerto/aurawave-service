package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando o relacionamento entre Almoxarifado e Laboratório.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseLaboratory {
    private Long laboratoryId;
    private Long warehouseId;
}
