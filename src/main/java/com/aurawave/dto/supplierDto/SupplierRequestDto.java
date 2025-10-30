package com.aurawave.dto.supplierDto;

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
public class SupplierRequestDto {
    @NotBlank(message = "nmSupplier é obrigatório")
    @Size(max = 100, message = "nmSupplier pode ter no máximo 100 caracteres")
    private String nmSupplier;

    @NotBlank(message = "cpf é obrigatório")
    @Size(min = 11, max = 11, message = "cpf deve ter exatamente 11 caracteres")
    private String cpf;
}
