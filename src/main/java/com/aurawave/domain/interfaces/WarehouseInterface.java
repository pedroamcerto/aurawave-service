package com.aurawave.domain.interfaces;

import com.aurawave.domain.model.Warehouse;

import java.util.List;

public interface WarehouseInterface {

    Long create(Warehouse warehouse);
    default void update(Long id, Warehouse warehouse) {}

    default void update(Long id) {}

    Warehouse getById(Long id);
    List<Warehouse> getAll();
    default void delete(Long id) {}
}
