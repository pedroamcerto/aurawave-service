package com.aurawave.dto.modelDto;

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
public class ModelRequestDto {
    @NotBlank(message = "nmModel é obrigatório")
    @Size(max = 60, message = "nmModel pode ter no máximo 60 caracteres")
    private String nmModel;

    @NotNull(message = "manufacturerId é obrigatório")
    private Long manufacturerId;
}
