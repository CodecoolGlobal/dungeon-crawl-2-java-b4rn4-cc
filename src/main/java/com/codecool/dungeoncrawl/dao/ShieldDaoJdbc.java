package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import com.codecool.dungeoncrawl.model.ShieldModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class ShieldDaoJdbc implements ShieldDao {
    private DataSource dataSource;

    public ShieldDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ShieldModel shield) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO weapon (id, x, y, defens, name, map_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(2, shield.getX());
            st.setInt(3, shield.getY());
            st.setInt(4, shield.getDefense());
            st.setString(5, shield.getName());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding new Shield.", throwables);
        }
    }

    @Override
    public void update(ShieldModel shield) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE shield SET x = ?, y = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, shield.getX());
            st.setInt(2, shield.getY());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ShieldModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT x, y, name, defense FROM shield WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(!rs.next()){
                return null;
            }
            ShieldModel shield = new ShieldModel(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4));
            return shield;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ShieldModel> getAll() {
        return null;
    }
}
