package com.aurawave.dto.manufacturerDto;

import jakarta.validation.constraints.NotBlank;

public record ManufacturerRequestDto(
        @NotBlank String name
) {}
