package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Item{
    public Key(Cell cell){
        super(cell, true);
    }

    @Override
    public String getTileName() {
        return "Key";
    }

    @Override
    public char getTileCharacter() { return 'k'; }

}
