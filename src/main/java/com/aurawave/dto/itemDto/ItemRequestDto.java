package com.aurawave.dto.itemDto;

import com.aurawave.core.domain.enumerated.ItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ItemRequestDto {
    @NotBlank private String name;
    @NotNull private UUID batchId;
    private LocalDate expirationDate;
    @NotNull private UUID warehouseId;
    @NotNull private UUID modelId;
    @NotNull private ItemStatus status;
}
