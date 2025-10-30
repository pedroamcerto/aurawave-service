package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando o relacionamento entre Item e Modelo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemModel {
    private Long modelId;
    private Long itemId;
}
