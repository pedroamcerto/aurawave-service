package com.aurawave.domain.model;

import com.aurawave.domain.enumerated.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Classe representando um produto.
 * Herda os campos de auditoria de {@link Auditable}.
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Product extends Auditable {

    private Long id;
    private String name;
    private LocalDateTime validityDate;
    private Long WarehouseId;
    private BigDecimal costPrice;
    private ProductStatus status;
}