package com.aurawave.dto.manufacturerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerResponseDto {
    private Long id;
    private String nmManufacturer;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
