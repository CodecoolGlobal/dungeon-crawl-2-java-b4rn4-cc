package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Skeleton extends Monster {
    public Skeleton(Cell cell) {
        super(cell, 10, 1, 2, 5);
    }


    @Override
    public String getTileName() {
        return "skeleton";
    }

    public void act(GameMap map, int index){
        act2(map, index);
    }


}
