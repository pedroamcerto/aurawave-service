package com.aurawave.dto.batchDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BatchResponseDto {
    private UUID id;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
    private String name;
    private LocalDate expirationDate;
    private UUID supplierId;
}
