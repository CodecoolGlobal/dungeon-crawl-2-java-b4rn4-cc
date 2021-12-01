package com.codecool.dungeoncrawl.model;

public class WeaponModel extends BaseModel {
    private int x;
    private int y;
    private int damage;
    private int crit;
    private String name;

    public WeaponModel(int x, int y, String name, int damage, int crit) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.crit = crit;
        this.name = name;
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
}
