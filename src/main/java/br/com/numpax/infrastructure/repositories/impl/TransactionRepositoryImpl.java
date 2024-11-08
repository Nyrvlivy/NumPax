package br.com.numpax.infrastructure.repositories.impl;

import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.infrastructure.entities.Transaction;
import br.com.numpax.infrastructure.repositories.TransactionRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

public class TransactionRepositoryImpl implements TransactionRepository {

    private final Connection connection;

    public TransactionRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Transaction transaction) {
        String sql = "INSERT INTO Transactions (transaction_id, code, is_effective, name, description, amount, " +
                    "category_id, account_id, nature_of_transaction, receiver, sender, transaction_date, " +
                    "is_repeatable, repeatable_type, note, is_active, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transaction.getTransactionId());
            stmt.setString(2, transaction.getCode());
            stmt.setInt(3, transaction.isEffective() ? 1 : 0);
            stmt.setString(4, transaction.getName());
            stmt.setString(5, transaction.getDescription());
            stmt.setBigDecimal(6, transaction.getAmount());
            stmt.setString(7, transaction.getCategory().getId());
            stmt.setString(8, transaction.getAccount().getAccountId());
            stmt.setString(9, transaction.getNatureOfTransaction().name());
            stmt.setString(10, transaction.getReceiver());
            stmt.setString(11, transaction.getSender());
            stmt.setDate(12, Date.valueOf(transaction.getTransactionDate()));
            stmt.setInt(13, transaction.isRepeatable() ? 1 : 0);
            stmt.setString(14, transaction.getRepeatableType() != null ? transaction.getRepeatableType().name() : null);
            stmt.setString(15, transaction.getNote());
            stmt.setInt(16, transaction.isActive() ? 1 : 0);
            stmt.setTimestamp(17, Timestamp.valueOf(transaction.getCreatedAt()));
            stmt.setTimestamp(18, Timestamp.valueOf(transaction.getUpdatedAt()));
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating transaction", e);
        }
    }

    @Override
    public Optional<Transaction> findById(String transactionId) {
        String sql = "SELECT t.*, c.*, a.* FROM Transactions t " +
                    "JOIN Categories c ON t.category_id = c.category_id " +
                    "JOIN Accounts a ON t.account_id = a.account_id " +
                    "WHERE t.transaction_id = ?";
                    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractTransactionFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding transaction", e);
        }
        return Optional.empty();
    }

    @Override
    public void update(Transaction transaction) {
        String sql = "UPDATE Transactions SET code = ?, is_effective = ?, name = ?, description = ?, " +
                    "amount = ?, category_id = ?, account_id = ?, nature_of_transaction = ?, " +
                    "receiver = ?, sender = ?, transaction_date = ?, is_repeatable = ?, " +
                    "repeatable_type = ?, note = ?, is_active = ?, updated_at = ? " +
                    "WHERE transaction_id = ?";
                    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transaction.getCode());
            stmt.setInt(2, transaction.isEffective() ? 1 : 0);
            stmt.setString(3, transaction.getName());
            stmt.setString(4, transaction.getDescription());
            stmt.setBigDecimal(5, transaction.getAmount());
            stmt.setString(6, transaction.getCategory().getId());
            stmt.setString(7, transaction.getAccount().getAccountId());
            stmt.setString(8, transaction.getNatureOfTransaction().name());
            stmt.setString(9, transaction.getReceiver());
            stmt.setString(10, transaction.getSender());
            stmt.setDate(11, Date.valueOf(transaction.getTransactionDate()));
            stmt.setInt(12, transaction.isRepeatable() ? 1 : 0);
            stmt.setString(13, transaction.getRepeatableType() != null ? transaction.getRepeatableType().name() : null);
            stmt.setString(14, transaction.getNote());
            stmt.setInt(15, transaction.isActive() ? 1 : 0);
            stmt.setTimestamp(16, Timestamp.valueOf(transaction.getUpdatedAt()));
            stmt.setString(17, transaction.getTransactionId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating transaction", e);
        }
    }

    private Transaction extractTransactionFromResultSet(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rs.getString("transaction_id"));
        transaction.setCode(rs.getString("code"));
        transaction.setEffective(rs.getInt("is_effective") == 1);
        transaction.setName(rs.getString("name"));
        transaction.setDescription(rs.getString("description"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        
        Category category = new Category();
        // Set category properties
        transaction.setCategory(category);
        
        Account account = new Account();
        // Set account properties
        transaction.setAccount(account);
        
        transaction.setNatureOfTransaction(NatureOfTransaction.valueOf(rs.getString("nature_of_transaction")));
        transaction.setReceiver(rs.getString("receiver"));
        transaction.setSender(rs.getString("sender"));
        transaction.setTransactionDate(rs.getDate("transaction_date").toLocalDate());
        transaction.setRepeatable(rs.getInt("is_repeatable") == 1);
        
        String repeatableType = rs.getString("repeatable_type");
        if (repeatableType != null) {
            transaction.setRepeatableType(RepeatableType.valueOf(repeatableType));
        }
        
        transaction.setNote(rs.getString("note"));
        transaction.setActive(rs.getInt("is_active") == 1);
        transaction.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        transaction.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        
        return transaction;
    }

    // Implementação dos métodos adicionais
    @Override
    public List<Transaction> findByAccountId(String accountId) {
        String sql = "SELECT t.*, c.*, a.* FROM Transactions t " +
                    "JOIN Categories c ON t.category_id = c.category_id " +
                    "JOIN Accounts a ON t.account_id = a.account_id " +
                    "WHERE t.account_id = ?";
                    
        return executeTransactionQuery(sql, accountId);
    }

    @Override
    public List<Transaction> findByCategoryId(String categoryId) {
        String sql = "SELECT t.*, c.*, a.* FROM Transactions t " +
                    "JOIN Categories c ON t.category_id = c.category_id " +
                    "JOIN Accounts a ON t.account_id = a.account_id " +
                    "WHERE t.category_id = ?";
                    
        return executeTransactionQuery(sql, categoryId);
    }

    @Override
    public List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT t.*, c.*, a.* FROM Transactions t " +
                    "JOIN Categories c ON t.category_id = c.category_id " +
                    "JOIN Accounts a ON t.account_id = a.account_id " +
                    "WHERE t.transaction_date BETWEEN ? AND ?";
                    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            
            return extractTransactionsFromResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException("Error finding transactions by date range", e);
        }
    }

    @Override
    public List<Transaction> findEffectiveTransactions() {
        String sql = "SELECT t.*, c.*, a.* FROM Transactions t " +
                    "JOIN Categories c ON t.category_id = c.category_id " +
                    "JOIN Accounts a ON t.account_id = a.account_id " +
                    "WHERE t.is_effective = 1";
                    
        return executeTransactionQuery(sql);
    }

    @Override
    public List<Transaction> findPendingTransactions() {
        String sql = "SELECT t.*, c.*, a.* FROM Transactions t " +
                    "JOIN Categories c ON t.category_id = c.category_id " +
                    "JOIN Accounts a ON t.account_id = a.account_id " +
                    "WHERE t.is_effective = 0";
                    
        return executeTransactionQuery(sql);
    }

    @Override
    public void markAsEffective(String transactionId) {
        String sql = "UPDATE Transactions SET is_effective = 1, updated_at = ? WHERE transaction_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(2, transactionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error marking transaction as effective", e);
        }
    }

    @Override
    public void delete(String transactionId) {
        String sql = "DELETE FROM Transactions WHERE transaction_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting transaction", e);
        }
    }

    @Override
    public List<Transaction> findAll() {
        String sql = "SELECT t.*, c.*, a.* FROM Transactions t " +
                    "JOIN Categories c ON t.category_id = c.category_id " +
                    "JOIN Accounts a ON t.account_id = a.account_id";
                    
        return executeTransactionQuery(sql);
    }

    @Override
    public List<Transaction> findAllActive() {
        String sql = "SELECT t.*, c.*, a.* FROM Transactions t " +
                    "JOIN Categories c ON t.category_id = c.category_id " +
                    "JOIN Accounts a ON t.account_id = a.account_id " +
                    "WHERE t.is_active = 1";
                    
        return executeTransactionQuery(sql);
    }

    @Override
    public List<Transaction> findAllInactive() {
        String sql = "SELECT t.*, c.*, a.* FROM Transactions t " +
                    "JOIN Categories c ON t.category_id = c.category_id " +
                    "JOIN Accounts a ON t.account_id = a.account_id " +
                    "WHERE t.is_active = 0";
                    
        return executeTransactionQuery(sql);
    }

    // Métodos auxiliares
    private List<Transaction> executeTransactionQuery(String sql, String... params) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setString(i + 1, params[i]);
            }
            return extractTransactionsFromResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException("Error executing transaction query", e);
        }
    }

    private List<Transaction> extractTransactionsFromResultSet(ResultSet rs) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            transactions.add(extractTransactionFromResultSet(rs));
        }
        return transactions;
    }

    @Override
    public BigDecimal calculateTotalInvestedAmount(String accountId) {
        String sql = "SELECT COALESCE(SUM(amount), 0) " +
                    "FROM Transactions " +
                    "WHERE account_id = ? " +
                    "AND is_active = 1 " +
                    "AND nature_of_transaction = 'VARIABLE_INVESTMENT'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating total invested amount", e);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal calculateTotalDividendYield(String accountId) {
        String sql = "SELECT COALESCE(SUM(t.amount), 0) " +
                    "FROM Transactions t " +
                    "JOIN Categories c ON t.category_id = c.id " +
                    "WHERE t.account_id = ? " +
                    "AND t.is_active = 1 " +
                    "AND c.name = 'DIVIDEND'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating total dividend yield", e);
        }
        return BigDecimal.ZERO;
    }
}
