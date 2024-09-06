package br.com.numpax.services;

import br.com.numpax.domain.entities.Category;
import br.com.numpax.domain.repositories.CategoryRepository;
import br.com.numpax.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getCategoryById(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(String id, Category updatedCategory) {
        return categoryRepository.findById(id)
            .map(category -> {
                category.setName(updatedCategory.getName());
                category.setDescription(updatedCategory.getDescription());
                category.setIcon(updatedCategory.getIcon());
                category.setCategoryType(updatedCategory.getCategoryType());
                category.setActive(updatedCategory.isActive());
                category.setDefault(updatedCategory.isDefault());
                return categoryRepository.save(category);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    public void deleteCategory(String id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
    }

    @Override
    public List<Category> getActiveCategories() {
        return categoryRepository.findByIsActiveTrue();
    }
}
