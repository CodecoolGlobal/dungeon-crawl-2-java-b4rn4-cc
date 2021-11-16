package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell, 1, 2);
    }


    public void setDirection(){

    }

    @Override
    public String getTileName() {
        return "skeleton";
    }


}
