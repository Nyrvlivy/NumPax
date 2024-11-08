package br.com.numpax.infrastructure.repositories.impl;

import br.com.numpax.application.enums.AccountType;
import br.com.numpax.infrastructure.entities.GoalAccount;
import br.com.numpax.infrastructure.repositories.GoalAccountRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoalAccountRepositoryImpl implements GoalAccountRepository {

    private final Connection connection;

    public GoalAccountRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(GoalAccount account) {
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
            throw new RuntimeException("Erro ao criar conta de metas", e);
        }

        String sql =
            "INSERT INTO GoalAccounts " +
                "(account_id, " +
                "target_value, " +
                "amount_value, " +
                "target_tax_rate, " +
                "monthly_tax_rate, " +
                "monthly_estimate, " +
                "monthly_achievement, " +
                "category_id, " +
                "target_date, " +
                "start_date, " +
                "end_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getAccountId());
            statement.setBigDecimal(2, account.getTargetValue());
            statement.setBigDecimal(3, account.getAmountValue());
            statement.setBigDecimal(4, account.getTargetTaxRate());
            statement.setBigDecimal(5, account.getMonthlyTaxRate());
            statement.setBigDecimal(6, account.getMonthlyEstimate());
            statement.setBigDecimal(7, account.getMonthlyAchievement());
            statement.setString(8, account.getCategory().getId());
            statement.setTimestamp(9, Timestamp.valueOf(account.getTargetDate().atStartOfDay()));
            statement.setTimestamp(10, Timestamp.valueOf(account.getStartDate().atStartOfDay()));
            statement.setTimestamp(11, Timestamp.valueOf(account.getEndDate().atStartOfDay()));
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar conta de metas", e);
        }
    }

    @Override
    public Optional<GoalAccount> findById(String accountId) {
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN GoalAccounts s ON a.account_id = s.account_id " +
                "WHERE a.account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    GoalAccount account = new GoalAccount();
                    account.setAccountId(rs.getString("account_id"));
                    account.setName(rs.getString("name"));
                    account.setDescription(rs.getString("description"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                    account.setIsActive(rs.getInt("is_active") == 1);
                    account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    account.setTargetValue(rs.getBigDecimal("target_value"));
                    account.setAmountValue(rs.getBigDecimal("amount_value"));
                    account.setTargetTaxRate(rs.getBigDecimal("target_tax_rate"));
                    account.setMonthlyTaxRate(rs.getBigDecimal("monthly_tax_rate"));
                    account.setMonthlyEstimate(rs.getBigDecimal("monthly_estimate"));
                    account.setMonthlyAchievement(rs.getBigDecimal("monthly_achievement"));
                    account.getCategory().setId(rs.getString("category_id"));
                    account.setTargetDate(rs.getTimestamp("target_date").toLocalDateTime().toLocalDate());
                    account.setStartDate(rs.getTimestamp("start_date").toLocalDateTime().toLocalDate());
                    account.setEndDate(rs.getTimestamp("end_date").toLocalDateTime().toLocalDate());
                    return Optional.of(account);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta de metas", e);
        }
        return Optional.empty();
    }

    @Override
    public void update(GoalAccount account) {
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

            String goalSql =
                "UPDATE GoalAccounts " +
                    "SET target_value = ?, " +
                    "amount_value = ?, " +
                    "target_tax_rate = ?, " +
                    "monthly_tax_rate = ?, " +
                    "monthly_estimate = ?, " +
                    "monthly_achievement = ?, " +
                    "category_id = ?, " +
                    "target_date = ?, " +
                    "start_date = ?, " +
                    "end_date = ? " +
                    "WHERE account_id = ?";
            try (PreparedStatement goalStmt = connection.prepareStatement(goalSql)) {
                goalStmt.setBigDecimal(1, account.getTargetValue());
                goalStmt.setBigDecimal(2, account.getAmountValue());
                goalStmt.setBigDecimal(3, account.getTargetTaxRate());
                goalStmt.setBigDecimal(4, account.getMonthlyTaxRate());
                goalStmt.setBigDecimal(5, account.getMonthlyEstimate());
                goalStmt.setBigDecimal(6, account.getMonthlyAchievement());
                goalStmt.setString(7, account.getCategory().getId());
                goalStmt.setTimestamp(8, Timestamp.valueOf(account.getTargetDate().atStartOfDay()));
                goalStmt.setTimestamp(9, Timestamp.valueOf(account.getStartDate().atStartOfDay()));
                goalStmt.setTimestamp(10, Timestamp.valueOf(account.getEndDate().atStartOfDay()));
                goalStmt.setString(11, account.getAccountId());
                goalStmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar conta de metas", e);
        }
    }

    @Override
    public void delete(String accountId) {
        String sql = "DELETE FROM GoalAccounts WHERE account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            stmt.executeUpdate();

            String accountSql = "DELETE FROM Accounts WHERE account_id = ?";
            try (PreparedStatement accountStmt = connection.prepareStatement(accountSql)) {
                accountStmt.setString(1, accountId);
                accountStmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta de metas", e);
        }
    }

    @Override
    public List<GoalAccount> findAll() {
        List<GoalAccount> accounts = new ArrayList<>();
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN GoalAccounts s ON a.account_id = s.account_id";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GoalAccount account = new GoalAccount();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setDescription(rs.getString("description"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setIsActive(rs.getInt("is_active") == 1);
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                account.setTargetValue(rs.getBigDecimal("target_value"));
                account.setAmountValue(rs.getBigDecimal("amount_value"));
                account.setTargetTaxRate(rs.getBigDecimal("target_tax_rate"));
                account.setMonthlyTaxRate(rs.getBigDecimal("monthly_tax_rate"));
                account.setMonthlyEstimate(rs.getBigDecimal("monthly_estimate"));
                account.setMonthlyAchievement(rs.getBigDecimal("monthly_achievement"));
                account.getCategory().setId(rs.getString("category_id"));
                account.setTargetDate(rs.getTimestamp("target_date").toLocalDateTime().toLocalDate());
                account.setStartDate(rs.getTimestamp("start_date").toLocalDateTime().toLocalDate());
                account.setEndDate(rs.getTimestamp("end_date").toLocalDateTime().toLocalDate());
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas de metas", e);
        }
        return accounts;
    }

    @Override
    public List<GoalAccount> findAllActive() {
        List<GoalAccount> accounts = new ArrayList<>();
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN GoalAccounts s ON a.account_id = s.account_id " +
                "WHERE a.is_active = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GoalAccount account = new GoalAccount();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setDescription(rs.getString("description"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setIsActive(rs.getInt("is_active") == 1);
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                account.setTargetValue(rs.getBigDecimal("target_value"));
                account.setAmountValue(rs.getBigDecimal("amount_value"));
                account.setTargetTaxRate(rs.getBigDecimal("target_tax_rate"));
                account.setMonthlyTaxRate(rs.getBigDecimal("monthly_tax_rate"));
                account.setMonthlyEstimate(rs.getBigDecimal("monthly_estimate"));
                account.setMonthlyAchievement(rs.getBigDecimal("monthly_achievement"));
                account.getCategory().setId(rs.getString("category_id")); // observar
                account.setTargetDate(rs.getTimestamp("target_date").toLocalDateTime().toLocalDate());
                account.setStartDate(rs.getTimestamp("start_date").toLocalDateTime().toLocalDate());
                account.setEndDate(rs.getTimestamp("end_date").toLocalDateTime().toLocalDate());
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas de metas ativas", e);
        }
        return accounts;
    }

    @Override
    public List<GoalAccount> findAllInactive() {
        List<GoalAccount> accounts = new ArrayList<>();
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN GoalAccounts s ON a.account_id = s.account_id " +
                "WHERE a.is_active = 0";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GoalAccount account = new GoalAccount();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setDescription(rs.getString("description"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setIsActive(rs.getInt("is_active") == 1);
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                account.setTargetValue(rs.getBigDecimal("target_value"));
                account.setAmountValue(rs.getBigDecimal("amount_value"));
                account.setTargetTaxRate(rs.getBigDecimal("target_tax_rate"));
                account.setMonthlyTaxRate(rs.getBigDecimal("monthly_tax_rate"));
                account.setMonthlyEstimate(rs.getBigDecimal("monthly_estimate"));
                account.setMonthlyAchievement(rs.getBigDecimal("monthly_achievement"));
                account.getCategory().setId(rs.getString("category_id"));
                account.setTargetDate(rs.getTimestamp("target_date").toLocalDateTime().toLocalDate());
                account.setStartDate(rs.getTimestamp("start_date").toLocalDateTime().toLocalDate());
                account.setEndDate(rs.getTimestamp("end_date").toLocalDateTime().toLocalDate());
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas de metas inativas", e);
        }
        return accounts;
    }

}
