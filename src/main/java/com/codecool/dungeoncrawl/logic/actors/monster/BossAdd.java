package com.codecool.dungeoncrawl.logic.actors.monster;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class BossAdd extends Monster{
    public BossAdd(Cell cell) {
        super(cell, 15, 4, 7, 10);
        this.critChance = 0;
    }

    @Override
    public void act(GameMap map, int index) {
        monsterAct(map, index);
    }

    @Override
    public String getTileName() {
        return "Spider";
    }


}
