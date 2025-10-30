package com.aurawave.dto.itemDto;

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
public class ItemRequestDto {
    @NotBlank(message = "nmItem é obrigatório")
    @Size(max = 100, message = "nmItem pode ter no máximo 100 caracteres")
    private String nmItem;

    @NotBlank(message = "status é obrigatório")
    @Size(max = 60, message = "status pode ter no máximo 60 caracteres")
    private String status;

    @NotNull(message = "modelId é obrigatório")
    private Long modelId;

    @NotNull(message = "batchId é obrigatório")
    private Long batchId;
}
