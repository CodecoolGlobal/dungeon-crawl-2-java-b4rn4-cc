package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public abstract class Monster extends Actor{
    public Monster(Cell cell, int health, int minDmg, int maxDmg) {
        super(cell, health, minDmg, maxDmg);
    }

    public void act(GameMap map, int index) {
        setDirection(map.getPlayer());
        Cell nextCell = getNextCell();
        if (!monsterWillMove(nextCell)){
            return;
        }
        if (collisionWithEnemy(nextCell)){
            combat(nextCell);
        } else if (canMove(nextCell)){
            move(nextCell);
            map.updateMonsterCells(index, nextCell);
        }
    }

    private boolean monsterWillMove(Cell nextCell){
        return nextCell != null;
    }



    @Override
    public String getTileName() {
        return null;
    }

    @Override
    public void setDirection(Player player) {

    }
}
