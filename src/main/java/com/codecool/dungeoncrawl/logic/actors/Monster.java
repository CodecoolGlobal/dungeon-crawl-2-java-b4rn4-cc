package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public abstract class Monster extends Actor{
    public Monster(Cell cell, int health, int minDmg, int maxDmg) {
        super(cell, health, minDmg, maxDmg);
    }

    public void act(GameMap map, int index) {
        Cell nextCell = getNextCell();
        if (nextCell == null){ return; }
        if (collisionWithEnemy(nextCell)){
            combat(nextCell);
        } else {
            if (move()){
                map.updateMonsterCells(index, nextCell);
            }
        }
    }



    @Override
    public String getTileName() {
        return null;
    }

    @Override
    public void setDirection(Player player) {

    }
}
