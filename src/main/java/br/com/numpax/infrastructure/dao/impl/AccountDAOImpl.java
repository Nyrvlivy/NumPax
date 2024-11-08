package br.com.numpax.infrastructure.dao.impl;

import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.dao.AccountDAO;
import br.com.numpax.infrastructure.entities.*;
import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.enums.InvestmentSubtype;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

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

        switch (account) {
            case CheckingAccount checkingAccount -> saveCheckingAccount(checkingAccount, conn);
            case SavingsAccount savingsAccount -> saveSavingsAccount(savingsAccount, conn);
            case InvestmentAccount investmentAccount -> saveInvestmentAccount(investmentAccount, conn);
            default -> {
            }
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
            stmt.setInt(6, account.isActive() ? 1 : 0);
            stmt.setString(7, account.getUserId());
            stmt.setTimestamp(8, Timestamp.valueOf(account.getCreatedAt()));
            stmt.setTimestamp(9, Timestamp.valueOf(account.getUpdatedAt()));
            stmt.executeUpdate();
        }
    }

    private void saveCheckingAccount(CheckingAccount account, Connection conn) throws SQLException {
        String sql = """
            INSERT INTO CheckingAccounts (
                account_id, bank_code, agency, account_number
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
            stmt.setBigDecimal(6, account.getNumberOfFixedInvestments());
            stmt.setBigDecimal(7, account.getTotalMaturityAmount());
            stmt.setBigDecimal(8, account.getTotalDepositAmount());
            stmt.executeUpdate();
        }
    }

    private void saveInvestmentAccount(InvestmentAccount account, Connection conn) throws SQLException {
        String sql = """
            INSERT INTO InvestmentAccounts (
                account_id, investment_subtype, total_invested_amount, total_profit,
                total_current_amount, total_withdrawn_amount, number_of_withdrawals,
                number_of_entries, number_of_assets, average_purchase_price,
                total_gain_loss, total_dividend_yield, risk_level_type
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getId());
            stmt.setString(2, account.getInvestmentSubtype().name());
            stmt.setBigDecimal(3, account.getTotalInvestedAmount());
            stmt.setBigDecimal(4, account.getTotalProfit());
            stmt.setBigDecimal(5, account.getTotalCurrentAmount());
            stmt.setBigDecimal(6, account.getTotalWithdrawnAmount());
            stmt.setInt(7, account.getNumberOfWithdrawals());
            stmt.setInt(8, account.getNumberOfEntries());
            stmt.setInt(9, account.getNumberOfAssets());
            stmt.setBigDecimal(10, account.getAveragePurchasePrice());
            stmt.setBigDecimal(11, account.getTotalGainLoss());
            stmt.setBigDecimal(12, account.getTotalDividendYield());
            stmt.setString(13, account.getRiskLevelType());
            stmt.executeUpdate();
        }
    }

    private void updateAccount(Account account, Connection conn) throws SQLException {
        updateBaseAccount(account, conn);

        switch (account) {
            case CheckingAccount checkingAccount -> updateCheckingAccount(checkingAccount, conn);
            case SavingsAccount savingsAccount -> updateSavingsAccount(savingsAccount, conn);
            case InvestmentAccount investmentAccount -> updateInvestmentAccount(investmentAccount, conn);
            default -> {
            }
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
            stmt.setInt(5, account.isActive() ? 1 : 0);
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(7, account.getId());
            stmt.executeUpdate();
        }
    }

    private void updateCheckingAccount(CheckingAccount account, Connection conn) throws SQLException {
        String sql = """
            UPDATE CheckingAccounts 
            SET bank_code = ?, agency = ?, account_number = ?
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
            stmt.setBigDecimal(5, account.getNumberOfFixedInvestments());
            stmt.setBigDecimal(6, account.getTotalMaturityAmount());
            stmt.setBigDecimal(7, account.getTotalDepositAmount());
            stmt.setString(8, account.getId());
            stmt.executeUpdate();
        }
    }

    private void updateInvestmentAccount(InvestmentAccount account, Connection conn) throws SQLException {
        String sql = """
            UPDATE InvestmentAccounts 
            SET investment_subtype = ?, total_invested_amount = ?, total_profit = ?,
                total_current_amount = ?, total_withdrawn_amount = ?, 
                number_of_withdrawals = ?, number_of_entries = ?, number_of_assets = ?,
                average_purchase_price = ?, total_gain_loss = ?, 
                total_dividend_yield = ?, risk_level_type = ?
            WHERE account_id = ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getInvestmentSubtype().name());
            stmt.setBigDecimal(2, account.getTotalInvestedAmount());
            stmt.setBigDecimal(3, account.getTotalProfit());
            stmt.setBigDecimal(4, account.getTotalCurrentAmount());
            stmt.setBigDecimal(5, account.getTotalWithdrawnAmount());
            stmt.setInt(6, account.getNumberOfWithdrawals());
            stmt.setInt(7, account.getNumberOfEntries());
            stmt.setInt(8, account.getNumberOfAssets());
            stmt.setBigDecimal(9, account.getAveragePurchasePrice());
            stmt.setBigDecimal(10, account.getTotalGainLoss());
            stmt.setBigDecimal(11, account.getTotalDividendYield());
            stmt.setString(12, account.getRiskLevelType());
            stmt.setString(13, account.getId());
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

                    switch (account) {
                        case CheckingAccount checkingAccount -> deleteFromTable("CheckingAccounts", id, conn);
                        case SavingsAccount savingsAccount -> deleteFromTable("SavingsAccounts", id, conn);
                        case InvestmentAccount investmentAccount -> deleteFromTable("InvestmentAccounts", id, conn);
                        default -> {
                        }
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
                    Account baseAccount = mapResultSetToAccount(rs);
                    
                    Account specificAccount = switch (baseAccount.getAccountType()) {
                        case CHECKING -> findCheckingAccountById(id, baseAccount, conn);
                        case SAVINGS -> findSavingsAccountById(id, baseAccount, conn);
                        case INVESTMENT -> findInvestmentAccountById(id, baseAccount, conn);
                        default -> null;
                    };
                    return Optional.of(specificAccount != null ? specificAccount : baseAccount);
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
                    Account baseAccount = mapResultSetToAccount(rs);
                    Account specificAccount = switch (baseAccount.getAccountType()) {
                        case CHECKING -> findCheckingAccountById(baseAccount.getId(), baseAccount, conn);
                        case SAVINGS -> findSavingsAccountById(baseAccount.getId(), baseAccount, conn);
                        case INVESTMENT -> findInvestmentAccountById(baseAccount.getId(), baseAccount, conn);
                        default -> null;
                    };

                    accounts.add(specificAccount != null ? specificAccount : baseAccount);
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
                Account baseAccount = mapResultSetToAccount(rs);
                Account specificAccount = switch (baseAccount.getAccountType()) {
                    case CHECKING -> findCheckingAccountById(baseAccount.getId(), baseAccount, conn);
                    case SAVINGS -> findSavingsAccountById(baseAccount.getId(), baseAccount, conn);
                    case INVESTMENT -> findInvestmentAccountById(baseAccount.getId(), baseAccount, conn);
                    default -> null;
                };

                accounts.add(specificAccount != null ? specificAccount : baseAccount);
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

    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        AccountType accountType = AccountType.valueOf(rs.getString("account_type"));
        Account account = switch (accountType) {
            case CHECKING -> new CheckingAccount("", "", accountType, "", "", "", "");
            case SAVINGS -> new SavingsAccount("", "", "", null, null, null,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            case INVESTMENT -> new InvestmentAccount("", "", "", InvestmentSubtype.OTHER);
            default -> throw new IllegalArgumentException("Tipo de conta não suportado: " + accountType);
        };

        account.setId(rs.getString("account_id"));
        account.setName(rs.getString("name"));
        account.setDescription(rs.getString("description"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setActive(rs.getInt("is_active") == 1);
        account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        account.setUserId(rs.getString("user_id"));
        
        return account;
    }

    private CheckingAccount mapResultSetToCheckingAccount(ResultSet rs, Account baseAccount) throws SQLException {
        return new CheckingAccount(
            baseAccount.getName(),
            baseAccount.getDescription(),
            baseAccount.getAccountType(),
            baseAccount.getUserId(),
            rs.getString("bank_code"),
            rs.getString("agency"),
            rs.getString("account_number")
        );
    }

    private SavingsAccount mapResultSetToSavingsAccount(ResultSet rs, Account baseAccount) throws SQLException {
        return new SavingsAccount(
            baseAccount.getName(),
            baseAccount.getDescription(),
            baseAccount.getUserId(),
            rs.getTimestamp("nearest_deadline") != null ?
                rs.getTimestamp("nearest_deadline").toLocalDateTime() : null,
            rs.getTimestamp("furthest_deadline") != null ?
                rs.getTimestamp("furthest_deadline").toLocalDateTime() : null,
            rs.getTimestamp("latest_deadline") != null ?
                rs.getTimestamp("latest_deadline").toLocalDateTime() : null,
            rs.getBigDecimal("average_tax_rate"),
            rs.getBigDecimal("number_of_fixed_investments"),
            rs.getBigDecimal("total_maturity_amount"),
            rs.getBigDecimal("total_deposit_amount")
        );
    }

    private InvestmentAccount mapResultSetToInvestmentAccount(ResultSet rs, Account baseAccount) throws SQLException {
        InvestmentAccount account = new InvestmentAccount(
            baseAccount.getName(),
            baseAccount.getDescription(),
            baseAccount.getUserId(),
            InvestmentSubtype.valueOf(rs.getString("investment_subtype"))
        );
        account.setTotalInvestedAmount(rs.getBigDecimal("total_invested_amount"));
        account.setTotalProfit(rs.getBigDecimal("total_profit"));
        account.setTotalCurrentAmount(rs.getBigDecimal("total_current_amount"));
        account.setTotalWithdrawnAmount(rs.getBigDecimal("total_withdrawn_amount"));
        account.setNumberOfWithdrawals(rs.getInt("number_of_withdrawals"));
        account.setNumberOfEntries(rs.getInt("number_of_entries"));
        account.setNumberOfAssets(rs.getInt("number_of_assets"));
        account.setAveragePurchasePrice(rs.getBigDecimal("average_purchase_price"));
        account.setTotalGainLoss(rs.getBigDecimal("total_gain_loss"));
        account.setTotalDividendYield(rs.getBigDecimal("total_dividend_yield"));
        account.setRiskLevelType(rs.getString("risk_level_type"));
        return account;
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
                stmt.setInt(1, active ? 1 : 0);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Account baseAccount = mapResultSetToAccount(rs);
                    Account specificAccount = switch (baseAccount.getAccountType()) {
                        case CHECKING -> findCheckingAccountById(baseAccount.getId(), baseAccount, conn);
                        case SAVINGS -> findSavingsAccountById(baseAccount.getId(), baseAccount, conn);
                        case INVESTMENT -> findInvestmentAccountById(baseAccount.getId(), baseAccount, conn);
                        default -> null;
                    };

                    accounts.add(specificAccount != null ? specificAccount : baseAccount);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contas inativas", e);
        }
        return accounts;
    }

    private Account findCheckingAccountById(String id, Account baseAccount, Connection conn) throws SQLException {
        String sql = "SELECT * FROM CheckingAccounts WHERE account_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToCheckingAccount(rs, baseAccount);
            }
        }
        return null;
    }

    private Account findSavingsAccountById(String id, Account baseAccount, Connection conn) throws SQLException {
        String sql = "SELECT * FROM SavingsAccounts WHERE account_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToSavingsAccount(rs, baseAccount);
            }
        }
        return null;
    }

    private Account findInvestmentAccountById(String id, Account baseAccount, Connection conn) throws SQLException {
        String sql = "SELECT * FROM InvestmentAccounts WHERE account_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToInvestmentAccount(rs, baseAccount);
            }
        }
        return null;
    }

    @Override
    public List<Account> findAllActive() {
        return findAccountsByStatus(true);
    }

    private Account mapResultSetToBaseAccount(ResultSet rs) throws SQLException {
        AccountType accountType = AccountType.valueOf(rs.getString("account_type"));
        Account account = switch (accountType) {
            case CHECKING -> new CheckingAccount("", "", accountType, "", "", "", "");
            case SAVINGS -> new SavingsAccount("", "", "", null, null, null,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            case INVESTMENT -> new InvestmentAccount("", "", "", InvestmentSubtype.OTHER);
            default -> throw new IllegalArgumentException("Tipo de conta não suportado: " + accountType);
        };

        account.setBalance(rs.getBigDecimal("balance"));
        account.setActive(rs.getInt("is_active") == 1);
        account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        account.setUserId(rs.getString("user_id"));
        
        return account;
    }
}
