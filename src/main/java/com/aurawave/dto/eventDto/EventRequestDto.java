package com.aurawave.dto.eventDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDto {
    @NotBlank(message = "eventType é obrigatório")
    @Size(max = 20, message = "eventType pode ter no máximo 20 caracteres")
    private String eventType;

    @NotBlank(message = "event é obrigatório")
    @Size(max = 20, message = "event pode ter no máximo 20 caracteres")
    private String event;

    @NotNull(message = "itemId é obrigatório")
    private Long itemId;
}
