package com.aurawave.dto.warehouse;

import com.aurawave.dto.laboratory.GetWarehouseLaboratoryDto;
import lombok.Data;

@Data
public class GetItemWarehouseDto {
    private Long id;
    private GetWarehouseLaboratoryDto laboratory;
}
