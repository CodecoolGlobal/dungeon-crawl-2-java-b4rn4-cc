package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.BaseModel;
import com.codecool.dungeoncrawl.model.InventoryModel;

import javax.sql.DataSource;
import java.sql.*;

public class InventoryDaoJdbc implements InventoryDao{

    private DataSource dataSource;

    public InventoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(InventoryModel inventory, int weapon_id, int shield_id, int player_id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO inventory (key, health_potion, freeze_potion, weapon_id, shield_id, player_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setBoolean(1, inventory.isKey());
            statement.setInt(2, inventory.getPotion());
            statement.setInt(3, inventory.getFreeze());
            statement.setInt(4, weapon_id);
            statement.setInt(5, shield_id);
            statement.setInt(6, player_id);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            inventory.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(InventoryModel inventory, int weapon_id, int shield_id, int player_id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE inventory SET key = ?, health_potion = ?, freeze_potion = ?, weapon_id = ?, shield_id = ?, player_id = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setBoolean(1, inventory.isKey());
            statement.setInt(2, inventory.getPotion());
            statement.setInt(3, inventory.getFreeze());
            statement.setInt(4, weapon_id);
            statement.setInt(5, shield_id);
            statement.setInt(6, player_id);
            statement.setInt(7, inventory.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InventoryModel get(int inventoryId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT key, health_potion, freeze_potion FROM inventory WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, inventoryId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            } else {
                InventoryModel inventory = new InventoryModel(resultSet.getBoolean(1), resultSet.getInt(2), resultSet.getInt(3));
                inventory.setId(inventoryId);
                return inventory;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int inventoryId) {

    }
}
