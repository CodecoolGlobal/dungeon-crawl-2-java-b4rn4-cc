package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class ImmortalSkeleton extends Monster{
    private int counter = 0;

    public ImmortalSkeleton(Cell cell) {
        super(cell, 5, 7, 8, 2);
    }

    @Override
    public String getTileName() {
        if (isImmortal()) {
            return "ImmortalSkeletonOn";
        } else {
            return "ImmortalSkeletonOff";
        }

    }

    public boolean isImmortal() {
        return counter % 3 == 0;
    }


    public void act(GameMap map, int index){
        proceedCounter();
        monsterAct(map, index);
    }

    public void proceedCounter(){
        counter++;
    }
}
