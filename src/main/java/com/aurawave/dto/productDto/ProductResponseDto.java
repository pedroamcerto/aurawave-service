package com.aurawave.dto.productDto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponseDto(UUID id, String name, LocalDateTime createdDate, LocalDateTime modifyDate) { }
