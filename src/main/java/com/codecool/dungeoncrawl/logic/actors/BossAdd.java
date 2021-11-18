package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class BossAdd extends Monster{
    public BossAdd(Cell cell) {
        super(cell, 7, 2, 3, 10);
    }

    @Override
    public void act(GameMap map, int index) {
        monsterAct(map, index);
    }

    @Override
    public String getTileName() {
        return "Boss Add";
    }


}
