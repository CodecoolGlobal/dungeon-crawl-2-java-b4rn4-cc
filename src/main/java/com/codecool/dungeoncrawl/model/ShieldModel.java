package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.items.Shield;

public class ShieldModel extends BaseModel{
    private int x;
    private int y;
    private int defense;
    private String name;
    private int inventoryId;
    private int mapId;

    public ShieldModel(int x, int y, String name, int defense, int inventoryId, int mapId) {
        this.x = x;
        this.y = y;
        this.defense = defense;
        this.name = name;
        this.inventoryId = inventoryId;
        this.mapId = mapId;
    }

    public ShieldModel(Shield shield, int inventoryId, int mapId) {
        if(shield.getCell() != null) {
            this.x = shield.getCell().getX();
            this.y = shield.getCell().getY();
        } else {
            this.x = -1;
            this.y = -1;
        }
        this.defense = shield.getFlatDefense();
        this.name = getName();
        this.inventoryId = inventoryId;
        this.mapId = mapId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
