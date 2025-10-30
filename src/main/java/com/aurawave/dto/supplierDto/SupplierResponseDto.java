package com.aurawave.dto.supplierDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponseDto {
    private Long id;
    private String nmSupplier;
    private String cpf;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
