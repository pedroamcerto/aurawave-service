package com.aurawave.dto.warehouse;

import com.aurawave.dto.laboratory.GetLaboratoryDto;
import lombok.Data;

@Data
public class CreateWarehouseDto {

    private Long id;

    private GetLaboratoryDto laboratory;

}
