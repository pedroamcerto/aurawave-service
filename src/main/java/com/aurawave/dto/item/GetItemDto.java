package com.aurawave.dto.item;

import com.aurawave.domain.enumerated.ItemStatus;
import com.aurawave.dto.model.GetModelDto;
import com.aurawave.dto.warehouse.GetWarehouseDto;
import lombok.Data;

import java.time.LocalDateTime;
/**
 * DTO para obter dados de um item.
 */
@Data
public class GetItemDto {
    private Long id;
    private String name;
    private ItemStatus status;
    private GetModelDto model;
    private GetWarehouseDto warehouse;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}