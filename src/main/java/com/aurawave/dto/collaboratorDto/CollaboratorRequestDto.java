package com.aurawave.dto.collaboratorDto;

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
public class CollaboratorRequestDto {
    @NotBlank(message = "nmCollaborator é obrigatório")
    @Size(max = 300, message = "nmCollaborator pode ter no máximo 300 caracteres")
    private String nmCollaborator;

    @NotBlank(message = "codCompany é obrigatório")
    @Size(max = 350, message = "codCompany pode ter no máximo 350 caracteres")
    private String codCompany;

    @NotBlank(message = "cpf é obrigatório")
    @Size(min = 11, max = 11, message = "cpf deve ter exatamente 11 caracteres")
    private String cpf;
}
