package com.aurawave.dto.item;

import com.aurawave.domain.enumerated.ItemStatus;
import com.aurawave.dto.warehouse.GetModelWarehouseDto;
import lombok.Data;


@Data
public class GetModelItemDto {
    private Long id;
    private String name;
    private ItemStatus status;
    private GetModelWarehouseDto warehouse;
}
