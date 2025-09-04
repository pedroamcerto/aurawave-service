package com.aurawave.dao;

import com.aurawave.domain.interfaces.ProductInterface;
import com.aurawave.domain.model.Product;

import java.util.List;

public class ProductDao implements ProductInterface {
    @Override
    public Long create(Product product) {
        return 0L;
    }

    @Override
    public void update(Long id, Product product) {
        ProductInterface.super.update(id, product);
    }

    @Override
    public void update(Long id) {
        ProductInterface.super.update(id);
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public List<Product> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {
        ProductInterface.super.delete(id);
    }
}
