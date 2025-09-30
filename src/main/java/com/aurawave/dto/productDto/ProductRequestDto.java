package com.aurawave.dto.productDto;

import jakarta.validation.constraints.NotBlank;

public record ProductRequestDto(
        @NotBlank String name
) {}
