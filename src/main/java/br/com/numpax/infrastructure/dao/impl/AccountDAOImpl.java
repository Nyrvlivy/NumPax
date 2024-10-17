package br.com.numpax.infrastructure.dao.impl;

import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.dao.AccountDAO;
import br.com.numpax.infrastructure.dao.UserDAO;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.application.enums.AccountType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDAOImpl implements AccountDAO {

    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public void saveOrUpdate(Account account) {
        if (existsById(account.getId())) {
            update(account);
        } else {
            save(account);
        }
    }

    private void save(Account account) {
        String sql = """
            INSERT INTO Accounts (account_id, name, description, balance, account_type, is_active, user_id, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getId());
            stmt.setString(2, account.getName());
            stmt.setString(3, account.getDescription());
            stmt.setBigDecimal(4, account.getBalance());
            stmt.setString(5, account.getAccountType().name());
            stmt.setInt(6, account.getIsActive() ? 1 : 0);
            stmt.setString(7, account.getUserId());
            stmt.setTimestamp(8, Timestamp.valueOf(account.getCreatedAt()));
            stmt.setTimestamp(9, Timestamp.valueOf(account.getUpdatedAt()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar a conta", e);
        }
    }

    private void update(Account account) {
        String sql = """
            UPDATE Accounts SET name = ?, description = ?, balance = ?, account_type = ?, is_active = ?, updated_at = ?
            WHERE account_id = ?
        """;
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getName());
            stmt.setString(2, account.getDescription());
            stmt.setBigDecimal(3, account.getBalance());
            stmt.setString(4, account.getAccountType().name());
            stmt.setInt(5, account.getIsActive() ? 1 : 0);
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(7, account.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a conta", e);
        }
    }

    @Override
    public void disableById(String id) {
        String sql = "UPDATE Accounts SET is_active = ?, updated_at = ? WHERE account_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, 0);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(3, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao desativar a conta", e);
        }
    }

    @Override
    public Optional<Account> findById(String id) {
        String sql = "SELECT * FROM Accounts WHERE account_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToAccount(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar a conta por ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Account> findByUserId(String userId) {
        String sql = "SELECT * FROM Accounts WHERE user_id = ?";
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                accounts.add(mapResultSetToAccount(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contas do usuário", e);
        }
        return accounts;
    }

    @Override
    public List<Account> findAll() {
        String sql = "SELECT * FROM Accounts";
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapResultSetToAccount(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas as contas", e);
        }
        return accounts;
    }

    @Override
    public List<Account> findAllActive() {
        String sql = "SELECT * FROM Accounts WHERE is_active = 1";
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapResultSetToAccount(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contas ativas", e);
        }
        return accounts;
    }

    @Override
    public List<Account> findAllInactive() {
        String sql = "SELECT * FROM Accounts WHERE is_active = 0";
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapResultSetToAccount(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contas inativas", e);
        }
        return accounts;
    }

    private boolean existsById(String id) {
        String sql = "SELECT 1 FROM Accounts WHERE account_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência da conta", e);
        }
    }

    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        String userId = rs.getString("user_id");

        return new Account(
            rs.getString("account_id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getBigDecimal("balance"),
            AccountType.valueOf(rs.getString("account_type")),
            rs.getInt("is_active") == 1,
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("updated_at").toLocalDateTime(),
            userId
        );
    }
}
