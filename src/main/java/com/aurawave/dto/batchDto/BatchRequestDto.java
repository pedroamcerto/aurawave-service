package com.aurawave.dto.batchDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BatchRequestDto {
    @NotBlank private String name;
    private LocalDate expirationDate;
    @NotNull private UUID supplierId;
}
