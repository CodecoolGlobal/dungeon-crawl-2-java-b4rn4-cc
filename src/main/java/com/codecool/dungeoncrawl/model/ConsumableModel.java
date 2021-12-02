package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.items.Item;

public class ConsumableModel extends BaseModel {
    private String consumableType;
    private int x;
    private int y;
    private int mapId;

    public ConsumableModel(String consumableType, int x, int y, int mapId) {
        this.consumableType = consumableType;
        this.x = x;
        this.y = y;
        this.mapId = mapId;
    }

    public ConsumableModel (Item consumable, int mapId) {
        this.consumableType = consumable.getTileName();
        this.x = consumable.getCell().getX();
        this.y = consumable.getCell().getY();
        this.mapId = mapId;
    }

    public String getConsumableType() {
        return consumableType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMapId() {
        return mapId;
    }

    public void setConsumableType(String consumableType) {
        this.consumableType = consumableType;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
