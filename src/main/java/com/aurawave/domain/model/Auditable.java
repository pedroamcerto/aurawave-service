package com.aurawave.domain.model;

import lombok.*;

import java.time.LocalDateTime;
/**
 * Classe base que armazena os campos de auditoria: data de criação e data de modificação.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auditable {
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
}
