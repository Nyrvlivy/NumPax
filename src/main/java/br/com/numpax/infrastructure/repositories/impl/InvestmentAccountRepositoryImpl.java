package br.com.numpax.infrastructure.repositories.impl;

import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.repositories.InvestmentAccountRepository;

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
        String sql = "INSERT INTO InvestmentAccounts (account_id, name, description, institution, " +
                    "account_number, agency, note, is_active, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getAccountId());
            stmt.setString(2, account.getName());
            stmt.setString(3, account.getDescription());
            stmt.setString(4, account.getInstitution());
            stmt.setString(5, account.getAccountNumber());
            stmt.setString(6, account.getAgency());
            stmt.setString(7, account.getNote());
            stmt.setBoolean(8, account.isActive());
            stmt.setTimestamp(9, Timestamp.valueOf(account.getCreatedAt()));
            stmt.setTimestamp(10, Timestamp.valueOf(account.getUpdatedAt()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating investment account", e);
        }
    }

    @Override
    public Optional<InvestmentAccount> findById(String accountId) {
        String sql = "SELECT * FROM InvestmentAccounts WHERE account_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding investment account", e);
        }
        return Optional.empty();
    }

    @Override
    public void update(InvestmentAccount account) {
        String sql = "UPDATE InvestmentAccounts SET name = ?, description = ?, institution = ?, " +
                    "account_number = ?, agency = ?, note = ?, is_active = ?, updated_at = ? " +
                    "WHERE account_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getName());
            stmt.setString(2, account.getDescription());
            stmt.setString(3, account.getInstitution());
            stmt.setString(4, account.getAccountNumber());
            stmt.setString(5, account.getAgency());
            stmt.setString(6, account.getNote());
            stmt.setBoolean(7, account.isActive());
            stmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(9, account.getAccountId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating investment account", e);
        }
    }

    @Override
    public void delete(String accountId) {
        String sql = "DELETE FROM InvestmentAccounts WHERE account_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, accountId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting investment account", e);
        }
    }

    @Override
    public List<InvestmentAccount> findAll() {
        String sql = "SELECT * FROM InvestmentAccounts";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            return extractListFromResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all investment accounts", e);
        }
    }

    @Override
    public List<InvestmentAccount> findByInstitution(String institution) {
        String sql = "SELECT * FROM InvestmentAccounts WHERE institution = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, institution);
            try (ResultSet rs = stmt.executeQuery()) {
                return extractListFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding accounts by institution", e);
        }
    }

    @Override
    public List<InvestmentAccount> findAllActive() {
        String sql = "SELECT * FROM InvestmentAccounts WHERE is_active = true";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            return extractListFromResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding active investment accounts", e);
        }
    }

    @Override
    public List<InvestmentAccount> findAllInactive() {
        String sql = "SELECT * FROM InvestmentAccounts WHERE is_active = false";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            return extractListFromResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding inactive investment accounts", e);
        }
    }

    @Override
    public Optional<InvestmentAccount> findByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM InvestmentAccounts WHERE account_number = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding investment account by account number", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<InvestmentAccount> findByAgencyAndAccountNumber(String agency, String accountNumber) {
        String sql = "SELECT * FROM InvestmentAccounts WHERE agency = ? AND account_number = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agency);
            stmt.setString(2, accountNumber);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding investment account by agency and account number", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        String sql = "SELECT COUNT(*) FROM InvestmentAccounts WHERE account_number = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking account number existence", e);
        }
        return false;
    }

    @Override
    public boolean existsByAgencyAndAccountNumber(String agency, String accountNumber) {
        String sql = "SELECT COUNT(*) FROM InvestmentAccounts WHERE agency = ? AND account_number = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agency);
            stmt.setString(2, accountNumber);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking agency and account number existence", e);
        }
        return false;
    }

    @Override
    public RiskLevelType findMostFrequentRiskLevel(String accountId) {
        String sql = "SELECT risk_level_type, COUNT(risk_level_type) AS frequency " +
                    "FROM VariableInvestments " +
                    "WHERE investment_account_id = ? AND is_active = 1 " +
                    "GROUP BY risk_level_type " +
                    "ORDER BY frequency DESC " +
                    "LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String riskLevel = rs.getString("risk_level_type");
                    return riskLevel != null ? RiskLevelType.valueOf(riskLevel) : RiskLevelType.VERY_LOW;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding most frequent risk level", e);
        }
        return RiskLevelType.VERY_LOW; // Default quando não há investimentos
    }

    private List<InvestmentAccount> extractListFromResultSet(ResultSet rs) throws SQLException {
        List<InvestmentAccount> accounts = new ArrayList<>();
        while (rs.next()) {
            accounts.add(extractFromResultSet(rs));
        }
        return accounts;
    }

    private InvestmentAccount extractFromResultSet(ResultSet rs) throws SQLException {
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId(rs.getString("account_id"));
        account.setName(rs.getString("name"));
        account.setDescription(rs.getString("description"));
        account.setInstitution(rs.getString("institution"));
        account.setAccountNumber(rs.getString("account_number"));
        account.setAgency(rs.getString("agency"));
        account.setNote(rs.getString("note"));
        account.setActive(rs.getBoolean("is_active"));
        account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        account.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return account;
    }
}
