package com.aurawave.dto.productDto;

import com.aurawave.domain.enumerated.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ProductRequestDto {
    @NotBlank(message = "name é obrigatório")
    @Size(max = 120, message = "name pode ter no máximo 120 caracteres")
    private String name;

    private LocalDateTime validityDate;

    @NotNull(message = "warehouseId é obrigatório")
    private Long warehouseId;

    @NotNull(message = "costPrice é obrigatório")
    @DecimalMin(value = "0.00", inclusive = true, message = "costPrice deve ser >= 0")
    private BigDecimal costPrice;

    @NotNull(message = "status é obrigatório")
    private ProductStatus status;
}
