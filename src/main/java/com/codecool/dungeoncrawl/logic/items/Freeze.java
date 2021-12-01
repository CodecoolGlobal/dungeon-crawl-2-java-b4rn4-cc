package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Freeze extends Item{
    public Freeze(Cell cell) {
        super(cell, true);
    }

    @Override
    public String getTileName() {
        return "Freeze spell";
    }

    @Override
    public char getTileCharacter() { return 'F'; }

}
