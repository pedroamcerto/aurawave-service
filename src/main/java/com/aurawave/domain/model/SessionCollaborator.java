package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando o relacionamento entre Sessão e Colaborador.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionCollaborator {
    private Long collaboratorId;
    private Long sessionId;
}
