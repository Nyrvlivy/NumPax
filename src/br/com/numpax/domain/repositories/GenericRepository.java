package br.com.numpax.domain.repositories;

import java.util.List;
import java.util.Optional;

public interface GenericRepository <T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T entity);
    void delete(ID id);
}
