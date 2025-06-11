package com.aurawave.dto.laboratory;

import com.aurawave.domain.model.Warehouse;
import lombok.Data;

import java.util.List;

@Data
public class CreateLaboratoryDto {
    private Long id;

    private String name;

    private String address;

    private List<Warehouse> warehouses;
}
