package com.rankup.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {

    private static final String URL = "jdbc:sqlite:rankup.db";
    private Connection connection;


    public Connection conectar() throws SQLException {
        if(connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
        }
        return connection;
    }

    public void fecharConexao() throws SQLException {
        if(connection != null) {
            connection.close();
        }
    }

}
