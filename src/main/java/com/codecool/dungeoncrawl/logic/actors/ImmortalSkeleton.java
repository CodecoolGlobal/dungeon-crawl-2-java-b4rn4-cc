package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class ImmortalSkeleton extends Actor{
    public ImmortalSkeleton(Cell cell) {
        super(cell, 5, 7, 8);
    }

    @Override
    public String getTileName() {
        return "immortalSkeleton";
    }
}
