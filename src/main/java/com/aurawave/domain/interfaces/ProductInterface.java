package com.aurawave.domain.interfaces;

import com.aurawave.domain.model.Product;

import java.util.List;

public interface ProductInterface {

    Long create(Product product);
    default void update(Long id, Product product) {}

    default void update(Long id) {}

    Product getById(Long id);
    List<Product> getAll();
    default void delete(Long id) {}
}
