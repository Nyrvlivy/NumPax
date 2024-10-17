package br.com.numpax.infrastructure.dao.impl;

import br.com.numpax.application.enums.AccountType;
import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.dao.AccountDAO;
import br.com.numpax.infrastructure.dao.UserDAO;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.RegularAccount;
import br.com.numpax.infrastructure.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDAOImpl implements AccountDAO {
    private final UserDAO userDAO;
    private final ConnectionManager connectionManager;

    public AccountDAOImpl() {
        this.userDAO = new UserDAOImpl();
        this.connectionManager = ConnectionManager.getInstance();
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
    public List<Account> findByUser(User user) {
        String sql = "SELECT * FROM Accounts WHERE user_id = ?";
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getId());
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
    public void save(Account account) {
        String sql = """
            INSERT INTO Accounts (account_id, name, description, balance, is_active, user_id, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getId());
            stmt.setString(2, account.getName());
            stmt.setString(3, account.getDescription());
            stmt.setBigDecimal(4, account.getBalance());
            stmt.setBoolean(5, account.getIsActive());
            stmt.setString(6, account.getUser().getId());
            stmt.setTimestamp(7, Timestamp.valueOf(account.getCreatedAt()));
            stmt.setTimestamp(8, Timestamp.valueOf(account.getUpdatedAt()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar a conta", e);
        }
    }

    @Override
    public void update(Account account) {
        String sql = """
            UPDATE Accounts SET name = ?, description = ?, balance = ?, is_active = ?, updated_at = ?
            WHERE account_id = ?
            """;
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getName());
            stmt.setString(2, account.getDescription());
            stmt.setBigDecimal(3, account.getBalance());
            stmt.setBoolean(4, account.getIsActive());
            stmt.setTimestamp(5, Timestamp.valueOf(account.getUpdatedAt()));
            stmt.setString(6, account.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a conta", e);
        }
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM Accounts WHERE account_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar a conta", e);
        }
    }

    @Override
    public boolean existsById(String id) {
        String sql = "SELECT 1 FROM Accounts WHERE account_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar se a conta existe por ID", e);
        }
    }

    @Override
    public Optional<RegularAccount> findRegularAccountById(String id) {
        String sql = "SELECT * FROM RegularAccounts WHERE account_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToRegularAccount(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta regular", e);
        }
        return Optional.empty();
    }

    private RegularAccount mapResultSetToRegularAccount(ResultSet rs) throws SQLException {

        String userId = rs.getString("user_id");

        User user = new UserDAOImpl().findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado para o ID: " + userId));

        AccountType accountType = AccountType.valueOf(rs.getString("account_type"));

        return new RegularAccount(
            rs.getString("name"),
            rs.getString("description"),
            user,
            accountType
        );
    }

    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        String userId = rs.getString("user_id");

        User user = userDAO.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado para o ID: " + userId));

        return new Account(
            rs.getString("account_id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getBigDecimal("balance"),
            rs.getBoolean("is_active"),
            user,
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
