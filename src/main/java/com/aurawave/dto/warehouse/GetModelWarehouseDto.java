package com.aurawave.dto.warehouse;

import com.aurawave.dto.item.GetWarehouseItemDto;
import com.aurawave.dto.laboratory.GetWarehouseLaboratoryDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetModelWarehouseDto {
    private Long id;
    private GetWarehouseLaboratoryDto laboratory;

    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
