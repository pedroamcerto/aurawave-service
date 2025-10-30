package com.aurawave.dto.warehouseDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class WarehouseRequestDto {
    @NotBlank(message = "name é obrigatório")
    @Size(max = 80, message = "name pode ter no máximo 80 caracteres")
    private String name;

    @NotNull(message = "laboratoryId é obrigatório")
    private Long laboratoryId;
}
