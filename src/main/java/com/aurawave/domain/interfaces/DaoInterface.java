package com.aurawave.domain.interfaces;

import java.util.List;

public interface DaoInterface<T, ID> {

    ID create(T object);
    default void update(ID id, T object) {}
    T getById(ID id);
    List<T> getAll();
    default void delete(ID id) {}
}
