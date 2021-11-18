package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;

public class FastSkeleton extends Monster{
    public FastSkeleton(Cell cell) {
        super(cell, 15, 2, 3, 8);
    }

    @Override
    public String getTileName() {
        return "Fast Boi";
    }


    public void act(GameMap map, int index){
        if (isPlayerInRange(map.getPlayer(), getX(), getY())){
            monsterAct(map, index);
        }
        monsterAct(map, index);
    }

    @Override
    protected void SetAlternativeDir() {
        int index = r.nextInt(4);
        direction = Direction.values()[index];
    }
}
