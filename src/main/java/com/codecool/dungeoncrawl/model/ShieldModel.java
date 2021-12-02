package com.codecool.dungeoncrawl.model;

public class ShieldModel {
    private int x;
    private int y;
    private int defense;
    private String name;
    private int inventory_id;
    private int map_id;

    public ShieldModel(int x, int y, String name, int defense, int inventory_id, int map_id) {
        this.x = x;
        this.y = y;
        this.defense = defense;
        this.name = name;
        this.inventory_id = inventory_id;
        this.map_id = map_id;
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

    public int getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

    public int getMap_id() {
        return map_id;
    }

    public void setMap_id(int map_id) {
        this.map_id = map_id;
    }
}
