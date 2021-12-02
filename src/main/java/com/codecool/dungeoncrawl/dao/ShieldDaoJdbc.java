package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ConsumableModel;
import com.codecool.dungeoncrawl.model.ShieldModel;
import com.codecool.dungeoncrawl.model.WeaponModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShieldDaoJdbc implements ShieldDao {
    private DataSource dataSource;

    public ShieldDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ShieldModel shield) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO shield (x, y, defense, name, inventory_id, map_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, shield.getX());
            st.setInt(2, shield.getY());
            st.setInt(3, shield.getDefense());
            st.setString(4, shield.getName());

            if (shield.getInventoryId() == -1){
                st.setNull(5, Types.NULL);
            } else {
                st.setInt(5, shield.getInventoryId());
            }

            if (shield.getMapId() == -1){
                st.setNull(6, Types.NULL);
            } else {
                st.setInt(6, shield.getMapId());
            }

            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            shield.setId(rs.getInt(1));
        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding new Shield.", throwables);
        }
    }

    @Override
    public void update(ShieldModel shield) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE shield SET x = ?, y = ?, defense = ?, name = ?, inventory_id = ?, map_id = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, shield.getX());
            st.setInt(2, shield.getY());
            st.setInt(3, shield.getDefense());
            st.setString(4, shield.getName());
            st.setInt(5, shield.getInventoryId());
            st.setInt(6, shield.getMapId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ShieldModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT x, y, name, defense, inventory_id, map_id FROM shield WHERE (inventory_id = ? AND x IS NULL) OR (map_id = ? AND x NOTNULL)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.setInt(2, id);
            ResultSet rs = st.executeQuery();
            if(!rs.next()){
                return null;
            }
            ShieldModel shield = new ShieldModel(rs.getInt(1), rs.getInt(2), rs.getString(3),
                    rs.getInt(4), rs.getInt(5), rs.getInt(6));
            return shield;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ShieldModel> getAll(int mapId) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT id, x, y, defense, name, inventory_id, map_id FROM shield WHERE map_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, mapId);
            ResultSet resultSet = statement.executeQuery();

            List<ShieldModel> results = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int x = resultSet.getInt(2);
                int y = resultSet.getInt(3);
                int defense = resultSet.getInt(4);
                String name = resultSet.getString(5);
                int weaponInventoryId = resultSet.getInt(6);
                int weaponMapId = resultSet.getInt(7);
                // get map by mapId
                // MapModel map = mapModelDao.get(consumableMapId);

                ShieldModel shieldModel = new ShieldModel(x, y, name, defense, weaponInventoryId, weaponMapId);
                shieldModel.setId(id);
                results.add(shieldModel);
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
