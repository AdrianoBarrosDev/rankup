package com.rankup.sql;

import java.sql.Connection;

public record PoolDto(int codigo, Connection connection) {
}
