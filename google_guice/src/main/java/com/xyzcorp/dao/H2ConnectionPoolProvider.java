package com.xyzcorp.dao;

import com.google.inject.Provider;
import com.google.inject.name.Named;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by bfach on 10/11/16.
 */
public class H2ConnectionPoolProvider implements Provider<DataSource> {

    private String url;
    private String userName;
    private String password;

    public H2ConnectionPoolProvider(){}

    @Inject
    public H2ConnectionPoolProvider(@Named("url") String url, @Named("userName") String userName, @Named ("password") String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public DataSource get() {
        return JdbcConnectionPool.create(
                url, userName, password);
    }
}
