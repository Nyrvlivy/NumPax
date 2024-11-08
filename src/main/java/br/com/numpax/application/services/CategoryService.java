package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.CategoryRequestDTO;
import br.com.numpax.API.V1.dto.response.CategoryResponseDTO;
import br.com.numpax.infrastructure.entities.Category;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO dto);

    CategoryResponseDTO getCategoryById(String categoryId);

    CategoryResponseDTO updateCategory(String categoryId, CategoryRequestDTO dto);

    void deleteCategory(String categoryId);

    List<CategoryResponseDTO> listAllCategories();

    void createDefaultCategories();

    Category findCategoryById(String categoryId);
}
