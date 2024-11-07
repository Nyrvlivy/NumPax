package br.com.numpax.infrastructure.dao.impl;

import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.dao.CategoryDAO;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.application.enums.CategoryType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDAOImpl implements CategoryDAO {

    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public void save(Category category) {
        String sql = """
            INSERT INTO Categories (category_id, name, description, category_type_id, is_active)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); // Start transaction

            stmt.setString(1, category.getId());
            stmt.setString(2, category.getName());
            stmt.setString(3, category.getDescription());
            stmt.setInt(4, getCategoryTypeId(category.getCategoryType(), conn));
            stmt.setBoolean(5, category.isActive());

            stmt.executeUpdate();
            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar a categoria", e);
        }
    }

    @Override
    public void update(Category category) {
        String sql = """
            UPDATE Categories SET name = ?, description = ?, category_type_id = ?, is_active = ?
            WHERE category_id = ?
        """;
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); // Start transaction

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setInt(3, getCategoryTypeId(category.getCategoryType(), conn));
            stmt.setBoolean(4, category.isActive());
            stmt.setString(5, category.getId());

            stmt.executeUpdate();
            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a categoria", e);
        }
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM Categories WHERE category_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); // Start transaction

            stmt.setString(1, id);
            stmt.executeUpdate();

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar a categoria", e);
        }
    }

    @Override
    public Optional<Category> findById(String id) {
        String sql = "SELECT * FROM Categories WHERE category_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar a categoria por ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM Categories";
        List<Category> categories = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas as categorias", e);
        }
        return categories;
    }

    @Override
    public List<Category> findActiveCategories() {
        String sql = "SELECT * FROM Categories WHERE is_active = 1";
        List<Category> categories = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar categorias ativas", e);
        }
        return categories;
    }

    private int getCategoryTypeId(CategoryType categoryType, Connection conn) throws SQLException {
        String sql = "SELECT id FROM CategoryTypes WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoryType.name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new RuntimeException("Category type not found");
            }
        }
    }

    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        return new Category(
            rs.getString("category_id"),
            rs.getString("name"),
            rs.getString("description"),
            CategoryType.fromString(rs.getString("category_type")),
            rs.getBoolean("is_active")
        );
    }
}
