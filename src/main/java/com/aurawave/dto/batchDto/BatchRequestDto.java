package com.aurawave.dto.batchDto;

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
public class BatchRequestDto {
    @NotBlank(message = "nmBatch é obrigatório")
    @Size(max = 200, message = "nmBatch pode ter no máximo 200 caracteres")
    private String nmBatch;

    @NotNull(message = "supplierId é obrigatório")
    private Long supplierId;
}
