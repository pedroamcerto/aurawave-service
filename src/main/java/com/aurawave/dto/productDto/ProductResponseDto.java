package com.aurawave.dto.productDto;

import com.aurawave.domain.enumerated.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private LocalDateTime validityDate;
    private Long warehouseId;
    private BigDecimal costPrice;
    private ProductStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
