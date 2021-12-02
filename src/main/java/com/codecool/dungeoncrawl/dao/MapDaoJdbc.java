package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.MapModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MapDaoJdbc implements MapDao {
    private DataSource dataSource;

    public MapDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(MapModel map) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO maps (map_number, game_state_id, map) VALUES (?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, map.getMapNumber());
            st.setInt(2, map.getGameStateId());
            st.setString(3, map.getMap());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            map.setId(rs.getInt(1));

        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding new map.", throwables);
        }
    }

    @Override
    public void update(MapModel map) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE maps SET map_number = ?, game_state_id = ?, map = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, map.getMapNumber());
            st.setInt(2, map.getGameStateId());
            st.setString(3, map.getMap());
            st.setInt(4, map.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MapModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT map_number, game_state_id, map FROM maps WHERE game_state_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                MapModel map = new MapModel(rs.getInt(1), (rs.getInt(2)), (rs.getString(3)));
                map.setId(id);
                return map;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public LinkedList<MapModel> getAll(int gameStateId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, map_number, game_state_id, map FROM maps WHERE game_state_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, gameStateId);
            ResultSet rs = st.executeQuery();
            LinkedList<MapModel> result = new LinkedList<>();
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                MapModel map = new MapModel(rs.getInt(2), rs.getInt(3), rs.getString(4));
                map.setId(rs.getInt(1));
                result.add(map);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all map", e);
        }
    }
}
