package br.com.numpax.services;

import java.util.List;
import java.util.Optional;

import br.com.numpax.domain.entities.Category;

public interface CategoryService {
    Category createCategory(Category category);
    Optional<Category> getCategoryById(String id);
    List<Category> getAllCategories();
    Category updateCategory(String id, Category category);
    void deleteCategory(String id);
    List<Category> getActiveCategories();
}
