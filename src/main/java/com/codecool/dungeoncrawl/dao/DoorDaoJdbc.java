package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.DoorModel;

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
            String sql = "INSERT INTO door (x, y, is_open, map_id) VALUES (?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, door.getX());
            st.setInt(2, door.getY());
            st.setBoolean(3, door.isOpen());
            st.setInt(3, door.getMapId());
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
            st.setInt(3, door.getMapId());
            st.setInt(4, door.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DoorModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT x, y, is_open, map_id FROM door WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                DoorModel map = new DoorModel(rs.getInt(0), (rs.getInt(1)), (rs.getBoolean(2)), rs.getInt(3));
                map.setId(id);
                return map;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
