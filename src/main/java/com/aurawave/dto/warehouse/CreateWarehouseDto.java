package com.aurawave.dto.warehouse;

import com.aurawave.domain.model.Laboratory;
import lombok.Data;

@Data
public class CreateWarehouseDto {

    private Long id;

    private Laboratory laboratory;

}
