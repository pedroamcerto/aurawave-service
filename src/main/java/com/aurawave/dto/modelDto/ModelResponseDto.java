package com.aurawave.dto.modelDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelResponseDto {
    private Long id;
    private String nmModel;
    private Long manufacturerId;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
