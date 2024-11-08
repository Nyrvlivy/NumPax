package br.com.numpax.infrastructure.repositories.impl;

import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.enums.InvestmentSubtype;
import br.com.numpax.application.enums.RiskLevelType;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.repositories.InvestmentAccountRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InvestmentAccountRepositoryImpl implements InvestmentAccountRepository {

    private final Connection connection;

    public InvestmentAccountRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(InvestmentAccount account) {
        String sql =
            "INSERT INTO InvestmentAccounts " +
                "(account_id, " +
                "total_invested_amount, " +
                "total_profit, " +
                "total_current_amount, " +
                "total_withdrawn_amount, " +
                "number_of_withdrawals, " +
                "number_of_entries, " +
                "number_of_assets, " +
                "average_purchase_price, " +
                "total_gain_loss, " +
                "total_dividend_yield, " +
                "risk_level_type, " +
                "investment_subtype) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getAccountId());
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
            stmt.setString(12, account.getRiskLevelType().toString());
            stmt.setString(13, account.getInvestmentSubtype().toString());
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
            throw new RuntimeException("Erro ao criar conta de investimento", e);
        }
    }

    @Override
    public Optional<InvestmentAccount> findById(String accountId) {
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN InvestmentAccounts i ON i.account_id = a.account_id " +
                "WHERE a.account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    InvestmentAccount account = new InvestmentAccount();
                    account.setAccountId(rs.getString("account_id"));
                    account.setName(rs.getString("name"));
                    account.setDescription(rs.getString("description"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                    account.setIsActive(rs.getInt("is_active") == 1);
                    account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
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
                    account.setRiskLevelType(RiskLevelType.valueOf(rs.getString("risk_level_type")));
                    account.setInvestmentSubtype(InvestmentSubtype.valueOf(rs.getString("investment_subtype")));
                    return Optional.of(account);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta de investimento", e);
        }
        return Optional.empty();

    }

    @Override
    public void update(InvestmentAccount account) {
        String sql =
            "UPDATE Accounts " +
                "SET name = ?, " +
                "description = ?, " +
                "updated_at = ?, " +
                "is_active = ?" +
                " WHERE account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getName());
            stmt.setString(2, account.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(4, account.isActive() ? 1 : 0);
            stmt.setString(5, account.getAccountId());
            stmt.executeUpdate();

            String investmentSql =
                "UPDATE InvestmentAccounts " +
                    "SET total_invested_amount = ?, " +
                    "total_profit = ?, " +
                    "total_current_amount = ?, " +
                    "total_withdrawn_amount = ?, " +
                    "number_of_withdrawals = ?, " +
                    "number_of_entries = ?, " +
                    "number_of_assets = ?, " +
                    "average_purchase_price = ?, " +
                    "total_gain_loss = ?, " +
                    "total_dividend_yield = ?, " +
                    "risk_level_type = ?, " +
                    "investment_subtype = ? " +
                    "WHERE account_id = ?";
            try (PreparedStatement investmentStmt = connection.prepareStatement(investmentSql)) {
                investmentStmt.setBigDecimal(1, account.getTotalInvestedAmount());
                investmentStmt.setBigDecimal(2, account.getTotalProfit());
                investmentStmt.setBigDecimal(3, account.getTotalCurrentAmount());
                investmentStmt.setBigDecimal(4, account.getTotalWithdrawnAmount());
                investmentStmt.setInt(5, account.getNumberOfWithdrawals());
                investmentStmt.setInt(6, account.getNumberOfEntries());
                investmentStmt.setInt(7, account.getNumberOfAssets());
                investmentStmt.setBigDecimal(8, account.getAveragePurchasePrice());
                investmentStmt.setBigDecimal(9, account.getTotalGainLoss());
                investmentStmt.setBigDecimal(10, account.getTotalDividendYield());
                investmentStmt.setString(11, account.getRiskLevelType().toString());
                investmentStmt.setString(12, account.getInvestmentSubtype().toString());
                investmentStmt.setString(13, account.getAccountId());
                investmentStmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar conta de investimento", e);
        }
    }

    @Override
    public void delete(String accountId) {
        String sql = "DELETE FROM InvestmentAccounts WHERE account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            stmt.executeUpdate();

            String accountSql = "DELETE FROM Accounts WHERE account_id = ?";
            try (PreparedStatement accountStmt = connection.prepareStatement(accountSql)) {
                accountStmt.setString(1, accountId);
                accountStmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta de investimento", e);
        }
    }

    @Override
    public List<InvestmentAccount> findAll() {
        List<InvestmentAccount> accounts = new ArrayList<>();
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN InvestmentAccounts i ON i.account_id = a.account_id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                InvestmentAccount account = new InvestmentAccount();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setDescription(rs.getString("description"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setIsActive(rs.getInt("is_active") == 1);
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
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
                account.setRiskLevelType(RiskLevelType.valueOf(rs.getString("risk_level_type")));
                account.setInvestmentSubtype(InvestmentSubtype.valueOf(rs.getString("investment_subtype")));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contas de investimento", e);
        }
        return accounts;
    }

    @Override
    public List<InvestmentAccount> findAllActive() {
        List<InvestmentAccount> accounts = new ArrayList<>();
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN InvestmentAccounts i ON i.account_id = a.account_id " +
                "WHERE a.is_active = 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                InvestmentAccount account = new InvestmentAccount();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setDescription(rs.getString("description"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setIsActive(rs.getInt("is_active") == 1);
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
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
                account.setRiskLevelType(RiskLevelType.valueOf(rs.getString("risk_level_type")));
                account.setInvestmentSubtype(InvestmentSubtype.valueOf(rs.getString("investment_subtype")));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contas de investimento ativas", e);
        }
        return accounts;
    }

    @Override
    public List<InvestmentAccount> findAllInactive() {
        List<InvestmentAccount> accounts = new ArrayList<>();
        String sql =
            "SELECT * " +
                "FROM Accounts a " +
                "JOIN InvestmentAccounts i ON i.account_id = a.account_id " +
                "WHERE a.is_active = 0";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                InvestmentAccount account = new InvestmentAccount();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setDescription(rs.getString("description"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setIsActive(rs.getInt("is_active") == 1);
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
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
                account.setRiskLevelType(RiskLevelType.valueOf(rs.getString("risk_level_type")));
                account.setInvestmentSubtype(InvestmentSubtype.valueOf(rs.getString("investment_subtype")));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contas de investimento inativas", e);
        }
        return accounts;
    }

}
