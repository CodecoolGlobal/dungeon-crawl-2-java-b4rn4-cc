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
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "UPDATE consumable SET x = ?, y = ?, type = ?, map_id = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, model.getX());
            statement.setInt(2, model.getY());
            statement.setString(3, model.getConsumableType());
            statement.setInt(4, model.getMapId());
            statement.setInt(5, model.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ConsumableModel get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT x, y, type, map_id FROM consumable WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            int x = resultSet.getInt(1);
            int y = resultSet.getInt(2);
            String type = resultSet.getString(3);
            int mapId = resultSet.getInt(4);
            ConsumableModel result = new ConsumableModel(type, x, y, mapId);
            result.setId(id);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
