package br.com.numpax.infrastructure.repositories.impl;

import br.com.numpax.infrastructure.entities.Transaction;
import br.com.numpax.infrastructure.repositories.TransactionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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

}
