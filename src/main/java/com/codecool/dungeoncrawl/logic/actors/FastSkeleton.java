package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class FastSkeleton extends Monster{
    public FastSkeleton(Cell cell) {
        super(cell, 15, 2, 3, 8);
    }

    @Override
    public String getTileName() {
        return "fastSkeleton";
    }


    public void act(GameMap map, int index){
        act2(map, index);
        act2(map, index);
    }
}
