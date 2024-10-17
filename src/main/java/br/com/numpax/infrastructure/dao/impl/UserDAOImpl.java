package br.com.numpax.infrastructure.dao.impl;

import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.dao.UserDAO;
import br.com.numpax.infrastructure.entities.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public void saveOrUpdate(User user) {
        if (existsById(user.getId())) { // Verificando pelo ID agora
            update(user);
        } else {
            save(user);
        }
    }

    private void save(User user) {
        String sql = """
        INSERT INTO Users (user_id, name, email, password, birthdate, is_active, created_at, updated_at)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    """;
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            stmt.setString(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getHashedPassword());
            stmt.setDate(5, Date.valueOf(user.getBirthdate()));
            stmt.setInt(6, user.getIsActive() ? 1 : 0);
            stmt.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));
            stmt.setTimestamp(8, Timestamp.valueOf(user.getUpdatedAt()));

            int rowsAffected = stmt.executeUpdate();
            //System.out.println("Linhas afetadas: " + rowsAffected);

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar o usuário", e);
        }
    }

    private void update(User user) {
        String sql = """
        UPDATE Users SET name = ?, email = ?, password = ?, birthdate = ?, is_active = ?, updated_at = ?
        WHERE user_id = ?
    """;
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getHashedPassword());
            stmt.setDate(4, Date.valueOf(user.getBirthdate()));
            stmt.setInt(5, user.getIsActive() ? 1 : 0); // Ajuste aqui
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(7, user.getId());

            int rowsAffected = stmt.executeUpdate();
            // System.out.println("Linhas afetadas: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar o usuário", e);
        }
    }

    @Override
    public void disableById(String id) {
        String sql = "UPDATE Users SET is_active = ?, updated_at = ? WHERE user_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, 0);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(3, id);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Usuário desativado. Linhas afetadas: " + rowsAffected);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao desativar o usuário", e);
        }
    }



    @Override
    public Optional<User> findSimpleById(String id) {
        String sql = "SELECT user_id, name, email, birthdate FROM Users WHERE user_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToSimpleUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findDetailedById(String id) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToDetailedUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String id) {
        return findDetailedById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        return findUser(sql, email);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os usuários", e);
        }
        return users;
    }

    @Override
    public List<User> findAllActive() {
        String sql = "SELECT * FROM Users WHERE is_active = 1";
        List<User> users = new ArrayList<>();

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os usuários ativos", e);
        }
        return users;
    }

    @Override
    public List<User> findAllInactive() {
        String sql = "SELECT * FROM Users WHERE is_active = 0";
        List<User> users = new ArrayList<>();

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os usuários inativos", e);
        }
        return users;
    }

    private Optional<User> findUser(String sql, String param) {
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, param);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
        return Optional.empty();
    }

    private boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM Users WHERE email = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência do e-mail", e);
        }
    }

    private boolean existsById(String id) {
        String sql = "SELECT 1 FROM Users WHERE user_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência do usuário", e);
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getString("user_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getDate("birthdate").toLocalDate(),
            rs.getInt("is_active") == 1,
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }

    private User mapResultSetToSimpleUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getString("user_id"),
            rs.getString("name"),
            rs.getString("email"),
            null,
            rs.getDate("birthdate") != null ? rs.getDate("birthdate").toLocalDate() : null,
            null,
            null,
            null
        );
    }

    private User mapResultSetToDetailedUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getString("user_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getDate("birthdate") != null ? rs.getDate("birthdate").toLocalDate() : null,
            rs.getBoolean("is_active"),
            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
            rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null
        );
    }
}
