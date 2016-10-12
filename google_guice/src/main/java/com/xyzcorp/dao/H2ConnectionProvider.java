package com.xyzcorp.dao;

import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class H2ConnectionProvider implements Provider<Connection> {

    private DataSource cp;
    public H2ConnectionProvider(){}

    @Inject
    public H2ConnectionProvider(@Named("h2CP") DataSource cp) {
        this.cp = cp;
    }

    @Override
    public Connection get() {
        try {
            return cp.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create a connection", e);
        }
    }
}
