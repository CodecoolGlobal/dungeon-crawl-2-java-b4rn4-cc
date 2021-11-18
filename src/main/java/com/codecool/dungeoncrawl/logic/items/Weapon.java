package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Weapon extends Item{

    public Weapon(Cell cell){
        super(cell, true);
    }

    @Override
    public String getTileName() {
        return "Weapon";
    }

    public int getDamage() {
        return 10;
    }

    public int getCrit() {
        return 10;
    }
}
