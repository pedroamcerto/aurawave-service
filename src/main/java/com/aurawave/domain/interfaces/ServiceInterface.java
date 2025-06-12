package com.aurawave.domain.interfaces;

import com.aurawave.domain.enumerated.ItemStatus;

import java.util.List;

public interface ServiceInterface<D, E> {

    void create(E entity);
    default void update(Long id, E entity) {}

    void update(Long id, ItemStatus status);

    D getById(Long id);
    List<D> getAll();
    default void delete(Long id) {}

}
