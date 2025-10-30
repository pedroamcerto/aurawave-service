package com.aurawave.dto.manufacturerDto;

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
public class ManufacturerRequestDto {
    @NotBlank(message = "nmManufacturer é obrigatório")
    @Size(max = 100, message = "nmManufacturer pode ter no máximo 100 caracteres")
    private String nmManufacturer;
}
