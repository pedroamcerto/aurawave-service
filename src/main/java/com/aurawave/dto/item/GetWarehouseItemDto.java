package com.aurawave.dto.item;

import com.aurawave.domain.enumerated.ItemStatus;
import com.aurawave.dto.model.GetItemModelDto;
import lombok.Data;

@Data
public class GetWarehouseItemDto {
    private Long id;
    private String name;
    private ItemStatus status;
    private GetItemModelDto model;
}
