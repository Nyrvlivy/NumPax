package br.com.numpax.infrastructure.repositories.impl;

import br.com.numpax.application.enums.FixedInvestmentType;
import br.com.numpax.infrastructure.entities.FixedInvestment;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.repositories.FixedInvestmentRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

public class FixedInvestmentRepositoryImpl implements FixedInvestmentRepository {

    private final Connection connection;

    public FixedInvestmentRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(FixedInvestment investment) {
        // Primeiro insere na tabela Transactions
        String transactionSql = "INSERT INTO Transactions (transaction_id, code, name, description, amount, " +
                               "category_id, account_id, nature_of_transaction, receiver, sender, " +
                               "transaction_date, note, created_at, updated_at) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Depois insere na tabela FixedInvestments
        String investmentSql = "INSERT INTO FixedInvestments (transaction_id, fixed_investment_type, " +
                              "investment_date, expiration_date, institution, redeem_value, redeem_date, " +
                              "liquidity_period, net_gain_loss) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Insert Transaction
            try (PreparedStatement stmt = connection.prepareStatement(transactionSql)) {
                stmt.setString(1, investment.getTransactionId());
                stmt.setString(2, investment.getCode());
                stmt.setString(3, investment.getName());
                stmt.setString(4, investment.getDescription());
                stmt.setBigDecimal(5, investment.getAmount());
                stmt.setString(6, investment.getCategoryId());
                stmt.setString(7, investment.getAccountId());
                stmt.setString(8, investment.getNatureOfTransaction().name());
                stmt.setString(9, investment.getReceiver());
                stmt.setString(10, investment.getSender());
                stmt.setDate(11, Date.valueOf(investment.getTransactionDate()));
                stmt.setString(12, investment.getNote());
                stmt.setTimestamp(13, Timestamp.valueOf(investment.getCreatedAt()));
                stmt.setTimestamp(14, Timestamp.valueOf(investment.getUpdatedAt()));
                stmt.executeUpdate();
            }

            // Insert FixedInvestment
            try (PreparedStatement stmt = connection.prepareStatement(investmentSql)) {
                stmt.setString(1, investment.getTransactionId());
                stmt.setString(2, investment.getFixedInvestmentType().name());
                stmt.setDate(3, Date.valueOf(investment.getInvestmentDate()));
                stmt.setDate(4, investment.getExpirationDate() != null ? 
                    Date.valueOf(investment.getExpirationDate()) : null);
                stmt.setString(5, investment.getInstitution());
                stmt.setBigDecimal(6, investment.getRedeemValue());
                stmt.setDate(7, investment.getRedeemDate() != null ? 
                    Date.valueOf(investment.getRedeemDate()) : null);
                stmt.setInt(8, investment.getLiquidityPeriod());
                stmt.setBigDecimal(9, investment.getNetGainLoss());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating fixed investment", e);
        }
    }

    @Override
    public Optional<FixedInvestment> findById(String investmentId) {
        String sql = "SELECT fi.*, ia.* FROM FixedInvestments fi " +
                    "JOIN InvestmentAccounts ia ON fi.investment_account_id = ia.account_id " +
                    "WHERE fi.fixed_investment_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, investmentId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractFixedInvestmentFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding fixed investment", e);
        }
        return Optional.empty();
    }

    @Override
    public void update(FixedInvestment investment) {
        String transactionSql = "UPDATE Transactions SET code = ?, name = ?, description = ?, " +
                               "amount = ?, category_id = ?, account_id = ?, nature_of_transaction = ?, " +
                               "receiver = ?, sender = ?, transaction_date = ?, note = ?, updated_at = ? " +
                               "WHERE transaction_id = ?";

        String investmentSql = "UPDATE FixedInvestments SET fixed_investment_type = ?, investment_date = ?, " +
                              "expiration_date = ?, institution = ?, redeem_value = ?, redeem_date = ?, " +
                              "liquidity_period = ?, net_gain_loss = ? WHERE transaction_id = ?";

        try {
            // Update Transaction
            try (PreparedStatement stmt = connection.prepareStatement(transactionSql)) {
                stmt.setString(1, investment.getCode());
                stmt.setString(2, investment.getName());
                stmt.setString(3, investment.getDescription());
                stmt.setBigDecimal(4, investment.getAmount());
                stmt.setString(5, investment.getCategoryId());
                stmt.setString(6, investment.getAccountId());
                stmt.setString(7, investment.getNatureOfTransaction().name());
                stmt.setString(8, investment.getReceiver());
                stmt.setString(9, investment.getSender());
                stmt.setDate(10, Date.valueOf(investment.getTransactionDate()));
                stmt.setString(11, investment.getNote());
                stmt.setTimestamp(12, Timestamp.valueOf(investment.getUpdatedAt()));
                stmt.setString(13, investment.getTransactionId());
                stmt.executeUpdate();
            }

            // Update FixedInvestment
            try (PreparedStatement stmt = connection.prepareStatement(investmentSql)) {
                stmt.setString(1, investment.getFixedInvestmentType().name());
                stmt.setDate(2, Date.valueOf(investment.getInvestmentDate()));
                stmt.setDate(3, investment.getExpirationDate() != null ? 
                    Date.valueOf(investment.getExpirationDate()) : null);
                stmt.setString(4, investment.getInstitution());
                stmt.setBigDecimal(5, investment.getRedeemValue());
                stmt.setDate(6, investment.getRedeemDate() != null ? 
                    Date.valueOf(investment.getRedeemDate()) : null);
                stmt.setInt(7, investment.getLiquidityPeriod());
                stmt.setBigDecimal(8, investment.getNetGainLoss());
                stmt.setString(9, investment.getTransactionId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating fixed investment", e);
        }
    }

    @Override
    public void delete(String investmentId) {
        String sql = "DELETE FROM FixedInvestments WHERE fixed_investment_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, investmentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting fixed investment", e);
        }
    }

    @Override
    public List<FixedInvestment> findAll() {
        String sql = "SELECT fi.*, ia.* FROM FixedInvestments fi " +
                    "JOIN InvestmentAccounts ia ON fi.investment_account_id = ia.account_id";
        return executeQuery(sql);
    }

    @Override
    public List<FixedInvestment> findAllActive() {
        String sql = "SELECT fi.*, ia.* FROM FixedInvestments fi " +
                    "JOIN InvestmentAccounts ia ON fi.investment_account_id = ia.account_id " +
                    "WHERE fi.is_active = 1";
        return executeQuery(sql);
    }

    @Override
    public List<FixedInvestment> findAllInactive() {
        String sql = "SELECT fi.*, ia.* FROM FixedInvestments fi " +
                    "JOIN InvestmentAccounts ia ON fi.investment_account_id = ia.account_id " +
                    "WHERE fi.is_active = 0";
        return executeQuery(sql);
    }

    @Override
    public List<FixedInvestment> findByInvestmentAccountId(String accountId) {
        String sql = "SELECT fi.*, ia.* FROM FixedInvestments fi " +
                    "JOIN InvestmentAccounts ia ON fi.investment_account_id = ia.account_id " +
                    "WHERE fi.investment_account_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            return extractFixedInvestmentsFromResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException("Error finding fixed investments by account", e);
        }
    }

    @Override
    public List<FixedInvestment> findByMaturityDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT fi.*, ia.* FROM FixedInvestments fi " +
                    "JOIN InvestmentAccounts ia ON fi.investment_account_id = ia.account_id " +
                    "WHERE fi.maturity_date BETWEEN ? AND ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            return extractFixedInvestmentsFromResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException("Error finding fixed investments by maturity date range", e);
        }
    }

    @Override
    public List<FixedInvestment> findRedeemedInvestments() {
        String sql = "SELECT fi.*, ia.* FROM FixedInvestments fi " +
                    "JOIN InvestmentAccounts ia ON fi.investment_account_id = ia.account_id " +
                    "WHERE fi.is_redeemed = 1";
        return executeQuery(sql);
    }

    @Override
    public List<FixedInvestment> findActiveInvestments() {
        String sql = "SELECT fi.*, t.* FROM FixedInvestments fi " +
                    "JOIN Transactions t ON fi.transaction_id = t.transaction_id " +
                    "WHERE fi.redeem_date IS NULL " +
                    "AND (fi.expiration_date IS NULL OR fi.expiration_date > CURRENT_DATE)";
        return executeQuery(sql);
    }

    @Override
    public List<FixedInvestment> findMaturedInvestments() {
        String sql = "SELECT fi.*, t.* FROM FixedInvestments fi " +
                    "JOIN Transactions t ON fi.transaction_id = t.transaction_id " +
                    "WHERE fi.redeem_date IS NULL " +
                    "AND fi.expiration_date <= CURRENT_DATE";
        return executeQuery(sql);
    }

    @Override
    public void redeemInvestment(String investmentId, LocalDate redemptionDate, double redemptionAmount) {
        String sql = "UPDATE FixedInvestments SET is_redeemed = 1, redemption_date = ?, " +
                    "redemption_amount = ?, updated_at = ? WHERE fixed_investment_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(redemptionDate));
            stmt.setBigDecimal(2, BigDecimal.valueOf(redemptionAmount));
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(4, investmentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error redeeming fixed investment", e);
        }
    }

    private List<FixedInvestment> executeQuery(String sql) {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return extractFixedInvestmentsFromResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query", e);
        }
    }

    private List<FixedInvestment> extractFixedInvestmentsFromResultSet(ResultSet rs) throws SQLException {
        List<FixedInvestment> investments = new ArrayList<>();
        while (rs.next()) {
            investments.add(extractFixedInvestmentFromResultSet(rs));
        }
        return investments;
    }

    private FixedInvestment extractFixedInvestmentFromResultSet(ResultSet rs) throws SQLException {
        FixedInvestment investment = new FixedInvestment();
        
        // Transaction fields
        investment.setTransactionId(rs.getString("transaction_id"));
        investment.setCode(rs.getString("code"));
        investment.setName(rs.getString("name"));
        investment.setDescription(rs.getString("description"));
        investment.setAmount(rs.getBigDecimal("amount"));
        investment.setCategoryId(rs.getString("category_id"));
        investment.setAccountId(rs.getString("account_id"));
        investment.setNatureOfTransaction(NatureOfTransaction.valueOf(rs.getString("nature_of_transaction")));
        investment.setReceiver(rs.getString("receiver"));
        investment.setSender(rs.getString("sender"));
        investment.setTransactionDate(rs.getDate("transaction_date").toLocalDate());
        investment.setNote(rs.getString("note"));
        investment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        investment.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        
        // FixedInvestment fields
        investment.setFixedInvestmentType(FixedInvestmentType.valueOf(rs.getString("fixed_investment_type")));
        investment.setInvestmentDate(rs.getDate("investment_date").toLocalDate());
        investment.setExpirationDate(rs.getDate("expiration_date") != null ? 
            rs.getDate("expiration_date").toLocalDate() : null);
        investment.setInstitution(rs.getString("institution"));
        investment.setRedeemValue(rs.getBigDecimal("redeem_value"));
        investment.setRedeemDate(rs.getDate("redeem_date") != null ? 
            rs.getDate("redeem_date").toLocalDate() : null);
        investment.setLiquidityPeriod(rs.getInt("liquidity_period"));
        investment.setNetGainLoss(rs.getBigDecimal("net_gain_loss"));
        
        return investment;
    }
} 