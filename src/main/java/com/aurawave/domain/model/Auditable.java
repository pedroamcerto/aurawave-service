package com.aurawave.domain.model;

import lombok.Data;

import java.time.LocalDateTime;
/**
 * Classe base que armazena os campos de auditoria: data de criação e data de modificação.
 */
@Data
public class Auditable {

    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
