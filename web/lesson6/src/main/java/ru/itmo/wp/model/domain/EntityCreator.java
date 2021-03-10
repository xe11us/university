package ru.itmo.wp.model.domain;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

@FunctionalInterface
public interface EntityCreator {
    Entity create(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;
}
