package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class FastSkeleton extends Actor{
    public FastSkeleton(Cell cell) {
        super(cell, 5, 5, 7);
    }

    @Override
    public String getTileName() {
        return "fastSkeleton";
    }
}
