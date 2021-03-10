package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Entity;
import ru.itmo.wp.model.domain.EntityCreator;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;

public abstract class BasicRepositoryImpl {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    protected Object find(long id, String name, EntityCreator creator) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            String s = "SELECT * FROM " + name;
            s += " WHERE id=?";

            try (PreparedStatement statement = connection.prepareStatement(s)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return creator.create(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + name + ".", e);
        }
    }

}
