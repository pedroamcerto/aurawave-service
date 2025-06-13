package com.aurawave.dto.model;

import com.aurawave.dto.product.GetModelProductDto;
import lombok.Data;

@Data
public class GetItemModelDto {
    private Long id;
    private String name;
    private GetModelProductDto product;
}
