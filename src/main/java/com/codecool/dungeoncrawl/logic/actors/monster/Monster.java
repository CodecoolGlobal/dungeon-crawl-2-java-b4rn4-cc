package com.codecool.dungeoncrawl.logic.actors.monster;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;

public abstract class Monster extends Actor {
    protected final int aggroRange;
    private Direction vertical;
    private Direction horizontal;

    public Monster(Cell cell, int health, int minDmg, int maxDmg, int aggroRange) {
        super(cell, health, minDmg, maxDmg, 5);
        this.aggroRange = aggroRange;
    }

    public abstract void act(GameMap map, int index);

    public void monsterAct(GameMap map, int index) {
        int tryMove = 2;
        while (tryMove > 0){
            setDirection(map.getPlayer());
            Cell nextCell = getNextCell();
            if (!willMonsterMove(nextCell)){
                return;
            }
            if (collisionWithEnemy(nextCell)){
                combat(nextCell, map.getPlayer());
                return;
            } else if (canMove(nextCell)){
                move(nextCell, map.getPlayer());
                map.updateMonsterCells(index, nextCell);
                return;
            }
            tryMove--;
        }
    }

    private boolean willMonsterMove(Cell nextCell){
        return nextCell != null;
    }


    public void setDirection(Player player){
        if (!isPlayerInRange(player, getX(), getY())){
            SetAlternativeDir();
        } else {
            setDirToWardPlayer(player, getX(), getY());
        }

    }

    protected void SetAlternativeDir(){
        direction = Direction.NONE;
    }

    private void setDirToWardPlayer(Player player, int x, int y){
        int xDiff = Math.abs(x - player.getX());
        int yDiff = Math.abs(y - player.getY());
        setVectorTowardPlayer(player);
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

    protected boolean isPlayerInRange(Player player, int x, int y){
        int xDiff = Math.abs(x - player.getX());
        int yDiff = Math.abs(y - player.getY());
        return xDiff <= aggroRange + 1 && yDiff <= aggroRange + 1;
    }

    private void setVectorTowardPlayer(Player player){
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
