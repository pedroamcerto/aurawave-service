package com.aurawave.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe representando um almoxarifado.
 * Herda os campos de auditoria de {@link Auditable}.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Warehouse extends Auditable {

    private Long id;
    private String name;
    private String address;
}