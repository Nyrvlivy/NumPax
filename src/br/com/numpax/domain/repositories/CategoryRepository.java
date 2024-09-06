package br.com.numpax.domain.repositories;

import br.com.numpax.domain.entities.Category;

import java.util.List;

public interface CategoryRepository extends GenericRepository<Category, String> {
    List<Category> findByIsActiveTrue();
}
