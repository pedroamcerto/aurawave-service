package com.aurawave.dto.warehouse;

import com.aurawave.dto.item.GetWarehouseItemDto;
import lombok.Data;

import java.util.List;

@Data
public class GetLaboratoryWarehouseDto {
    private Long id;
    private List<GetWarehouseItemDto> items;
}
