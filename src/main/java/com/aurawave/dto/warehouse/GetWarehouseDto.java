package com.aurawave.dto.warehouse;

import com.aurawave.dto.item.GetWarehouseItemDto;
import com.aurawave.dto.laboratory.GetWarehouseLaboratoryDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para obter dados de um almoxarifado.
 */
@Data
public class GetWarehouseDto {
    private Long id;
    private GetWarehouseLaboratoryDto laboratory;
    private List<GetWarehouseItemDto> items;

    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
