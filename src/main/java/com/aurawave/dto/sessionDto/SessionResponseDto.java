package com.aurawave.dto.sessionDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponseDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long warehouseId;
    private Long collaboratorId;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
