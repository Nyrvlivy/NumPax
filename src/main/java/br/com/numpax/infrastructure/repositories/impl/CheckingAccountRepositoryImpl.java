package br.com.numpax.infrastructure.repositories.impl;

import br.com.numpax.application.enums.AccountType;
import br.com.numpax.infrastructure.entities.CheckingAccount;
import br.com.numpax.infrastructure.repositories.CheckingAccountRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CheckingAccountRepositoryImpl implements CheckingAccountRepository {

    private final Connection connection;

    public CheckingAccountRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(CheckingAccount account) {
        String sql = "INSERT INTO CheckingAccounts (account_id, bank_code, agency, account_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getAccountId());
            stmt.setString(2, account.getBankCode());
            stmt.setString(3, account.getAgency());
            stmt.setString(4, account.getAccountNumber());
            stmt.executeUpdate();

            // Inserir na tabela Accounts
            String accountSql = "INSERT INTO Accounts (account_id, name, description, balance, account_type, is_active, user_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement accountStmt = connection.prepareStatement(accountSql)) {
                accountStmt.setString(1, account.getAccountId());
                accountStmt.setString(2, account.getName());
                accountStmt.setString(3, account.getDescription());
                accountStmt.setBigDecimal(4, BigDecimal.ZERO);
                accountStmt.setString(5, account.getAccountType().toString());
                accountStmt.setInt(6, account.isActive() ? 1 : 0);
                accountStmt.setString(7, account.getUserId().getUserId());
                accountStmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                accountStmt.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                accountStmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar conta corrente", e);
        }
    }

    @Override
    public Optional<CheckingAccount> findById(String accountId) {
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN CheckingAccounts c ON a.account_id = c.account_id " +
                "WHERE a.account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CheckingAccount account = new CheckingAccount();
                    account.setAccountId(rs.getString("account_id"));
                    account.setName(rs.getString("name"));
                    account.setDescription(rs.getString("description"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                    account.setIsActive(rs.getInt("is_active") == 1);
                    account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    account.setBankCode(rs.getString("bank_code"));
                    account.setAgency(rs.getString("agency"));
                    account.setAccountNumber(rs.getString("account_number"));
                    return Optional.of(account);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta corrente", e);
        }
        return Optional.empty();
    }

    @Override
    public void update(CheckingAccount account) {
        String sql = "UPDATE Accounts SET name = ?, description = ?, updated_at = ?, is_active = ? WHERE account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getName());
            stmt.setString(2, account.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(4, account.isActive() ? 1 : 0);
            stmt.setString(5, account.getAccountId());
            stmt.executeUpdate();

            String checkingSql = "UPDATE CheckingAccounts SET bank_code = ?, agency = ?, account_number = ? WHERE account_id = ?";
            try (PreparedStatement checkingStmt = connection.prepareStatement(checkingSql)) {
                checkingStmt.setString(1, account.getBankCode());
                checkingStmt.setString(2, account.getAgency());
                checkingStmt.setString(3, account.getAccountNumber());
                checkingStmt.setString(4, account.getAccountId());
                checkingStmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar conta corrente", e);
        }
    }

    @Override
    public void delete(String accountId) {
        String sql = "DELETE FROM CheckingAccounts WHERE account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            stmt.executeUpdate();

            String accountSql = "DELETE FROM Accounts WHERE account_id = ?";
            try (PreparedStatement accountStmt = connection.prepareStatement(accountSql)) {
                accountStmt.setString(1, accountId);
                accountStmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta corrente", e);
        }
    }

    @Override
    public List<CheckingAccount> findAll() {
        List<CheckingAccount> accounts = new ArrayList<>();
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN CheckingAccounts c ON a.account_id = c.account_id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CheckingAccount account = new CheckingAccount();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setDescription(rs.getString("description"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setIsActive(rs.getInt("is_active") == 1);
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                account.setBankCode(rs.getString("bank_code"));
                account.setAgency(rs.getString("agency"));
                account.setAccountNumber(rs.getString("account_number"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas correntes", e);
        }
        return accounts;
    }

    @Override
    public List<CheckingAccount> findAllActive() {
        List<CheckingAccount> accounts = new ArrayList<>();
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN CheckingAccounts c ON a.account_id = c.account_id " +
                "WHERE a.is_active = 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CheckingAccount account = new CheckingAccount();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setDescription(rs.getString("description"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setIsActive(rs.getInt("is_active") == 1);
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                account.setBankCode(rs.getString("bank_code"));
                account.setAgency(rs.getString("agency"));
                account.setAccountNumber(rs.getString("account_number"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas correntes ativas", e);
        }
        return accounts;
    }

    @Override
    public List<CheckingAccount> findAllInactive() {
        List<CheckingAccount> accounts = new ArrayList<>();
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN CheckingAccounts c ON a.account_id = c.account_id " +
                "WHERE a.is_active = 0";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CheckingAccount account = new CheckingAccount();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setDescription(rs.getString("description"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setIsActive(rs.getInt("is_active") == 1);
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                account.setBankCode(rs.getString("bank_code"));
                account.setAgency(rs.getString("agency"));
                account.setAccountNumber(rs.getString("account_number"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas correntes inativas", e);
        }
        return accounts;
    }
}
