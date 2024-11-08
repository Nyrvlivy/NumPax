package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    void create(Category category);

    Optional<Category> findById(String categoryId);

    void update(Category category);

    void delete(String categoryId);

    List<Category> findAll();
}
