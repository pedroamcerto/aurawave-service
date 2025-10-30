package com.aurawave.dto.collaboratorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollaboratorResponseDto {
    private Long id;
    private String nmCollaborator;
    private String codCompany;
    private String cpf;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
