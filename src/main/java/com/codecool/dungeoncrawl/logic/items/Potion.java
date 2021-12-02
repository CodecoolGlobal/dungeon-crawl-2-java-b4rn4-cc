package com.codecool.dungeoncrawl.logic.items;


import com.codecool.dungeoncrawl.logic.Cell;

public class Potion extends Item{
    public Potion(Cell cell, boolean packable) {
        super(cell, packable);
    }

    @Override
    public String getTileName() {
        return "Potion";
    }

    @Override
    public char getTileCharacter() { return 'p'; }

}
