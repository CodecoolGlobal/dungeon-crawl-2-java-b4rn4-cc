package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.inventory.Inventory;

public class InventoryModel extends BaseModel{
    private boolean key;
    private int freeze;
    private int potion;
    private int playerId;

    public InventoryModel(Inventory inventory, int playerId) {
        this.key = (inventory.getKeys() != null);
        this.freeze = inventory.getFreezeValue();
        this.potion = inventory.getPotionValue();
        this.playerId = playerId;
    }

    public InventoryModel(boolean key, int freeze, int potion, int playerId) {
        this.key = key;
        this.freeze = freeze;
        this.potion = potion;
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }

    public void setPotion(int potion) {
        this.potion = potion;
    }

    public boolean hasKey() {
        return key;
    }

    public int getFreeze() {
        return freeze;
    }

    public int getPotion() {
        return potion;
    }
}
