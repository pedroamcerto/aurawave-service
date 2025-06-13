package com.aurawave.dto.model;

import com.aurawave.dto.product.GetProductDto;
import lombok.Data;

@Data
public class CreateModelDto {
    private Long id;
    private String name;
    private GetProductDto product;
}
