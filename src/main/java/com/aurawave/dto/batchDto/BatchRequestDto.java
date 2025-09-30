package com.aurawave.dto.batchDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record BatchRequestDto(
        @NotBlank String name,
        @NotNull LocalDate expirationDate,
        @NotNull UUID supplierId
) {}
