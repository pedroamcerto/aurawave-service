package com.aurawave.dto.warehouseDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class WarehouseRequestDto {
    @NotBlank(message = "name é obrigatório")
    @Size(max = 120, message = "name pode ter no máximo 120 caracteres")
    private String name;

    @Size(max = 255, message = "address pode ter no máximo 255 caracteres")
    private String address;
}
