package com.aurawave.dto.manufacturerDto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ManufacturerResponseDto(UUID id, String name, LocalDateTime createdDate, LocalDateTime modifyDate) { }
