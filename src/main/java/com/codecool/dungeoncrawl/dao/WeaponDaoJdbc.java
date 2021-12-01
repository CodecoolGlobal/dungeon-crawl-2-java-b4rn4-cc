package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.Weapon;
import com.codecool.dungeoncrawl.model.WeaponModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class WeaponDaoJdbc implements WeaponDao{
    private DataSource dataSource;

    public WeaponDaoJdbc(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void add(WeaponModel weapon) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "INSERT INTO weapon (x, y, damage, crit, name, inventory_id, map_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, weapon.getX());
            st.setInt(2, weapon.getY());
            st.setInt(3, weapon.getDamage());
            st.setInt(4, weapon.getCrit());
            st.setString(5, weapon.getName());
            st.setInt(6, weapon.getInventoryId());
            st.setInt(7, weapon.getMapId());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            weapon.setId(rs.getInt(1));
        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding new Weapon.", throwables);
        }
    }

    @Override
    public void update(WeaponModel weapon) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE weapon SET x = ?, y = ?,  damage = ?, crit = ?, inventory_id = ?, map_id = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, weapon.getX());
            st.setInt(2, weapon.getY());
            st.setInt(3, weapon.getDamage());
            st.setInt(4, weapon.getCrit());
            st.setString(5, weapon.getName());
            st.setInt(6, weapon.getInventoryId());
            st.setInt(7, weapon.getMapId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WeaponModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT x, y, damage, crit, name, inventory_id, map_id FROM weapon WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(!rs.next()){
                return null;
            }
            WeaponModel weapon = new WeaponModel(rs.getInt(1), rs.getInt(2), rs.getString(5),
                    rs.getInt(3), rs.getInt(4), rs.getInt(6), rs.getInt(7));
            return weapon;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WeaponModel> getAll() {
        return null;
    }
}
