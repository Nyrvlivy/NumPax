package br.com.numpax.infrastructure.transaction;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ApplicationScoped
public class TransactionManager {
    
    private final DataSource dataSource;
    private final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    @Inject
    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void beginTransaction() throws SQLException {
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        connectionHolder.set(conn);
    }

    public void commitTransaction() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                conn.commit();
            } finally {
                cleanup();
            }
        }
    }

    public void rollbackTransaction() {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException("Error rolling back transaction", e);
            } finally {
                cleanup();
            }
        }
    }

    private void cleanup() {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error cleaning up connection", e);
            } finally {
                connectionHolder.remove();
            }
        }
    }

    public Connection getCurrentConnection() {
        return connectionHolder.get();
    }
} 