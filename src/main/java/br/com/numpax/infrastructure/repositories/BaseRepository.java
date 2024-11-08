package br.com.numpax.infrastructure.repositories;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {

    void create(T entity);

    Optional<T> findById(ID id);

    void update(T entity);

    void delete(ID id);

    List<T> findAll();

    List<T> findAllActive();

    List<T> findAllInactive();
}
