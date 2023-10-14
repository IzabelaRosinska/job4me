package miwm.job4me.services;

import java.util.Set;

public interface BaseService<T, ID> {

    Set<T> findAll();

    T findById(ID id);

    T save(T object);

    void delete(T object);

    void deleteById(ID id);
}
