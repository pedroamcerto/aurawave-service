package com.aurawave.repository;

import com.aurawave.core.domain.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> { }
