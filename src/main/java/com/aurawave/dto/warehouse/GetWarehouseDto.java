package com.aurawave.dto.warehouse;

import com.aurawave.dto.item.GetItemDto;
import com.aurawave.dto.laboratory.GetLaboratoryDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para obter dados de um almoxarifado.
 */
@Data
public class GetWarehouseDto {
    private Long id;
    private GetLaboratoryDto laboratory;  // Referência para o DTO de Laboratory
    private List<GetItemDto> items;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
