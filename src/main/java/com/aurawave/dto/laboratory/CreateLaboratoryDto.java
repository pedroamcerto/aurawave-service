package com.aurawave.dto.laboratory;

import lombok.Data;

/**
 * DTO para criação de um laboratório.
 */
@Data
public class CreateLaboratoryDto {
    private String name;
    private String address;
}
