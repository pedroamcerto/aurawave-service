package com.aurawave.dto.model;

import com.aurawave.domain.model.Product;
import com.aurawave.dto.item.GetItemDto;
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
    private Product product;
    private List<GetItemDto> items;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}