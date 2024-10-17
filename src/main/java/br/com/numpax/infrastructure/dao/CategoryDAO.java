package br.com.numpax.infrastructure.dao;

import br.com.numpax.infrastructure.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {
    void save(Category category);
    void update(Category category);
    void deleteById(String id);
    Optional<Category> findById(String id);
    List<Category> findAll();
    List<Category> findActiveCategories();
}
