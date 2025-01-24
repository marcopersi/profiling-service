package org.actics.customer.profiling.configuration;

import org.jooq.ConnectionProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SimpleConnectionProvider implements ConnectionProvider {
    private final DataSource dataSource;

    public SimpleConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection acquire() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to acquire connection from DataSource", e);
        }
    }

    @Override
    public void release(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to release connection", e);
        }
    }
}
