package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Weapon extends Item{
    private final int damage;
    private final int crit;
    private final String name;
    private Cell cell;

    public Weapon(Cell cell, String name, int damage, int crit){
        super(cell, true);
        this.damage = damage;
        this.crit = crit;
        this.name = name;
        this.cell = cell;
    }

    @Override
    public String getTileName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getCrit() {
        return crit;
    }

    @Override
    public Cell getCell() {
        return cell;
    }

    public String getName(){
        return name;
    }
}
