package com.aurawave.dto.model;

import com.aurawave.dto.item.GetModelItemDto;
import com.aurawave.dto.product.GetModelProductDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para obter dados de um modelo.
 */
@Data
public class GetModelDto {
    private Long id;
    private String name;
    private GetModelProductDto product;
    private List<GetModelItemDto> items;

    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}