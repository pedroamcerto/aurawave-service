package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando o relacionamento entre Sessão e Almoxarifado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionWarehouse {
    private Long warehouseId;
    private Long sessionId;
}
