package com.aurawave.dto.warehouse;

import com.aurawave.domain.model.Laboratory;
import com.aurawave.dto.RelationDto;
import com.aurawave.dto.laboratory.GetLaboratoryDto;
import lombok.Data;

@Data
public class CreateWarehouseDto {

    private Long laboratoryId;
}