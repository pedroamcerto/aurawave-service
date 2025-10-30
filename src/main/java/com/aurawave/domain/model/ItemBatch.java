package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando o relacionamento entre Item e Lote.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemBatch {
    private Long batchId;
    private Long itemId;
}
