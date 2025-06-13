package com.aurawave.dto.product;

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
    private List<GetModelProductDto> models;

    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}