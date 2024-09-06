package br.com.numpax.test.domain.entities;

import br.com.numpax.domain.entities.Category;
import br.com.numpax.domain.enums.CategoryType;

public class CategoryTest {
    public static void main(String[] args) {
        testCategoryCreation();
        testSetName();
        testSetDescription();
        testSetIcon();
        testSetCategoryType();
        testSetActive();
        testSetDefault();
    }

    private static void testCategoryCreation() {
        Category category = new Category("Food", "Expenses on food", "food_icon.png", CategoryType.EXPENSE, true);

        assert category.getId() != null;
        assert "Food".equals(category.getName());
        assert "Expenses on iFood".equals(category.getDescription());
        assert "food_icon.png".equals(category.getIcon());
        assert CategoryType.EXPENSE.equals(category.getCategoryType());
        assert category.isActive();
        assert category.isDefault();
        assert category.getCreatedAt() != null;
        assert category.getUpdatedAt() != null;
    }

    private static void testSetName() {
        Category category = new Category("Food", "Expenses on iFood", "food_icon.png", CategoryType.EXPENSE, true);
        category.setName("New Name");

        assert "New Name".equals(category.getName());
    }

    private static void testSetDescription() {
        Category category = new Category("Food", "Expenses on iFood", "food_icon.png", CategoryType.EXPENSE, true);
        category.setDescription("New Description");

        assert "New Description".equals(category.getDescription());
    }

    private static void testSetIcon() {
        Category category = new Category("Food", "Expenses on iFood", "food_icon.png", CategoryType.EXPENSE, true);
        category.setIcon("new_icon.png");

        assert "new_icon.png".equals(category.getIcon());
    }

    private static void testSetCategoryType() {
        Category category = new Category("Food", "Expenses on iFood", "food_icon.png", CategoryType.EXPENSE, true);
        category.setCategoryType(CategoryType.INCOME);

        assert CategoryType.INCOME.equals(category.getCategoryType());
    }

    private static void testSetActive() {
        Category category = new Category("Food", "Expenses on iFood", "food_icon.png", CategoryType.EXPENSE, true);
        category.setActive(false);

        assert !category.isActive();
    }

    private static void testSetDefault() {
        Category category = new Category("Food", "Expenses on iFood", "food_icon.png", CategoryType.EXPENSE, true);
        category.setDefault(false);

        assert !category.isDefault();
    }
}
