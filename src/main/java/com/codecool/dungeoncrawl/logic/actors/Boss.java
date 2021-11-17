package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Boss extends Monster{
    public Boss(Cell cell) {
        super(cell, 200, 10, 20);
    }


    @Override
    public String getTileName() {
        return "boss";
    }

    @Override
    public void setDirection(Player player) {

    }
}
