package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell, 10 , 5, 10);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
