package com.xyzcorp;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.xyzcorp.annotations.H2;
import com.xyzcorp.dao.AlbumDAO;
import com.xyzcorp.dao.H2ConnectionProvider;
import com.xyzcorp.dao.H2ConnectionPoolProvider;
import com.xyzcorp.dao.StandardAlbumDAO;

import java.sql.Connection;

/**
 * Created by bfach on 10/11/16.
 */
public class AlbumModule extends AbstractModule {


    @Override
    protected void configure() {

        //wiring all takes place here
        bind(AlbumDAO.class).to(StandardAlbumDAO.class);
        //this binds the connection to the H2 annotation, and to provider implementation
        bind(Connection.class).annotatedWith(H2.class).toProvider(H2ConnectionProvider.class);
        bind(javax.sql.DataSource.class).annotatedWith(Names.named("h2CP"))
                .toProvider(H2ConnectionPoolProvider.class).asEagerSingleton();
        //another way you can do this... use the Nameed annotation
        //bind(Connection.class).annotatedWith(Names.named("h2")).toProvider(H2ConnectionProvider.class);

        bind(String.class).annotatedWith(Names.named("url")).toInstance("jdbc:h2:tcp://localhost/~/test");
        bind(String.class).annotatedWith(Names.named("userName")).toInstance("sa");
        bind(String.class).annotatedWith(Names.named("password")).toInstance("");
    }
}
