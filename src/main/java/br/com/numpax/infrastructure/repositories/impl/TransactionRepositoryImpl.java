package br.com.numpax.infrastructure.repositories.impl;

import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;
import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.enums.CategoryType;
import br.com.numpax.infrastructure.entities.Transaction;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.repositories.TransactionRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionRepositoryImpl implements TransactionRepository {
    private final Connection connection;

    public TransactionRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Transaction transaction) {
        String sql = "INSERT INTO Transactions (transaction_id, code, name, description, amount, " +
                    "category_id, account_id, nature_of_transaction, receiver, sender, " +
                    "transaction_date, is_repeatable, repeatable_type, note, is_active, " +
                    "is_effective, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transaction.getTransactionId());
            stmt.setString(2, transaction.getCode());
            stmt.setString(3, transaction.getName());
            stmt.setString(4, transaction.getDescription());
            stmt.setBigDecimal(5, transaction.getAmount());
            stmt.setString(6, transaction.getCategory().getId());
            stmt.setString(7, transaction.getAccount().getAccountId());
            stmt.setString(8, transaction.getNatureOfTransaction().toString());
            stmt.setString(9, transaction.getReceiver());
            stmt.setString(10, transaction.getSender());
            stmt.setDate(11, Date.valueOf(transaction.getTransactionDate()));
            stmt.setBoolean(12, transaction.isRepeatable());
            stmt.setString(13, transaction.getRepeatableType() != null ? 
                         transaction.getRepeatableType().toString() : null);
            stmt.setString(14, transaction.getNote());
            stmt.setBoolean(15, transaction.isActive());
            stmt.setBoolean(16, transaction.isEffective());
            stmt.setTimestamp(17, Timestamp.valueOf(transaction.getCreatedAt()));
            stmt.setTimestamp(18, Timestamp.valueOf(transaction.getUpdatedAt()));
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar transação", e);
        }
    }

    @Override
    public Optional<Transaction> findById(String transactionId) {
        String sql = "SELECT t.*, c.*, a.* FROM Transactions t " +
                    "LEFT JOIN Categories c ON t.category_id = c.category_id " +
                    "LEFT JOIN Accounts a ON t.account_id = a.account_id " +
                    "WHERE t.transaction_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToTransaction(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transação", e);
        }
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, c.*, a.* FROM Transactions t " +
                    "LEFT JOIN Categories c ON t.category_id = c.category_id " +
                    "LEFT JOIN Accounts a ON t.account_id = a.account_id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
            return transactions;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar transações", e);
        }
    }

    @Override
    public List<Transaction> findByAccountId(String accountId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, c.*, a.* FROM Transactions t " +
                    "LEFT JOIN Categories c ON t.category_id = c.category_id " +
                    "LEFT JOIN Accounts a ON t.account_id = a.account_id " +
                    "WHERE t.account_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
            return transactions;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transações da conta", e);
        }
    }

    @Override
    public void update(Transaction transaction) {
        String sql = "UPDATE Transactions SET " +
                    "code = ?, name = ?, description = ?, amount = ?, " +
                    "category_id = ?, account_id = ?, nature_of_transaction = ?, " +
                    "receiver = ?, sender = ?, transaction_date = ?, " +
                    "is_repeatable = ?, repeatable_type = ?, note = ?, " +
                    "is_active = ?, is_effective = ?, updated_at = ? " +
                    "WHERE transaction_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transaction.getCode());
            stmt.setString(2, transaction.getName());
            stmt.setString(3, transaction.getDescription());
            stmt.setBigDecimal(4, transaction.getAmount());
            stmt.setString(5, transaction.getCategory().getId());
            stmt.setString(6, transaction.getAccount().getAccountId());
            stmt.setString(7, transaction.getNatureOfTransaction().toString());
            stmt.setString(8, transaction.getReceiver());
            stmt.setString(9, transaction.getSender());
            stmt.setDate(10, Date.valueOf(transaction.getTransactionDate()));
            stmt.setBoolean(11, transaction.isRepeatable());
            stmt.setString(12, transaction.getRepeatableType() != null ? 
                         transaction.getRepeatableType().toString() : null);
            stmt.setString(13, transaction.getNote());
            stmt.setBoolean(14, transaction.isActive());
            stmt.setBoolean(15, transaction.isEffective());
            stmt.setTimestamp(16, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(17, transaction.getTransactionId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar transação", e);
        }
    }

    @Override
    public void delete(String transactionId) {
        String sql = "DELETE FROM Transactions WHERE transaction_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar transação", e);
        }
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rs.getString("transaction_id"));
        transaction.setCode(rs.getString("code"));
        transaction.setName(rs.getString("name"));
        transaction.setDescription(rs.getString("description"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setNatureOfTransaction(NatureOfTransaction.valueOf(rs.getString("nature_of_transaction")));
        transaction.setReceiver(rs.getString("receiver"));
        transaction.setSender(rs.getString("sender"));
        transaction.setTransactionDate(rs.getDate("transaction_date").toLocalDate());
        transaction.setRepeatable(rs.getBoolean("is_repeatable"));
        String repeatableType = rs.getString("repeatable_type");
        if (repeatableType != null) {
            transaction.setRepeatableType(RepeatableType.valueOf(repeatableType));
        }
        transaction.setNote(rs.getString("note"));
        transaction.setActive(rs.getBoolean("is_active"));
        transaction.setEffective(rs.getBoolean("is_effective"));
        transaction.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        transaction.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        // Mapear Category
        Category category = new Category();
        category.setCategoryId(rs.getString("category_id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        category.setCategoryType(CategoryType.valueOf(rs.getString("category_type")));
        category.setIsActive(rs.getBoolean("is_active"));
        category.setIsDefault(rs.getBoolean("is_default"));
        transaction.setCategory(category);

        // Mapear Account
        Account account = new Account();
        account.setAccountId(rs.getString("account_id"));
        account.setName(rs.getString("name"));
        account.setDescription(rs.getString("description"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
        account.setIsActive(rs.getBoolean("is_active"));
        transaction.setAccount(account);

        return transaction;
    }
} 