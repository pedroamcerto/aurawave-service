package com.aurawave.dto.item;

import com.aurawave.domain.enumerated.ItemStatus;
import lombok.Data;

@Data
public class UpdateItemStatusDto {
    private ItemStatus status;
}
