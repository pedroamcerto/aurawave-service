package com.aurawave.dto.model;

import com.aurawave.domain.model.Product;
import com.aurawave.dto.item.GetItemDto;
import lombok.Data;

import java.util.List;

@Data
public class CreateModelDto {
    private Long id;
    private String name;
    private Product product;
    private List<GetItemDto> items;
}
