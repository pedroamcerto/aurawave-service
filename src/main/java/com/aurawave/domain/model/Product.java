package com.aurawave.domain.model;

import com.aurawave.domain.enumerated.ProductStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Classe representando um produto.
 * Herda os campos de auditoria de {@link Auditable}.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends Auditable {
    private Long id;
    private String name;
    private LocalDateTime validityDate;
    private Long warehouseId;
    private BigDecimal costPrice;
    private ProductStatus status;
}