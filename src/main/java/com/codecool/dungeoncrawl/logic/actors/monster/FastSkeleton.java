package com.codecool.dungeoncrawl.logic.actors.monster;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;

public class FastSkeleton extends Monster{
    public FastSkeleton(Cell cell, int aggroRange) {
        super(cell, 5, 3, 5, aggroRange);
        this.critChance = 33;
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
        int index = Actor.r.nextInt(4);
        direction = Direction.values()[index];
    }
}
