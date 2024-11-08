package br.com.numpax.infrastructure.repositories.impl;

import br.com.numpax.application.enums.CategoryType;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.infrastructure.repositories.CategoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final Connection connection;

    public CategoryRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Category category) {
        String sql = "INSERT INTO Categories (category_id, name, description, icon, category_type, is_active, is_default, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category.getId());
            stmt.setString(2, category.getName());
            stmt.setString(3, category.getDescription());
            stmt.setString(4, category.getIcon());
            stmt.setString(5, category.getCategoryType().name());
            stmt.setInt(6, category.isActive() ? 1 : 0);
            stmt.setInt(7, category.isDefault() ? 1 : 0);
            stmt.setTimestamp(8, Timestamp.valueOf(category.getCreatedAt()));
            stmt.setTimestamp(9, Timestamp.valueOf(category.getUpdatedAt()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar categoria", e);
        }
    }

    @Override
    public Optional<Category> findById(String categoryId) {
        String sql = "SELECT * FROM Categories WHERE category_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Category category = extractCategoryFromResultSet(rs);
                    return Optional.of(category);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar categoria", e);
        }
        return Optional.empty();
    }

    @Override
    public void update(Category category) {
        String sql = "UPDATE Categories SET name = ?, description = ?, icon = ?, category_type = ?, is_active = ?, is_default = ?, updated_at = ? WHERE category_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setString(3, category.getIcon());
            stmt.setString(4, category.getCategoryType().name());
            stmt.setInt(5, category.isActive() ? 1 : 0);
            stmt.setInt(6, category.isDefault() ? 1 : 0);
            stmt.setTimestamp(7, Timestamp.valueOf(category.getUpdatedAt()));
            stmt.setString(8, category.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar categoria", e);
        }
    }

    @Override
    public void delete(String categoryId) {
        String sql = "DELETE FROM Categories WHERE category_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoryId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar categoria", e);
        }
    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM Categories";
        List<Category> categories = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Category category = extractCategoryFromResultSet(rs);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar categorias", e);
        }
        return categories;
    }

    private Category extractCategoryFromResultSet(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getString("category_id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        category.setIcon(rs.getString("icon"));
        category.setCategoryType(CategoryType.valueOf(rs.getString("category_type")));
        category.setIsActive(rs.getInt("is_active") == 1);
        category.setIsDefault(rs.getInt("is_default") == 1);
        category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        category.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return category;
    }
}
