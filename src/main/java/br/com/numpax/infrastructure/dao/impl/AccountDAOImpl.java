package br.com.numpax.infrastructure.dao.impl;

import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.dao.AccountDAO;
import br.com.numpax.infrastructure.entities.*;
import br.com.numpax.application.enums.AccountType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDAOImpl implements AccountDAO {
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public void saveOrUpdate(Account account) {
        try (Connection conn = connectionManager.getConnection()) {
            conn.setAutoCommit(false);
            try {
                if (existsById(account.getId(), conn)) {
                    updateAccount(account, conn);
                } else {
                    saveAccount(account, conn);
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Erro na transação", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar/atualizar conta", e);
        }
    }

    private void saveAccount(Account account, Connection conn) throws SQLException {
        saveBaseAccount(account, conn);

        if (account instanceof CheckingAccount checkingAccount) {
            saveCheckingAccount(checkingAccount, conn);
        } else if (account instanceof SavingsAccount savingsAccount) {
            saveSavingsAccount(savingsAccount, conn);
        } else if (account instanceof InvestmentAccount investmentAccount) {
            saveInvestmentAccount(investmentAccount, conn);
        } else if (account instanceof GoalAccount goalAccount) {
            saveGoalAccount(goalAccount, conn);
        }
    }

    private void saveBaseAccount(Account account, Connection conn) throws SQLException {
        String sql = """
            INSERT INTO Accounts (
                account_id, name, description, balance, account_type,
                is_active, user_id, created_at, updated_at
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getId());
            stmt.setString(2, account.getName());
            stmt.setString(3, account.getDescription());
            stmt.setBigDecimal(4, account.getBalance());
            stmt.setString(5, account.getAccountType().name());
            stmt.setBoolean(6, account.isActive());
            stmt.setString(7, account.getUserId());
            stmt.setTimestamp(8, Timestamp.valueOf(account.getCreatedAt()));
            stmt.setTimestamp(9, Timestamp.valueOf(account.getUpdatedAt()));
            stmt.executeUpdate();
        }
    }

    private void saveCheckingAccount(CheckingAccount account, Connection conn) throws SQLException {
        String sql = """
            INSERT INTO CheckingAccounts (
                account_id, bank_name, agency, account_number
            ) VALUES (?, ?, ?, ?)
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getId());
            stmt.setString(2, account.getBankName());
            stmt.setString(3, account.getAgency());
            stmt.setString(4, account.getAccountNumber());
            stmt.executeUpdate();
        }
    }

    private void saveSavingsAccount(SavingsAccount account, Connection conn) throws SQLException {
        String sql = """
            INSERT INTO SavingsAccounts (
                account_id, nearest_deadline, furthest_deadline, latest_deadline,
                average_tax_rate, number_of_fixed_investments, total_maturity_amount,
                total_deposit_amount
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getId());
            stmt.setTimestamp(2, account.getNearestDeadline() != null ?
                Timestamp.valueOf(account.getNearestDeadline()) : null);
            stmt.setTimestamp(3, account.getFurthestDeadline() != null ?
                Timestamp.valueOf(account.getFurthestDeadline()) : null);
            stmt.setTimestamp(4, account.getLatestDeadline() != null ?
                Timestamp.valueOf(account.getLatestDeadline()) : null);
            stmt.setBigDecimal(5, account.getAverageTaxRate());
            stmt.setInt(6, account.getNumberOfFixedInvestments());
            stmt.setBigDecimal(7, account.getTotalMaturityAmount());
            stmt.setBigDecimal(8, account.getTotalDepositAmount());
            stmt.executeUpdate();
        }
    }

    private void saveInvestmentAccount(InvestmentAccount account, Connection conn) throws SQLException {
        String sql = """
            INSERT INTO InvestmentAccounts (
                account_id, broker, account_number, total_invested, profitability,
                current_yield, last_update, investment_types
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getId());
            stmt.setString(2, account.getBroker());
            stmt.setString(3, account.getAccountNumber());
            stmt.setBigDecimal(4, account.getTotalInvested());
            stmt.setBigDecimal(5, account.getProfitability());
            stmt.setBigDecimal(6, account.getCurrentYield());
            stmt.setTimestamp(7, Timestamp.valueOf(account.getLastUpdate()));
            stmt.setString(8, String.join(",", account.getInvestmentTypes()));
            stmt.executeUpdate();
        }
    }

    private void saveGoalAccount(GoalAccount account, Connection conn) throws SQLException {
        String sql = """
            INSERT INTO GoalAccounts (
                account_id, target_amount, target_date, monthly_contribution,
                progress_percentage, category, priority, is_completed
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getId());
            stmt.setBigDecimal(2, account.getTargetAmount());
            stmt.setTimestamp(3, account.getTargetDate() != null ?
                Timestamp.valueOf(account.getTargetDate()) : null);
            stmt.setBigDecimal(4, account.getMonthlyContribution());
            stmt.setDouble(5, account.getProgressPercentage());
            stmt.setString(6, account.getCategory());
            stmt.setString(7, account.getPriority());
            stmt.setBoolean(8, account.getIsCompleted());
            stmt.executeUpdate();
        }
    }

    private void updateAccount(Account account, Connection conn) throws SQLException {
        updateBaseAccount(account, conn);

        if (account instanceof CheckingAccount checkingAccount) {
            updateCheckingAccount(checkingAccount, conn);
        } else if (account instanceof SavingsAccount savingsAccount) {
            updateSavingsAccount(savingsAccount, conn);
        } else if (account instanceof InvestmentAccount investmentAccount) {
            updateInvestmentAccount(investmentAccount, conn);
        } else if (account instanceof GoalAccount goalAccount) {
            updateGoalAccount(goalAccount, conn);
        }
    }

    private void updateBaseAccount(Account account, Connection conn) throws SQLException {
        String sql = """
            UPDATE Accounts 
            SET name = ?, description = ?, balance = ?, account_type = ?, 
                is_active = ?, updated_at = ?
            WHERE account_id = ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getName());
            stmt.setString(2, account.getDescription());
            stmt.setBigDecimal(3, account.getBalance());
            stmt.setString(4, account.getAccountType().name());
            stmt.setBoolean(5, account.isActive());
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(7, account.getId());
            stmt.executeUpdate();
        }
    }

    private void updateCheckingAccount(CheckingAccount account, Connection conn) throws SQLException {
        String sql = """
            UPDATE CheckingAccounts 
            SET bank_name = ?, agency = ?, account_number = ?
            WHERE account_id = ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getBankName());
            stmt.setString(2, account.getAgency());
            stmt.setString(3, account.getAccountNumber());
            stmt.setString(4, account.getId());
            stmt.executeUpdate();
        }
    }

    private void updateSavingsAccount(SavingsAccount account, Connection conn) throws SQLException {
        String sql = """
            UPDATE SavingsAccounts 
            SET nearest_deadline = ?, furthest_deadline = ?, latest_deadline = ?,
                average_tax_rate = ?, number_of_fixed_investments = ?, 
                total_maturity_amount = ?, total_deposit_amount = ?
            WHERE account_id = ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, account.getNearestDeadline() != null ?
                Timestamp.valueOf(account.getNearestDeadline()) : null);
            stmt.setTimestamp(2, account.getFurthestDeadline() != null ?
                Timestamp.valueOf(account.getFurthestDeadline()) : null);
            stmt.setTimestamp(3, account.getLatestDeadline() != null ?
                Timestamp.valueOf(account.getLatestDeadline()) : null);
            stmt.setBigDecimal(4, account.getAverageTaxRate());
            stmt.setInt(5, account.getNumberOfFixedInvestments());
            stmt.setBigDecimal(6, account.getTotalMaturityAmount());
            stmt.setBigDecimal(7, account.getTotalDepositAmount());
            stmt.setString(8, account.getId());
            stmt.executeUpdate();
        }
    }

    private void updateInvestmentAccount(InvestmentAccount account, Connection conn) throws SQLException {
        String sql = """
            UPDATE InvestmentAccounts 
            SET broker = ?, account_number = ?, total_invested = ?, profitability = ?,
                current_yield = ?, last_update = ?, investment_types = ?
            WHERE account_id = ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getBroker());
            stmt.setString(2, account.getAccountNumber());
            stmt.setBigDecimal(3, account.getTotalInvested());
            stmt.setBigDecimal(4, account.getProfitability());
            stmt.setBigDecimal(5, account.getCurrentYield());
            stmt.setTimestamp(6, Timestamp.valueOf(account.getLastUpdate()));
            stmt.setString(7, String.join(",", account.getInvestmentTypes()));
            stmt.setString(8, account.getId());
            stmt.executeUpdate();
        }
    }

    private void updateGoalAccount(GoalAccount account, Connection conn) throws SQLException {
        String sql = """
            UPDATE GoalAccounts 
            SET target_amount = ?, target_date = ?, monthly_contribution = ?,
                progress_percentage = ?, category = ?, priority = ?, is_completed = ?
            WHERE account_id = ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, account.getTargetAmount());
            stmt.setTimestamp(2, account.getTargetDate() != null ?
                Timestamp.valueOf(account.getTargetDate()) : null);
            stmt.setBigDecimal(3, account.getMonthlyContribution());
            stmt.setDouble(4, account.getProgressPercentage());
            stmt.setString(5, account.getCategory());
            stmt.setString(6, account.getPriority());
            stmt.setBoolean(7, account.getIsCompleted());
            stmt.setString(8, account.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteById(String id) {
        try (Connection conn = connectionManager.getConnection()) {
            conn.setAutoCommit(false);
            try {
                Optional<Account> accountOpt = findById(id);
                if (accountOpt.isPresent()) {
                    Account account = accountOpt.get();

                    if (account instanceof CheckingAccount) {
                        deleteFromTable("CheckingAccounts", id, conn);
                    } else if (account instanceof SavingsAccount) {
                        deleteFromTable("SavingsAccounts", id, conn);
                    } else if (account instanceof InvestmentAccount) {
                        deleteFromTable("InvestmentAccounts", id, conn);
                    } else if (account instanceof GoalAccount) {
                        deleteFromTable("GoalAccounts", id, conn);
                    }

                    deleteFromTable("Accounts", id, conn);
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Erro ao deletar conta", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta", e);
        }
    }

    private void deleteFromTable(String tableName, String id, Connection conn) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE account_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Account> findById(String id) {
        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT * FROM Accounts WHERE account_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return Optional.of(mapResultSetToAccount(rs, conn));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta por ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Account> findByUserId(String userId) {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT * FROM Accounts WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, userId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    accounts.add(mapResultSetToAccount(rs, conn));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contas do usuário", e);
        }
        return accounts;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Accounts");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapResultSetToAccount(rs, conn));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas as contas", e);
        }
        return accounts;
    }

    @Override
    public void disableById(String id) {
        try (Connection conn = connectionManager.getConnection()) {
            conn.setAutoCommit(false);
            try {
                String sql = "UPDATE Accounts SET is_active = 0, updated_at = ? WHERE account_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                    stmt.setString(2, id);
                    stmt.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Erro ao desativar conta", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao desativar conta", e);
        }
    }

    private boolean existsById(String id, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM Accounts WHERE account_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    private Account mapResultSetToAccount(ResultSet rs, Connection conn) throws SQLException {
        AccountType accountType = AccountType.valueOf(rs.getString("account_type"));
        String accountId = rs.getString("account_id");
        Account baseAccount = null;

        switch (accountType) {
            case CHECKING:
                baseAccount = findCheckingAccountById(accountId, rs, conn);
                break;
            case SAVINGS:
                baseAccount = findSavingsAccountById(accountId, rs, conn);
                break;
            case INVESTMENT:
                baseAccount = findInvestmentAccountById(accountId, rs, conn);
                break;
            case GOAL:
                baseAccount = findGoalAccountById(accountId, rs, conn);
                break;
            default:
                throw new IllegalArgumentException("Tipo de conta não suportado: " + accountType);
        }

        return baseAccount;
    }

    private CheckingAccount findCheckingAccountById(String id, ResultSet baseRs, Connection conn) throws SQLException {
        String sql = "SELECT * FROM CheckingAccounts WHERE account_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CheckingAccount(
                    baseRs.getString("account_id"),
                    baseRs.getString("name"),
                    baseRs.getString("description"),
                    baseRs.getBigDecimal("balance"),
                    baseRs.getString("user_id"),
                    baseRs.getBoolean("is_active"),
                    baseRs.getTimestamp("created_at").toLocalDateTime(),
                    baseRs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getString("bank_name"),
                    rs.getString("agency"),
                    rs.getString("account_number")
                );
            }
        }
        return null;
    }

    private SavingsAccount findSavingsAccountById(String id, ResultSet baseRs, Connection conn) throws SQLException {
        String sql = "SELECT * FROM SavingsAccounts WHERE account_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new SavingsAccount(
                    baseRs.getString("account_id"),
                    baseRs.getString("name"),
                    baseRs.getString("description"),
                    baseRs.getBigDecimal("balance"),
                    baseRs.getString("user_id"),
                    baseRs.getBoolean("is_active"),
                    baseRs.getTimestamp("created_at").toLocalDateTime(),
                    baseRs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getTimestamp("nearest_deadline") != null ? rs.getTimestamp("nearest_deadline").toLocalDateTime() : null,
                    rs.getTimestamp("furthest_deadline") != null ? rs.getTimestamp("furthest_deadline").toLocalDateTime() : null,
                    rs.getTimestamp("latest_deadline") != null ? rs.getTimestamp("latest_deadline").toLocalDateTime() : null,
                    rs.getBigDecimal("average_tax_rate"),
                    rs.getInt("number_of_fixed_investments"),
                    rs.getBigDecimal("total_maturity_amount"),
                    rs.getBigDecimal("total_deposit_amount")
                );
            }
        }
        return null;
    }

    private InvestmentAccount findInvestmentAccountById(String id, ResultSet baseRs, Connection conn) throws SQLException {
        String sql = "SELECT * FROM InvestmentAccounts WHERE account_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new InvestmentAccount(
                    baseRs.getString("account_id"),
                    baseRs.getString("name"),
                    baseRs.getString("description"),
                    baseRs.getBigDecimal("balance"),
                    baseRs.getString("user_id"),
                    baseRs.getBoolean("is_active"),
                    baseRs.getTimestamp("created_at").toLocalDateTime(),
                    baseRs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getString("broker"),
                    rs.getString("account_number"),
                    rs.getBigDecimal("total_invested"),
                    rs.getBigDecimal("profitability"),
                    rs.getBigDecimal("current_yield"),
                    rs.getTimestamp("last_update") != null ? rs.getTimestamp("last_update").toLocalDateTime() : null,
                    List.of(rs.getString("investment_types").split(","))
                );
            }
        }
        return null;
    }

    private GoalAccount findGoalAccountById(String id, ResultSet baseRs, Connection conn) throws SQLException {
        String sql = "SELECT * FROM GoalAccounts WHERE account_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new GoalAccount(
                    baseRs.getString("account_id"),
                    baseRs.getString("name"),
                    baseRs.getString("description"),
                    baseRs.getBigDecimal("balance"),
                    baseRs.getString("user_id"),
                    baseRs.getBoolean("is_active"),
                    baseRs.getTimestamp("created_at").toLocalDateTime(),
                    baseRs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getBigDecimal("target_amount"),
                    rs.getTimestamp("target_date") != null ? rs.getTimestamp("target_date").toLocalDateTime() : null,
                    rs.getBigDecimal("monthly_contribution"),
                    rs.getDouble("progress_percentage"),
                    rs.getString("category"),
                    rs.getString("priority"),
                    rs.getBoolean("is_completed")
                );
            }
        }
        return null;
    }

    @Override
    public List<Account> findAllActive() {
        return findAccountsByStatus(true);
    }

    @Override
    public List<Account> findAllInactive() {
        return findAccountsByStatus(false);
    }

    private List<Account> findAccountsByStatus(boolean active) {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT * FROM Accounts WHERE is_active = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBoolean(1, active);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    accounts.add(mapResultSetToAccount(rs, conn));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contas", e);
        }
        return accounts;
    }
}
