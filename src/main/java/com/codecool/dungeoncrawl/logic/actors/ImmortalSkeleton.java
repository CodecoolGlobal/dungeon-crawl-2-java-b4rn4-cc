package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class ImmortalSkeleton extends Monster{
    public ImmortalSkeleton(Cell cell) {
        super(cell, 5, 7, 8);
    }

    @Override
    public String getTileName() {
        return "immortalSkeleton";
    }

    @Override
    public void setDirection(Player player) {

    }
}
