package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;

public abstract class Monster extends Actor{
    protected final int aggroRange;
    private Direction vertical;
    private Direction horizontal;

    public Monster(Cell cell, int health, int minDmg, int maxDmg, int aggroRange) {
        super(cell, health, minDmg, maxDmg, 5);
        this.aggroRange = aggroRange;
    }

    public abstract void act(GameMap map, int index);

    public void act2(GameMap map, int index) {
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


    public void setDirection(Player player){
        int xDiff = Math.abs(getX() - player.getX());
        int yDiff = Math.abs(getY() - player.getY());

        if (xDiff > aggroRange + 1 || yDiff > aggroRange + 1){
            direction = Direction.NONE;
            return;
        }

        setVectors(player);

        if (xDiff == yDiff){
            int n = r.nextInt(2);
            if (n == 0){
                direction = vertical;
            } else {
                direction = horizontal;
            }
        } else if (xDiff > yDiff){
            direction = horizontal;
            if (getNextCell().getType() == CellType.WALL){
                direction = vertical;
            }
        } else {
            direction = vertical;
            if (getNextCell().getType() == CellType.WALL){
                direction = horizontal;
            }
        }

    }

    private void setVectors(Player player){
        if (player.getX() > getX()){
            horizontal = Direction.WEST;
        } else {
            horizontal = Direction.EAST;
        }
        if (player.getY() > getY()){
            vertical = Direction.SOUTH;
        } else {
            vertical = Direction.NORTH;
        }
    }


    @Override
    public String getTileName() {
        return null;
    }

}
