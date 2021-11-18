package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Skeleton extends Monster {
    private int directionIndex;

    public Skeleton(Cell cell) {
        super(cell, 10, 1, 2, 5);
        int index = r.nextInt(8);
        setValidIndex(index-2);
    }


    @Override
    public String getTileName() {
        return "Skeleton";
    }

    public void act(GameMap map, int index){
        monsterAct(map, index);
    }

    @Override
    protected void SetAlternativeDir() {
        direction = Direction.NONE;
//        setValidIndex(directionIndex + 1);
//        direction = Direction.values()[directionIndex / 2];
    }

    private void setValidIndex(int index){
        if (index < 0){
            index++;
        } else if (index == Direction.values().length * 2 - 2){
            index = 0;
        }
        directionIndex = index;
    }
}
