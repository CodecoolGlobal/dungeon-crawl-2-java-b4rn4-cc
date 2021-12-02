package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.DoorModel;
import com.codecool.dungeoncrawl.model.MapModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoorDaoJdbc implements DoorDao {
    private DataSource dataSource;

    public DoorDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(DoorModel door) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO door (x, y, is_open, map_id, map_to) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, door.getX());
            st.setInt(2, door.getY());
            st.setBoolean(3, door.isOpen());
            st.setInt(4, door.getMapId());
            st.setString(5, door.getMapTo());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            door.setId(rs.getInt(1));

        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding new door.", throwables);
        }
    }

    @Override
    public void update(DoorModel door) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE door SET x = ?, y = ?, is_open = ?, map_id = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, door.getX());
            st.setInt(2, door.getY());
            st.setBoolean(3, door.isOpen());
            st.setInt(4, door.getMapId());
            st.setInt(5, door.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DoorModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT x, y, map_to, is_open, map_id FROM door WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                DoorModel map = new DoorModel(rs.getInt(1), (rs.getInt(2)), (rs.getBoolean(4)), rs.getInt(5), rs.getString(3));
                map.setId(id);
                return map;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DoorModel> getAll(int gameStateId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, x, y, map_to, is_open, map_id FROM door WHERE map_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, gameStateId);
            ResultSet rs = st.executeQuery();
            List<DoorModel> result = new ArrayList<>();
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                DoorModel door = new DoorModel(rs.getInt(2), rs.getInt(3), rs.getBoolean(5), rs.getInt(6), rs.getString(4));
                door.setId(rs.getInt(1));
                result.add(door);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all door", e);
        }
    }

}
