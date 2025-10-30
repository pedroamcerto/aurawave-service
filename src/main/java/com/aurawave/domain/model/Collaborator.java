package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando um colaborador.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collaborator extends Auditable {
    private Long id;
    private String nmCollaborator;
    private String codCompany;
    private String cpf;
}
