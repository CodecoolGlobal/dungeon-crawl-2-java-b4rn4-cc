package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.inventory.Inventory;

public interface InventoryDao {
    void add(Inventory inventory);
    void update(Inventory inventory);
    void get(int InventoryId);
    void delete(int inventoryId);
}
