package com.aurawave.dto.sessionDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequestDto {
    @NotNull(message = "start é obrigatório")
    private LocalDateTime start;

    private LocalDateTime end;

    @NotNull(message = "warehouseId é obrigatório")
    private Long warehouseId;

    @NotNull(message = "collaboratorId é obrigatório")
    private Long collaboratorId;
}
