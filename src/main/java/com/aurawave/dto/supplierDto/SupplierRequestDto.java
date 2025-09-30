package com.aurawave.dto.supplierDto;

import jakarta.validation.constraints.NotBlank;

public record SupplierRequestDto(@NotBlank String name) { }
