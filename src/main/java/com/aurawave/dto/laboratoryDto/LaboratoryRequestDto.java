package com.aurawave.dto.laboratoryDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaboratoryRequestDto {
    @NotBlank(message = "nmLaboratory é obrigatório")
    @Size(max = 65, message = "nmLaboratory pode ter no máximo 65 caracteres")
    private String nmLaboratory;
}
