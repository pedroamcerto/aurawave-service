package com.aurawave.core.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "manufacturers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Manufacturer extends Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
}
