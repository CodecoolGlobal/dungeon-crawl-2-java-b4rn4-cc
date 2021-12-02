package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Weapon extends Item{
    private final int damage;
    private final int crit;
    private final String name;

    public Weapon(Cell cell, String name, int damage, int crit){
        super(cell, true);
        this.damage = damage;
        this.crit = crit;
        this.name = name;
    }

    @Override
    public String getTileName() {
        return name;
    }

    @Override
    public char getTileCharacter() { return 'w'; } // return 'X'


    public int getDamage() {
        return damage;
    }

    public int getCrit() {
        return crit;
    }


    public String getName(){
        return name;
    }
}
