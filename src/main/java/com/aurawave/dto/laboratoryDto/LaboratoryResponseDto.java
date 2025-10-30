package com.aurawave.dto.laboratoryDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaboratoryResponseDto {
    private Long id;
    private String nmLaboratory;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
