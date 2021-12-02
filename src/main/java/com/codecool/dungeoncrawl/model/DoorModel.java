package com.codecool.dungeoncrawl.model;


import com.codecool.dungeoncrawl.logic.items.Door;

public class DoorModel extends BaseModel {
    private int x;
    private int y;
    private boolean isOpen;
    private int mapId;

    public DoorModel(int x, int y, boolean isOpen, int mapId) {
        this.x = x;
        this.y = y;
        this.isOpen = isOpen;
        this.mapId = mapId;
    }

    public DoorModel(Door door, boolean isOpen, int mapId) {
        this.x = door.getCell().getX();
        this.y = door.getCell().getY();
        this.isOpen = isOpen;
        this.mapId = mapId;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public int getMapId() {
        return mapId;
    }
}
