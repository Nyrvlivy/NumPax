package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.CategoryRequestDTO;
import br.com.numpax.API.V1.dto.response.CategoryResponseDTO;
import br.com.numpax.API.V1.exceptions.CategoryNotFoundException;
import br.com.numpax.API.V1.mappers.CategoryMapper;
import br.com.numpax.application.enums.CategoryType;
import br.com.numpax.application.services.CategoryService;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.infrastructure.repositories.CategoryRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        ValidatorUtil.validate(dto);

        Category category = CategoryMapper.toEntity(dto);
        repository.create(category);

        return CategoryMapper.toResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO getCategoryById(String categoryId) {
        Category category = repository.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));
        return CategoryMapper.toResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO updateCategory(String categoryId, CategoryRequestDTO dto) {
        ValidatorUtil.validate(dto);

        Category category = repository.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setIcon(dto.getIcon());
        category.setCategoryType(dto.getCategoryType());
        category.setIsDefault(dto.isDefault());
        category.setUpdatedAt(LocalDateTime.now());

        repository.update(category);

        return CategoryMapper.toResponseDTO(category);
    }

    @Override
    public void deleteCategory(String categoryId) {
        repository.delete(categoryId);
    }

    @Override
    public List<CategoryResponseDTO> listAllCategories() {
        return repository.findAll().stream()
            .map(CategoryMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public void createDefaultCategories() {
        List<Category> defaultCategories = Arrays.asList(
            new Category("Salário", "Recebimento de salário", "salary_icon", CategoryType.INCOME, true),
            new Category("Alimentação", "Gastos com alimentação", "food_icon", CategoryType.EXPENSE, true),
            new Category("Transporte", "Gastos com transporte", "transport_icon", CategoryType.EXPENSE, true),
            new Category("Moradia", "Gastos com moradia", "home_icon", CategoryType.EXPENSE, true),
            new Category("Lazer", "Gastos com lazer", "leisure_icon", CategoryType.EXPENSE, true),
            new Category("Educação", "Gastos com educação", "education_icon", CategoryType.EXPENSE, true),
            new Category("Saúde", "Gastos com saúde", "health_icon", CategoryType.EXPENSE, true),
            new Category("Investimentos", "Investimentos", "investment_icon", CategoryType.INVESTMENT, true),
            new Category("Economias", "Economias", "savings_icon", CategoryType.SAVINGS, true),
            new Category("Outros", "Outras categorias", "other_icon", CategoryType.PERSONAL, true)
        );

        for (Category category : defaultCategories) {
            repository.create(category);
        }
    }

    @Override
    public Category findCategoryById(String categoryId) {
        return repository.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));
    }

}
