package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.InventoryModel;

public interface InventoryDao {
    void add(InventoryModel inventory, int weapon_id, int shield_id, int player_id);
    void update(InventoryModel inventory, int weapon_id, int shield_id, int player_id);
    InventoryModel get(int inventoryId);
    void delete(int inventoryId);
}
