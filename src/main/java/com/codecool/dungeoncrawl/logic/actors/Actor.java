package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.Drawable;
import java.util.Random;

public abstract class Actor implements Drawable {
    protected Cell cell;
    protected static final Random r = new Random();
    protected Direction direction;
    private int health = 10;
    protected final int MAX_DAMAGE;
    protected final int MIN_DAMAGE;


    public Actor(Cell cell, int health, int minDmg, int maxDmg) {
        this.cell = cell;
        this.cell.setActor(this);
        this.health = health;
        this.MAX_DAMAGE = maxDmg;
        this.MIN_DAMAGE = minDmg;
    }

    public abstract void setDirection();

    public void act() {
        Cell nextCell = getNextCell();
        if (collisionWithEnemy(nextCell)){
            combat(nextCell);
        } else {
            move(0, 1);
        }
    }

    private Cell  getNextCell(){
        int dx = 0;
        int dy = 0;
        switch (direction){
        case EAST:
            dx = -1;
            break;
        case WEST:
            dx = 1;
            break;
        case NORTH:
            dy = -1;
            break;
        case SOUTH:
            dy = 1;
            break;
        }
        return cell.getNeighbor(dx, dy);
    }


    private void combat(Cell nextCell){
        int damage = getDamage();
        nextCell.getActor().getHit(damage);
    }


    public int getDamage(){
        return r.nextInt(MAX_DAMAGE + 1 - MIN_DAMAGE) + MIN_DAMAGE;
    }


    private boolean collisionWithEnemy(Cell nextCell){
        if (nextCell.getActor() != null){
            return (this instanceof Skeleton && nextCell.getActor() instanceof Player) || this instanceof Player;
        }
        return false;
    }


    public void move(int dx, int dy){
        Cell nextCell = getNextCell();
        if (nextCell.getType() != CellType.WALL && nextCell.getActor() == null) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }


    public int getHealth() {
        return health;
    }

    public void getHit(int damage){
        health -= damage;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}
