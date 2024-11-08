package br.com.numpax.infrastructure.repositories.impl;

import br.com.numpax.application.enums.AccountType;
import br.com.numpax.infrastructure.entities.SavingsAccount;
import br.com.numpax.infrastructure.repositories.SavingsAccountRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SavingsAccountRepositoryImpl implements SavingsAccountRepository {

    private final Connection connection;

    public SavingsAccountRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(SavingsAccount account) {
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
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar conta poupança", e);
        }

        String sql =
            "INSERT INTO SavingsAccounts " +
                "(account_id, " +
                "nearest_deadline, " +
                "furthest_deadline, " +
                "latest_deadline, " +
                "average_tax_rate, " +
                "number_of_fixed_investments, " +
                "total_maturity_amount, " +
                "total_deposit_amount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getAccountId());
            stmt.setTimestamp(2, Timestamp.valueOf(account.getNearestDeadline()));
            stmt.setTimestamp(3, Timestamp.valueOf(account.getFurthestDeadline()));
            stmt.setTimestamp(4, Timestamp.valueOf(account.getLatestDeadline()));
            stmt.setBigDecimal(5, account.getAverageTaxRate());
            stmt.setInt(6, account.getNumberOfFixedInvestments());
            stmt.setBigDecimal(7, account.getTotalMaturityAmount());
            stmt.setBigDecimal(8, account.getTotalDepositAmount());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar conta poupança", e);
        }
    }

    @Override
    public Optional<SavingsAccount> findById(String accountId) {
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN SavingsAccounts s ON a.account_id = s.account_id " +
                "WHERE a.account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    SavingsAccount account = new SavingsAccount();
                    account.setAccountId(rs.getString("account_id"));
                    account.setName(rs.getString("name"));
                    account.setDescription(rs.getString("description"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                    account.setIsActive(rs.getInt("is_active") == 1);
                    account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    account.setNearestDeadline(rs.getTimestamp("nearest_deadline").toLocalDateTime());
                    account.setFurthestDeadline(rs.getTimestamp("furthest_deadline").toLocalDateTime());
                    account.setLatestDeadline(rs.getTimestamp("latest_deadline").toLocalDateTime());
                    account.setAverageTaxRate(rs.getBigDecimal("average_tax_rate"));
                    account.setNumberOfFixedInvestments(rs.getInt("number_of_fixed_investments"));
                    account.setTotalMaturityAmount(rs.getBigDecimal("total_maturity_amount"));
                    account.setTotalDepositAmount(rs.getBigDecimal("total_deposit_amount"));
                    return Optional.of(account);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta poupança", e);
        }
        return Optional.empty();
    }

    @Override
    public void update(SavingsAccount account) {
        String sql =
            "UPDATE Accounts " +
                "SET name = ?, " +
                "description = ?, " +
                "updated_at = ?, " +
                "is_active = ? " +
                "WHERE account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getName());
            stmt.setString(2, account.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(4, account.isActive() ? 1 : 0);
            stmt.setString(5, account.getAccountId());
            stmt.executeUpdate();

            String checkingSql =
                "UPDATE SavingsAccounts " +
                    "SET nearest_deadline = ?, " +
                    "furthest_deadline = ?, " +
                    "latest_deadline = ?, " +
                    "average_tax_rate = ?, " +
                    "number_of_fixed_investments = ?, " +
                    "total_maturity_amount = ?, " +
                    "total_deposit_amount = ? " +
                    "WHERE account_id = ?";
            try (PreparedStatement checkingStmt = connection.prepareStatement(checkingSql)) {
                checkingStmt.setTimestamp(1, Timestamp.valueOf(account.getNearestDeadline()));
                checkingStmt.setTimestamp(2, Timestamp.valueOf(account.getFurthestDeadline()));
                checkingStmt.setTimestamp(3, Timestamp.valueOf(account.getLatestDeadline()));
                checkingStmt.setBigDecimal(4, account.getAverageTaxRate());
                checkingStmt.setInt(5, account.getNumberOfFixedInvestments());
                checkingStmt.setBigDecimal(6, account.getTotalMaturityAmount());
                checkingStmt.setBigDecimal(7, account.getTotalDepositAmount());
                checkingStmt.setString(8, account.getAccountId());
                checkingStmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar conta poupança", e);
        }
    }

    @Override
    public void delete(String accountId) {
        String sql = "DELETE FROM SavingsAccounts WHERE account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            stmt.executeUpdate();

            String accountSql = "DELETE FROM Accounts WHERE account_id = ?";
            try (PreparedStatement accountStmt = connection.prepareStatement(accountSql)) {
                accountStmt.setString(1, accountId);
                accountStmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta poupança", e);
        }
    }

    @Override
    public List<SavingsAccount> findAll() {
        List<SavingsAccount> accounts = new ArrayList<>();
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN SavingsAccounts s ON a.account_id = s.account_id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                SavingsAccount account = new SavingsAccount();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setDescription(rs.getString("description"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setIsActive(rs.getInt("is_active") == 1);
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                account.setNearestDeadline(rs.getTimestamp("nearest_deadline").toLocalDateTime());
                account.setFurthestDeadline(rs.getTimestamp("furthest_deadline").toLocalDateTime());
                account.setLatestDeadline(rs.getTimestamp("latest_deadline").toLocalDateTime());
                account.setAverageTaxRate(rs.getBigDecimal("average_tax_rate"));
                account.setNumberOfFixedInvestments(rs.getInt("number_of_fixed_investments"));
                account.setTotalMaturityAmount(rs.getBigDecimal("total_maturity_amount"));
                account.setTotalDepositAmount(rs.getBigDecimal("total_deposit_amount"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas poupança", e);
        }
        return accounts;
    }

    @Override
    public List<SavingsAccount> findAllActive() {
        List<SavingsAccount> accounts = new ArrayList<>();
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN SavingsAccounts s ON a.account_id = s.account_id " +
                "WHERE is_active = 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                SavingsAccount account = new SavingsAccount();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setDescription(rs.getString("description"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setIsActive(rs.getInt("is_active") == 1);
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                account.setNearestDeadline(rs.getTimestamp("nearest_deadline").toLocalDateTime());
                account.setFurthestDeadline(rs.getTimestamp("furthest_deadline").toLocalDateTime());
                account.setLatestDeadline(rs.getTimestamp("latest_deadline").toLocalDateTime());
                account.setAverageTaxRate(rs.getBigDecimal("average_tax_rate"));
                account.setNumberOfFixedInvestments(rs.getInt("number_of_fixed_investments"));
                account.setTotalMaturityAmount(rs.getBigDecimal("total_maturity_amount"));
                account.setTotalDepositAmount(rs.getBigDecimal("total_deposit_amount"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas poupança ativas", e);
        }
        return accounts;
    }

    @Override
    public List<SavingsAccount> findAllInactive() {
        List<SavingsAccount> accounts = new ArrayList<>();
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN SavingsAccounts s ON a.account_id = s.account_id " +
                "WHERE is_active = 0";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                SavingsAccount account = new SavingsAccount();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setDescription(rs.getString("description"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setIsActive(rs.getInt("is_active") == 1);
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                account.setNearestDeadline(rs.getTimestamp("nearest_deadline").toLocalDateTime());
                account.setFurthestDeadline(rs.getTimestamp("furthest_deadline").toLocalDateTime());
                account.setLatestDeadline(rs.getTimestamp("latest_deadline").toLocalDateTime());
                account.setAverageTaxRate(rs.getBigDecimal("average_tax_rate"));
                account.setNumberOfFixedInvestments(rs.getInt("number_of_fixed_investments"));
                account.setTotalMaturityAmount(rs.getBigDecimal("total_maturity_amount"));
                account.setTotalDepositAmount(rs.getBigDecimal("total_deposit_amount"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas poupança inativas", e);
        }
        return accounts;
    }

}