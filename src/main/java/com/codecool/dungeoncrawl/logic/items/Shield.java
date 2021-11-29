package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Shield extends Item{
    private final int defense;
    private final String name;

    public Shield(Cell cell, String name, int defense){
        super(cell, true);
        this.defense = defense;
        this.name = name;
    }

    public int getFlatDefense(){
        return defense;
    }

    @Override
    public String getTileName() {
        return name;
    }
}
