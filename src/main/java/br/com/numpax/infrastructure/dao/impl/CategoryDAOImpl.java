package br.com.numpax.infrastructure.dao.impl;

import br.com.numpax.application.enums.CategoryType;
import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.dao.CategoryDAO;
import br.com.numpax.infrastructure.entities.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDAOImpl implements CategoryDAO {
    private final ConnectionManager connectionManager;

    public CategoryDAOImpl() {
        this.connectionManager = ConnectionManager.getInstance();
    }
    @Override
    public void save(Category category) {
        String sql = """
            INSERT INTO Categories (category_id, name, description, is_active) VALUES (?, ?, ?, ?)
            """;
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getId());
            stmt.setString(2, category.getName());
            stmt.setString(3, category.getDescription());
            stmt.setBoolean(4, category.isActive());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar a categoria", e);
        }
    }

    @Override
    public void update(Category category) {
        String sql = """
            UPDATE Categories SET name = ?, description = ?, is_active = ? WHERE category_id = ?
            """;
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setBoolean(3, category.isActive());
            stmt.setString(4, category.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a categoria", e);
        }
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM Categories WHERE category_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
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
