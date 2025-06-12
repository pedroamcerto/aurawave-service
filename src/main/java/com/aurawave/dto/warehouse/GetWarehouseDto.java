package com.aurawave.dto.warehouse;

import com.aurawave.domain.model.Item;
import com.aurawave.domain.model.Laboratory;
import lombok.Data;

import java.util.List;

@Data
public class GetWarehouseDto {

    private Long id;

    private Laboratory laboratory;

    private List<Item> items;
}
