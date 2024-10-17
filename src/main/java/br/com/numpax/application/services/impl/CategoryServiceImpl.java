package br.com.numpax.application.services.impl;

import br.com.numpax.application.services.CategoryService;
import br.com.numpax.infrastructure.dao.CategoryDAO;
import br.com.numpax.infrastructure.dao.impl.CategoryDAOImpl;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.API.V1.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl() {
        this.categoryDAO = new CategoryDAOImpl();
    }

    @Override
    public Category createCategory(Category category) {
        categoryDAO.save(category);
        return category;
    }

    @Override
    public Optional<Category> getCategoryById(String id) {
        return categoryDAO.findById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    @Override
    public Category updateCategory(String id, Category updatedCategory) {
        Category category = categoryDAO.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));

        category.setName(updatedCategory.getName());
        category.setDescription(updatedCategory.getDescription());
        category.setActive(updatedCategory.isActive());

        categoryDAO.update(category);
        return category;
    }

    @Override
    public void deleteCategory(String id) {
        if (categoryDAO.findById(id).isPresent()) {
            categoryDAO.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Categoria não encontrada com ID: " + id);
        }
    }

    @Override
    public List<Category> getActiveCategories() {
        return categoryDAO.findActiveCategories();
    }
}
