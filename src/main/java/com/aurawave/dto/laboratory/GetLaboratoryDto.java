package com.aurawave.dto.laboratory;

import com.aurawave.dto.warehouse.GetLaboratoryWarehouseDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
/**
 * DTO para obter dados de um laboratório.
 */
@Data
public class GetLaboratoryDto {
    private Long id;
    private String name;
    private String address;
    private List<GetLaboratoryWarehouseDto> warehouses;

    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
