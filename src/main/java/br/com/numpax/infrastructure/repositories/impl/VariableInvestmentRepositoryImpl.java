package br.com.numpax.infrastructure.repositories.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VariableInvestmentRepositoryImpl {

    public BigDecimal calculateTotalProfit(String accountId) {
        String sql = "SELECT COALESCE(SUM((v.quantity * v.sale_price) - t.amount), 0) " +
                    "FROM VariableInvestments v " +
                    "JOIN Transactions t ON v.transaction_id = t.id " +
                    "WHERE t.account_id = ? " +
                    "AND v.is_active = 1 " +
                    "AND v.sale_date IS NOT NULL " +
                    "AND (v.expiration_date IS NULL OR v.expiration_date > CURRENT_DATE)";

        return executeMonetaryQuery(sql, accountId);
    }

    public BigDecimal calculateTotalCurrentAmount(String accountId) {
        String sql = "SELECT COALESCE(SUM(CASE " +
                    "   WHEN v.sale_date IS NOT NULL THEN (v.quantity * v.sale_price) - t.amount " +
                    "   ELSE t.amount " +
                    "END), 0) " +
                    "FROM VariableInvestments v " +
                    "JOIN Transactions t ON v.transaction_id = t.id " +
                    "WHERE t.account_id = ? " +
                    "AND v.is_active = 1 " +
                    "AND v.sale_date IS NULL";

        return executeMonetaryQuery(sql, accountId);
    }

    public BigDecimal calculateTotalWithdrawnAmount(String accountId) {
        String sql = "SELECT COALESCE(SUM((v.quantity * v.sale_price) - t.amount), 0) " +
                    "FROM VariableInvestments v " +
                    "JOIN Transactions t ON v.transaction_id = t.id " +
                    "WHERE t.account_id = ? " +
                    "AND v.is_active = 1 " +
                    "AND v.expiration_date < CURRENT_DATE";

        return executeMonetaryQuery(sql, accountId);
    }

    public BigDecimal calculateAveragePurchasePrice(String accountId) {
        String sql = "SELECT COALESCE(AVG(v.unit_price), 0) " +
                    "FROM VariableInvestments v " +
                    "JOIN Transactions t ON v.transaction_id = t.id " +
                    "WHERE t.account_id = ? " +
                    "AND v.is_active = 1";

        return executeMonetaryQuery(sql, accountId);
    }

    public BigDecimal calculateTotalGainLoss(String accountId) {
        String sql = "SELECT COALESCE(SUM((v.quantity * v.sale_price) - t.amount), 0) " +
                    "FROM VariableInvestments v " +
                    "JOIN Transactions t ON v.transaction_id = t.id " +
                    "WHERE t.account_id = ? " +
                    "AND v.is_active = 1 " +
                    "AND v.sale_date IS NOT NULL";

        return executeMonetaryQuery(sql, accountId);
    }

    public int countWithdrawals(String accountId) {
        String sql = "SELECT COUNT(*) " +
                    "FROM VariableInvestments v " +
                    "JOIN Transactions t ON v.transaction_id = t.id " +
                    "WHERE t.account_id = ? " +
                    "AND v.is_active = 1 " +
                    "AND v.sale_date IS NOT NULL";

        return executeCountQuery(sql, accountId);
    }

    public int countEntries(String accountId) {
        String sql = "SELECT COUNT(*) " +
                    "FROM VariableInvestments v " +
                    "JOIN Transactions t ON v.transaction_id = t.id " +
                    "WHERE t.account_id = ? " +
                    "AND v.is_active = 1 " +
                    "AND v.sale_date IS NULL";

        return executeCountQuery(sql, accountId);
    }

    public int countDistinctAssets(String accountId) {
        String sql = "SELECT COUNT(DISTINCT v.asset_code) " +
                    "FROM VariableInvestments v " +
                    "JOIN Transactions t ON v.transaction_id = t.id " +
                    "WHERE t.account_id = ? " +
                    "AND v.is_active = 1 " +
                    "AND v.sale_date IS NULL " +
                    "AND (v.expiration_date IS NULL OR v.expiration_date > CURRENT_DATE)";

        return executeCountQuery(sql, accountId);
    }

    private BigDecimal executeMonetaryQuery(String sql, String accountId) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing monetary query", e);
        }
        return BigDecimal.ZERO;
    }

    private int executeCountQuery(String sql, String accountId) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing count query", e);
        }
        return 0;
    }
} 