package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(GameState state) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO game_state (game_state_name, current_map_number, saved_at) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, state.getGameStateName());
            statement.setInt(2, state.getCurrentMapNumber());
            statement.setDate(3, state.getSavedAt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            state.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(GameState state) {
        try (Connection connection = dataSource.getConnection()){
            String sqlQuery = "UPDATE game_state SET game_state_name = ?, current_map_number = ?, saved_at = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, state.getGameStateName());
            statement.setInt(2, state.getCurrentMapNumber());
            statement.setDate(3, state.getSavedAt());
            statement.setInt(4, state.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public GameState get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT game_state_name, current_map_number, saved_at FROM game_state WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            GameState gameState = new GameState(resultSet.getString(1), resultSet.getDate(2), resultSet.getInt(3));
            gameState.setId(id);
            return gameState;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GameState> getAll() {
        return null;
    }
}
