package com.aurawave.domain.model;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
/**
 * Classe base que armazena os campos de auditoria: data de criação e data de modificação.
 */
@Getter @Setter
public class Auditable {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
