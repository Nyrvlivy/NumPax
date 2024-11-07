package br.com.numpax.infrastructure.dao.impl;

import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.dao.UserDAO;
import br.com.numpax.infrastructure.entities.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public void saveOrUpdate(User user) {
        try (Connection conn = connectionManager.getConnection()) {
            conn.setAutoCommit(false); // Start transaction
            if (existsById(user.getId(), conn)) {
                update(user, conn);
            } else {
                save(user, conn);
            }
            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar ou atualizar o usuário", e);
        }
    }

    private void save(User user, Connection conn) throws SQLException {
        String sql = """
            INSERT INTO Users (user_id, name, email, password, birthdate, is_active, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            stmt.setDate(5, Date.valueOf(user.getBirthdate()));
            stmt.setBoolean(6, user.getIsActive());
            stmt.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));
            stmt.setTimestamp(8, Timestamp.valueOf(user.getUpdatedAt()));
            stmt.executeUpdate();
        }
    }

    private void update(User user, Connection conn) throws SQLException {
        String sql = """
            UPDATE Users SET name = ?, email = ?, password = ?, birthdate = ?, is_active = ?, updated_at = ?
            WHERE user_id = ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getHashedPassword());
            stmt.setDate(4, Date.valueOf(user.getBirthdate()));
            stmt.setBoolean(5, user.getIsActive());
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(7, user.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void disableById(String id) {
        String sql = "UPDATE Users SET is_active = ?, updated_at = ? WHERE user_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false); // Start transaction
            stmt.setBoolean(1, false);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(3, id);
            stmt.executeUpdate();
            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao desativar o usuário", e);
        }
    }

    @Override
    public Optional<User> findSimpleById(String id) {
        String sql = "SELECT user_id, name, email, birthdate FROM Users WHERE user_id = ?";
        return findUser(sql, id);
    }

    @Override
    public Optional<User> findDetailedById(String id) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        return findUser(sql, id);
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
        return findUsers(sql);
    }

    @Override
    public List<User> findAllActive() {
        String sql = "SELECT * FROM Users WHERE is_active = 1";
        return findUsers(sql);
    }

    @Override
    public List<User> findAllInactive() {
        String sql = "SELECT * FROM Users WHERE is_active = 0";
        return findUsers(sql);
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

    private List<User> findUsers(String sql) {
        List<User> users = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuários", e);
        }
        return users;
    }

    private boolean existsById(String id, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM Users WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getString("user_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getDate("birthdate").toLocalDate(),
            rs.getBoolean("is_active"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }

    public Optional<User> login(String email, String password) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (BCrypt.checkpw(password, storedHash)) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao realizar login", e);
        }
        return Optional.empty();
    }
}
