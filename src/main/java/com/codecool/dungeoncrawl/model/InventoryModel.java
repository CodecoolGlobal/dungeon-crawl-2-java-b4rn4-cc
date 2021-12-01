package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.inventory.Inventory;

public class InventoryModel extends BaseModel{
    private boolean key;
    private int freeze;
    private int potion;

    public InventoryModel(Inventory inventory) {
        this.key = inventory.getKeys() != null;
        this.freeze = inventory.getFreezeValue();
        this.potion = inventory.getPotionValue();
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

    public boolean isKey() {
        return key;
    }

    public int getFreeze() {
        return freeze;
    }

    public int getPotion() {
        return potion;
    }
}
