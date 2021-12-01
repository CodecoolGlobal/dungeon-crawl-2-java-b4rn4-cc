package com.codecool.dungeoncrawl.model;

public class ShieldModel {
    private int x;
    private int y;
    private int defense;
    private String name;

    public ShieldModel(int x, int y, int defense, String name) {
        this.x = x;
        this.y = y;
        this.defense = defense;
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
}
