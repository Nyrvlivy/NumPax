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
        String sql = "INSERT INTO FixedInvestments (fixed_investment_id, name, description, investment_type, " +
                    "investment_account_id, investment_amount, tax_rate, investment_date, maturity_date, " +
                    "expected_return, current_amount, profit_amount, broker, institution, note, " +
                    "is_redeemed, redemption_date, redemption_amount, is_active, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, investment.getFixedInvestmentId());
            stmt.setString(2, investment.getName());
            stmt.setString(3, investment.getDescription());
            stmt.setString(4, investment.getInvestmentType().name());
            stmt.setString(5, investment.getInvestmentAccount().getAccountId());
            stmt.setBigDecimal(6, investment.getInvestmentAmount());
            stmt.setBigDecimal(7, investment.getTaxRate());
            stmt.setDate(8, Date.valueOf(investment.getInvestmentDate()));
            stmt.setDate(9, Date.valueOf(investment.getMaturityDate()));
            stmt.setBigDecimal(10, investment.getExpectedReturn());
            stmt.setBigDecimal(11, investment.getCurrentAmount());
            stmt.setBigDecimal(12, investment.getProfitAmount());
            stmt.setString(13, investment.getBroker());
            stmt.setString(14, investment.getInstitution());
            stmt.setString(15, investment.getNote());
            stmt.setInt(16, investment.isRedeemed() ? 1 : 0);
            stmt.setDate(17, investment.getRedemptionDate() != null ? 
                    Date.valueOf(investment.getRedemptionDate()) : null);
            stmt.setBigDecimal(18, investment.getRedemptionAmount());
            stmt.setInt(19, investment.isActive() ? 1 : 0);
            stmt.setTimestamp(20, Timestamp.valueOf(investment.getCreatedAt()));
            stmt.setTimestamp(21, Timestamp.valueOf(investment.getUpdatedAt()));

            stmt.executeUpdate();
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
        String sql = "UPDATE FixedInvestments SET name = ?, description = ?, investment_type = ?, " +
                    "investment_account_id = ?, investment_amount = ?, tax_rate = ?, investment_date = ?, " +
                    "maturity_date = ?, expected_return = ?, current_amount = ?, profit_amount = ?, " +
                    "broker = ?, institution = ?, note = ?, is_redeemed = ?, redemption_date = ?, " +
                    "redemption_amount = ?, is_active = ?, updated_at = ? " +
                    "WHERE fixed_investment_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, investment.getName());
            stmt.setString(2, investment.getDescription());
            stmt.setString(3, investment.getInvestmentType().name());
            stmt.setString(4, investment.getInvestmentAccount().getAccountId());
            stmt.setBigDecimal(5, investment.getInvestmentAmount());
            stmt.setBigDecimal(6, investment.getTaxRate());
            stmt.setDate(7, Date.valueOf(investment.getInvestmentDate()));
            stmt.setDate(8, Date.valueOf(investment.getMaturityDate()));
            stmt.setBigDecimal(9, investment.getExpectedReturn());
            stmt.setBigDecimal(10, investment.getCurrentAmount());
            stmt.setBigDecimal(11, investment.getProfitAmount());
            stmt.setString(12, investment.getBroker());
            stmt.setString(13, investment.getInstitution());
            stmt.setString(14, investment.getNote());
            stmt.setInt(15, investment.isRedeemed() ? 1 : 0);
            stmt.setDate(16, investment.getRedemptionDate() != null ? 
                    Date.valueOf(investment.getRedemptionDate()) : null);
            stmt.setBigDecimal(17, investment.getRedemptionAmount());
            stmt.setInt(18, investment.isActive() ? 1 : 0);
            stmt.setTimestamp(19, Timestamp.valueOf(investment.getUpdatedAt()));
            stmt.setString(20, investment.getFixedInvestmentId());

            stmt.executeUpdate();
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
        String sql = "SELECT fi.*, ia.* FROM FixedInvestments fi " +
                    "JOIN InvestmentAccounts ia ON fi.investment_account_id = ia.account_id " +
                    "WHERE fi.is_redeemed = 0 AND fi.is_active = 1";
        return executeQuery(sql);
    }

    @Override
    public List<FixedInvestment> findMaturedInvestments() {
        String sql = "SELECT fi.*, ia.* FROM FixedInvestments fi " +
                    "JOIN InvestmentAccounts ia ON fi.investment_account_id = ia.account_id " +
                    "WHERE fi.maturity_date <= CURRENT_DATE AND fi.is_redeemed = 0";
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
        investment.setFixedInvestmentId(rs.getString("fixed_investment_id"));
        investment.setName(rs.getString("name"));
        investment.setDescription(rs.getString("description"));
        investment.setInvestmentType(FixedInvestmentType.valueOf(rs.getString("investment_type")));
        
        InvestmentAccount account = new InvestmentAccount();
        // Preencher propriedades da conta de investimento
        account.setAccountId(rs.getString("account_id"));
        account.setName(rs.getString("account_name"));
        // ... outras propriedades da conta
        investment.setInvestmentAccount(account);
        
        investment.setInvestmentAmount(rs.getBigDecimal("investment_amount"));
        investment.setTaxRate(rs.getBigDecimal("tax_rate"));
        investment.setInvestmentDate(rs.getDate("investment_date").toLocalDate());
        investment.setMaturityDate(rs.getDate("maturity_date").toLocalDate());
        investment.setExpectedReturn(rs.getBigDecimal("expected_return"));
        investment.setCurrentAmount(rs.getBigDecimal("current_amount"));
        investment.setProfitAmount(rs.getBigDecimal("profit_amount"));
        investment.setBroker(rs.getString("broker"));
        investment.setInstitution(rs.getString("institution"));
        investment.setNote(rs.getString("note"));
        investment.setRedeemed(rs.getInt("is_redeemed") == 1);
        
        Date redemptionDate = rs.getDate("redemption_date");
        if (redemptionDate != null) {
            investment.setRedemptionDate(redemptionDate.toLocalDate());
        }
        
        investment.setRedemptionAmount(rs.getBigDecimal("redemption_amount"));
        investment.setActive(rs.getInt("is_active") == 1);
        investment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        investment.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        
        return investment;
    }
} 