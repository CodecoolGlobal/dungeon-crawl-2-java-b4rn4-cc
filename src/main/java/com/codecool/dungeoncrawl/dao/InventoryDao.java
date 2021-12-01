package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.InventoryModel;

public interface InventoryDao {
    void add(InventoryModel inventory);
    void update(InventoryModel inventory);
    InventoryModel get(int inventoryId);
    void delete(int inventoryId);
}
