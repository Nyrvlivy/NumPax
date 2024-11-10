package br.com.numpax.infrastructure.repositories.impl;

import br.com.numpax.API.V1.dto.response.ActiveTransactionDTO;
import br.com.numpax.infrastructure.entities.Transaction;
import br.com.numpax.infrastructure.repositories.TransactionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepository {

    private final Connection connection;

    public TransactionRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Transaction transaction) {
        String sql =
            "INSERT INTO transactions " +
                "(transaction_id, code, name, description, amount, category_id, account_id, " +
                "nature_of_transaction, receiver, sender, transaction_date, is_repeatable, " +
                "repeatable_type, note, is_active, is_effective, created_at, updated_at) " +
                "VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, transaction.getTransactionId());
            preparedStatement.setString(2, transaction.getCode());
            preparedStatement.setString(3, transaction.getName());
            preparedStatement.setString(4, transaction.getDescription());
            preparedStatement.setBigDecimal(5, transaction.getAmount());
            preparedStatement.setString(6, transaction.getCategory().getId());
            preparedStatement.setString(7, transaction.getAccount().getAccountId());
            preparedStatement.setString(8, String.valueOf(transaction.getNatureOfTransaction()));
            preparedStatement.setString(9, transaction.getReceiver());
            preparedStatement.setString(10, transaction.getSender());
            preparedStatement.setTimestamp(11, Timestamp.valueOf(transaction.getTransactionDate().atStartOfDay()));
            preparedStatement.setBoolean(12, transaction.isRepeatable());
            preparedStatement.setString(13, String.valueOf(transaction.getRepeatableType()));
            preparedStatement.setString(14, transaction.getNote());
            preparedStatement.setBoolean(15, transaction.isActive());
            preparedStatement.setBoolean(16, transaction.isEffective());
            preparedStatement.setTimestamp(17, Timestamp.valueOf(transaction.getCreatedAt()));
            preparedStatement.setTimestamp(18, Timestamp.valueOf(transaction.getUpdatedAt()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating transaction", e);
        }
    }

    @Override
    public List<ActiveTransactionDTO> findActiveTransactionsByUserId(String userId) {
        String sql = "SELECT t.transaction_id, t.is_effective, t.transaction_date, t.name, " +
            "c.name AS category_name, a.name AS account_name, t.amount " +
            "FROM Transactions t " +
            "JOIN Accounts a ON t.account_id = a.account_id " +
            "JOIN Categories c ON t.category_id = c.category_id " +
            "WHERE a.user_id = ? " +
            "AND t.is_active = 1 " +
            "AND t.nature_of_transaction IN ('INCOME', 'EXPENSE', 'TRANSFER')";

        List<ActiveTransactionDTO> transactions = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ActiveTransactionDTO dto = new ActiveTransactionDTO();
                    dto.setTransactionId(rs.getString("transaction_id"));
                    dto.setEffective(rs.getInt("is_effective") == 1);
                    dto.setTransactionDate(rs.getDate("transaction_date"));
                    dto.setName(rs.getString("name"));
                    dto.setCategoryName(rs.getString("category_name"));
                    dto.setAccountName(rs.getString("account_name"));
                    dto.setAmount(rs.getBigDecimal("amount"));
                    transactions.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transações ativas", e);
        }
        return transactions;
    }

}
