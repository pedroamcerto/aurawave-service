package com.aurawave.repository;

import com.aurawave.core.domain.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CollaboratorRepository extends JpaRepository<Collaborator, UUID> {}
