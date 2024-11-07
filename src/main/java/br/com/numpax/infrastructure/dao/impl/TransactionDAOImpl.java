package br.com.numpax.infrastructure.dao.impl;

import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.dao.TransactionDAO;
import br.com.numpax.infrastructure.entities.Transaction;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;
import br.com.numpax.infrastructure.entities.RegularAccount;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDAOImpl implements TransactionDAO {

    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public void save(Transaction transaction) {
        String sql = """
            INSERT INTO Transactions (
                transaction_id, code, is_effective, name, description, amount, category_id,
                account_id, nature_of_transaction, receiver, sender, transaction_date,
                is_repeatable, repeatable_type, note, is_active
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); // Start transaction

            stmt.setString(1, transaction.getId());
            stmt.setString(2, transaction.getCode());
            stmt.setBoolean(3, transaction.getIsEffective());
            stmt.setString(4, transaction.getName());
            stmt.setString(5, transaction.getDescription());
            stmt.setBigDecimal(6, transaction.getAmount());
            stmt.setString(7, transaction.getCategory().getId());
            stmt.setString(8, transaction.getRegularAccount().getId());
            stmt.setString(9, transaction.getNatureOfTransaction().name());
            stmt.setString(10, transaction.getReceiver());
            stmt.setString(11, transaction.getSender());
            stmt.setDate(12, Date.valueOf(transaction.getTransactionDate()));
            stmt.setBoolean(13, transaction.isRepeatable());
            stmt.setString(14, transaction.getRepeatableType().name());
            stmt.setString(15, transaction.getNote());
            stmt.setBoolean(16, transaction.isActive());

            stmt.executeUpdate();
            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar a transação", e);
        }
    }

    @Override
    public void update(Transaction transaction) {
        String sql = """
            UPDATE Transactions SET code = ?, name = ?, description = ?, amount = ?, category_id = ?, account_id = ?, nature_of_transaction = ?,
                receiver = ?, sender = ?, transaction_date = ?,
                is_repeatable = ?, repeatable_type = ?, note = ?,
                is_active = ?, is_effective = ?, updated_at = CURRENT_TIMESTAMP
            WHERE transaction_id = ?
        """;
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); // Start transaction

            stmt.setString(1, transaction.getCode());
            stmt.setString(2, transaction.getName());
            stmt.setString(3, transaction.getDescription());
            stmt.setBigDecimal(4, transaction.getAmount());
            stmt.setString(5, transaction.getCategory().getId());
            stmt.setString(6, transaction.getRegularAccount().getId());
            stmt.setString(7, transaction.getNatureOfTransaction().name());
            stmt.setString(8, transaction.getReceiver());
            stmt.setString(9, transaction.getSender());
            stmt.setDate(10, Date.valueOf(transaction.getTransactionDate()));
            stmt.setBoolean(11, transaction.isRepeatable());
            stmt.setString(12, transaction.getRepeatableType().name());
            stmt.setString(13, transaction.getNote());
            stmt.setBoolean(14, transaction.isActive());
            stmt.setBoolean(15, transaction.getIsEffective());
            stmt.setString(16, transaction.getId());

            stmt.executeUpdate();
            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a transação", e);
        }
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM Transactions WHERE transaction_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); // Start transaction

            stmt.setString(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhuma transação encontrada com o ID: " + id);
            }

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar a transação com ID: " + id, e);
        }
    }

    @Override
    public Optional<Transaction> findById(String id) {
        String sql = "SELECT * FROM Transactions WHERE transaction_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar a transação", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Transaction> findByAccount(Account account) {
        String sql = "SELECT * FROM Transactions WHERE account_id = ?";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transações da conta", e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> findAll() {
        String sql = "SELECT * FROM Transactions";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transações", e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM Transactions WHERE transaction_date BETWEEN ? AND ?";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transações por data", e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> findRepeatable() {
        String sql = "SELECT * FROM Transactions WHERE is_repeatable = 1";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transações repetíveis", e);
        }
        return transactions;
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Account account = new AccountDAOImpl().findById(rs.getString("account_id"))
            .orElseThrow(() -> new RuntimeException("Account not found"));
        
        if (!(account instanceof RegularAccount)) {
            throw new RuntimeException("Invalid account type");
        }

        return new Transaction(
            rs.getString("code"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getBigDecimal("amount"),
            new CategoryDAOImpl().findById(rs.getString("category_id")).orElse(null),
            (RegularAccount) account,
            NatureOfTransaction.valueOf(rs.getString("nature_of_transaction")),
            rs.getString("receiver"),
            rs.getString("sender"),
            rs.getDate("transaction_date").toLocalDate(),
            RepeatableType.valueOf(rs.getString("repeatable_type")),
            rs.getString("note")
        );
    }
}
