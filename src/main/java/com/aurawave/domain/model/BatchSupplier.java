package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando o relacionamento entre Lote e Fornecedor.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchSupplier {
    private Long supplierId;
    private Long batchId;
}
