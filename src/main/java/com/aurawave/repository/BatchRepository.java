package com.aurawave.repository;

import com.aurawave.core.domain.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BatchRepository extends JpaRepository<Batch, UUID> {

    boolean existsByNameAndSupplier_Id(String name, UUID supplierId);
    boolean existsByNameAndSupplier_IdAndIdNot(String name, UUID supplierId, UUID id);
}
