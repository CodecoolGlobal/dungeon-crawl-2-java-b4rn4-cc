package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.Weapon;
import com.codecool.dungeoncrawl.model.ConsumableModel;
import com.codecool.dungeoncrawl.model.WeaponModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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

            if (weapon.getInventoryId() == -1){
                st.setNull(6, Types.NULL);
            } else {
                st.setInt(6, weapon.getInventoryId());
            }

            if (weapon.getMapId() == -1){
                st.setNull(7, Types.NULL);
            } else {
                st.setInt(7, weapon.getMapId());
            }

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
            String sql = "SELECT x, y, damage, crit, name, inventory_id, map_id FROM weapon WHERE (inventory_id = ? AND x IS NULL) OR (map_id = ? AND x NOTNULL)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.setInt(2, id);
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
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT id, x, y, type, map_id FROM weapon WHERE map_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, mapId);
            ResultSet resultSet = statement.executeQuery();

            List<ConsumableModel> results = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int x = resultSet.getInt(2);
                int y = resultSet.getInt(3);
                String type = resultSet.getString(4);
                int consumableMapId = resultSet.getInt(5);
                // get map by mapId
                // MapModel map = mapModelDao.get(consumableMapId);

                ConsumableModel consumableModel = new ConsumableModel(type, x, y,consumableMapId);
                consumableModel.setId(id);
                results.add(consumableModel);
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
