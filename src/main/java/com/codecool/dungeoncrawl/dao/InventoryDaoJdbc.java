package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.InventoryModel;

import javax.sql.DataSource;
import java.sql.*;

public class InventoryDaoJdbc implements InventoryDao{

    private DataSource dataSource;

    public InventoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(InventoryModel inventory) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO inventory (key, health_potion, freeze_potion, player_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setBoolean(1, inventory.hasKey());
            statement.setInt(2, inventory.getPotion());
            statement.setInt(3, inventory.getFreeze());
            statement.setInt(4, inventory.getPlayerId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            inventory.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(InventoryModel inventory) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE inventory SET key = ?, health_potion = ?, freeze_potion = ?, player_id = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setBoolean(1, inventory.hasKey());
            statement.setInt(2, inventory.getPotion());
            statement.setInt(3, inventory.getFreeze());
            statement.setInt(4, inventory.getPlayerId());
            statement.setInt(5, inventory.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InventoryModel get(int playerId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, key, health_potion, freeze_potion, player_id FROM inventory WHERE player_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, playerId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            } else {
                InventoryModel inventory = new InventoryModel(resultSet.getBoolean(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5));
                inventory.setId(resultSet.getInt(1));
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
