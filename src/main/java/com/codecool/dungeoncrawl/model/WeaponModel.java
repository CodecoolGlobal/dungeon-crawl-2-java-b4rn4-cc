package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.items.Weapon;

public class WeaponModel extends BaseModel {
    private int x;
    private int y;
    private int damage;
    private int crit;
    private String name;
    private int inventoryId;
    private int mapId;


    public WeaponModel(int x, int y, String name, int damage, int crit, int inventoryId, int mapId) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.crit = crit;
        this.name = name;
        this.inventoryId = inventoryId;
        this.mapId = mapId;
    }

    public WeaponModel(Weapon weapon, int inventoryId, int mapId) {
        if(weapon.getCell() != null) {
            this.x = weapon.getCell().getX();
            this.y = weapon.getCell().getY();
        } else {
            this.x = -1;
            this.y = -1;
        }
        this.damage = weapon.getDamage();
        this.crit = weapon.getCrit();
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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getCrit() {
        return crit;
    }

    public void setCrit(int crit) {
        this.crit = crit;
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
