package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell, 10, 1, 2);
        direction = Direction.EAST;
    }


    public void setDirection(){

    }

    @Override
    public String getTileName() {
        return "skeleton";
    }


}
