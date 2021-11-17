package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Direction;

public class Skeleton extends Monster {
    private Direction vertical;
    private Direction horizontal;
    private int aggroRange = 5;
    public Skeleton(Cell cell) {
        super(cell, 10, 1, 2);
    }


    public void setDirection(Player player){
        int xDiff = Math.abs(getX() - player.getX());
        int yDiff = Math.abs(getY() - player.getY());

        if (xDiff > aggroRange || yDiff > aggroRange){
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
        return "skeleton";
    }


}
