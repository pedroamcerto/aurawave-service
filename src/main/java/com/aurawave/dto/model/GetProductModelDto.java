package com.aurawave.dto.model;

import com.aurawave.dto.item.GetModelItemDto;
import lombok.Data;

import java.util.List;

@Data
public class GetProductModelDto {
    private Long id;
    private String name;
    private List<GetModelItemDto> items;
}
