package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;

public class ImmortalSkeleton extends Monster{
    private int counter = 0;

    public ImmortalSkeleton(Cell cell) {
        super(cell, 5, 7, 8);
    }

    @Override
    public String getTileName() {
        if (isImmortal()) {
            return "immortalSkeletonOn";
        } else {
            return "immortalSkeletonOff";
        }

    }

    public boolean isImmortal() {
        return counter % 3 == 0;
    }

    @Override
    public void setDirection(Player player) {
        direction = Direction.NONE;
        counter++;
    }
}
