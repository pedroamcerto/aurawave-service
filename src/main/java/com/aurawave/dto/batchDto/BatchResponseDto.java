package com.aurawave.dto.batchDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchResponseDto {
    private Long id;
    private String nmBatch;
    private Long supplierId;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
