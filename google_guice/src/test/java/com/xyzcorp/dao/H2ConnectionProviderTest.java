package com.xyzcorp.dao;

import org.junit.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;


public class H2ConnectionProviderTest {

    @Test
    public void testGetConnection() {
        H2ConnectionPoolProvider cpProvider = new H2ConnectionPoolProvider( "jdbc:h2:tcp://localhost/~/test", "sa", "");
        H2ConnectionProvider provider = new H2ConnectionProvider(cpProvider.get());
        assertThat(provider.get()).isNotNull().isInstanceOf(Connection.class);
    }
}