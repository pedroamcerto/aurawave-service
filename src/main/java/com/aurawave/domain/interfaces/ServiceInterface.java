package com.aurawave.domain.interfaces;

import java.util.List;

public interface ServiceInterface<D, E> {

    void create(E entity);
    default void update(Long id, E entity) {}

    D getById(Long id);
    List<D> getAll();
    default void delete(Long id) {}

}
