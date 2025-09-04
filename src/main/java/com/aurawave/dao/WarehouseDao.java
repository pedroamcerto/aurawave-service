package com.aurawave.dao;

import com.aurawave.domain.interfaces.WarehouseInterface;
import com.aurawave.domain.model.Product;
import com.aurawave.domain.model.Warehouse;

import java.util.List;

public class WarehouseDao implements WarehouseInterface {
    @Override
    public Long create(Warehouse warehouse) {
        return 0L;
    }

    @Override
    public void update(Long id, Warehouse warehouse) {
        WarehouseInterface.super.update(id, warehouse);
    }

    @Override
    public void update(Long id) {
        WarehouseInterface.super.update(id);
    }

    @Override
    public Warehouse getById(Long id) {
        return null;
    }

    @Override
    public List<Product> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {
        WarehouseInterface.super.delete(id);
    }
}
