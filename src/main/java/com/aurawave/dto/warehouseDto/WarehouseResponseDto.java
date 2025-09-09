package com.aurawave.dto.warehouseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponseDto {
    private Long id;
    private String name;
    private String address;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
