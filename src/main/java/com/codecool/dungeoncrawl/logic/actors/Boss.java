package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Boss extends Monster{
    public Boss(Cell cell) {
        super(cell, 200, 10, 20, 10);
    }


    @Override
    public String getTileName() {
        return "boss";
    }

    @Override
    public void setDirection(Player player) {

    }

    public void act(GameMap map, int index){}
}
