package com.rankup.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {

    private PoolDto poolDto;

    public Connection conectar() throws SQLException {
        this.poolDto = PoolConexoes.getConnection();
        return poolDto.connection();
    }

    public void fecharConexao() throws SQLException {
        PoolConexoes.releaseConnection(poolDto);
    }

}
