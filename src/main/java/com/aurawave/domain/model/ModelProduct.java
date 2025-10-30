package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando o relacionamento entre Modelo e Produto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelProduct {
    private Long productId;
    private Long modelId;
}
