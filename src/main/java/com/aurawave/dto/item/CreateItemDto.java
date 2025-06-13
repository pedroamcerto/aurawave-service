package com.aurawave.dto.item;

import com.aurawave.domain.enumerated.ItemStatus;
import com.aurawave.dto.model.GetModelDto;
import com.aurawave.dto.warehouse.GetWarehouseDto;
import lombok.Data;
/**
 * DTO para criação de item.
 */
@Data
public class CreateItemDto {
    private String name;
    private ItemStatus status;
    private GetModelDto model;
    private GetWarehouseDto warehouse;
}
