package com.aurawave.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "collaborators")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Collaborator {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(name = "company_id")
    private UUID companyId;

    @Column(name = "collaborator_registration", unique = true)
    private String registration;
}
