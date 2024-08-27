package br.com.numpax.domain.repositories;

import java.util.List;

import br.com.numpax.domain.entities.Category;

public interface CategoryRepository extends GenericRepository<Category, String>{
    List<Category> findByIsActiveTrue();
}
