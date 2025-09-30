package com.aurawave.dto.itemDto;

import com.aurawave.core.domain.enumerated.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ItemResponseDto {
    private UUID id;
    private String name;
    private LocalDate expirationDate;
    private ItemStatus status;
    private UUID batchId;
    private UUID warehouseId;
    private UUID modelId;
}
