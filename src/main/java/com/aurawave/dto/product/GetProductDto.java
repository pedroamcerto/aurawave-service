package com.aurawave.dto.product;

import com.aurawave.dto.model.GetModelDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para obter dados de um produto.
 */
@Data
public class GetProductDto {
    private Long id;
    private String name;
    private List<GetModelDto> models;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}