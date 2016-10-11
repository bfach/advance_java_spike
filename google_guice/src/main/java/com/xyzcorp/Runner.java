package com.xyzcorp;

import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.xyzcorp.dao.AlbumDAO;
import com.xyzcorp.models.Album;

import java.sql.SQLException;

/**
 * Created by bfach on 10/11/16.
 */
public class Runner {
    public static void main(String args[]) throws SQLException {
        //create a container where all of these mappings will take place
        final Injector injector = Guice.createInjector(new AlbumModule());

        AlbumDAO albumDAO = injector.getInstance(AlbumDAO.class);
        albumDAO.insert(new Album("Dark side of the moon", "Pink Floyd", 1972));

    }
}
