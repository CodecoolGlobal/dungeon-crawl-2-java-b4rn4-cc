package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Potion extends Item{
    public Potion(Cell cell){
        super(cell, true);
    }

    @Override
    public String getTileName() {
        return "potion";
    }
}
