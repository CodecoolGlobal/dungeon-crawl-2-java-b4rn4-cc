package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.*;

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

    public abstract void act(GameMap map, int index);

    public abstract void setDirection(Player player);

    protected void combat(Cell nextCell){
        int damage = getDamage();
        if (isEnemyImmortal(nextCell)){
            return;
        }
        nextCell.getActor().getHit(damage);
    }

    private boolean isEnemyImmortal(Cell nextCell){
        if (nextCell.getActor() instanceof ImmortalSkeleton){
            return ((ImmortalSkeleton) nextCell.getActor()).isImmortal();
        }
        return false;
    }


    protected int getDamage(){
         return r.nextInt(MAX_DAMAGE + 1 - MIN_DAMAGE) + MIN_DAMAGE;
    }


    protected boolean collisionWithEnemy(Cell nextCell){
        if (nextCell.getActor() != null){
            return (this instanceof Monster && nextCell.getActor() instanceof Player) || this instanceof Player;
        }
        return false;
    }


    protected void move(Cell nextCell){
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    protected boolean canMove(Cell nextCell){
        return nextCell.getType() != CellType.WALL && nextCell.getActor() == null;
    }


    protected Cell  getNextCell(){
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
            case NONE:
                return null;
        }
        return cell.getNeighbor(dx, dy);
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
