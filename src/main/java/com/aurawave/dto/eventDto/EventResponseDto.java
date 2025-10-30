package com.aurawave.dto.eventDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDto {
    private Long id;
    private String eventType;
    private String event;
    private Long itemId;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
