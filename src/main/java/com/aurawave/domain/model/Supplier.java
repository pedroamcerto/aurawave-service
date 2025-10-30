package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando um fornecedor.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier extends Auditable {
    private Long id;
    private String nmSupplier;
    private String cpf;
}
