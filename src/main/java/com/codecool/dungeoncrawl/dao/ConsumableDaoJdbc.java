package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ConsumableModel;

import javax.sql.DataSource;
import java.sql.*;

public class ConsumableDaoJdbc implements ConsumableDao{
    DataSource dataSource;
//    MapModelDao mapModelDao;

    public ConsumableDaoJdbc (DataSource dataSource/*, MapModelDao mapModel*/ ) {
        this.dataSource = dataSource;
//        this.mapModelDao = mapModel;
    }

    @Override
    public void add(ConsumableModel model) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO consumable (x, y, type, map_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, model.getX());
            statement.setInt(2, model.getY());
            statement.setString(3, model.getConsumableType());
            statement.setInt(4, model.getMapId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            model.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ConsumableModel model) {

    }

    @Override
    public ConsumableModel get(int id) {
        return null;
    }
}
