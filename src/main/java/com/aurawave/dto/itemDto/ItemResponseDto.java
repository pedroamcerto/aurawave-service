package com.aurawave.dto.itemDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {
    private Long id;
    private String nmItem;
    private String status;
    private Long modelId;
    private Long batchId;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
