package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.request.CategoryRequestDTO;
import br.com.numpax.API.V1.dto.response.CategoryResponseDTO;
import br.com.numpax.infrastructure.entities.Category;

import java.time.LocalDateTime;
import java.util.UUID;

public class CategoryMapper {

    public static Category toEntity(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setId(UUID.randomUUID().toString());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setIcon(dto.getIcon());
        category.setCategoryType(dto.getCategoryType());
        category.setIsDefault(dto.isDefault());
        category.setIsActive(true);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        return category;
    }

    public static CategoryResponseDTO toResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setIcon(category.getIcon());
        dto.setCategoryType(category.getCategoryType());
        dto.setActive(category.isActive());
        dto.setDefault(category.isDefault());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }
}
