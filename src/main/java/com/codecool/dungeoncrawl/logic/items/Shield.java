package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Shield extends Item{
    public Shield(Cell cell){
        super(cell, true);
    }

    public int getFlatDefense(){
        return 3;
    }

    @Override
    public String getTileName() {
        return "shield";
    }
}
