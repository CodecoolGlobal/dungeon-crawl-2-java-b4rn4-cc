package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    protected int health;
    protected final int maxDamage;
    protected final int minDamage;

    public Actor(Cell cell, int health, int minDamage, int maxDamage) {
        this.cell = cell;
        this.cell.setActor(this);
        this.health = health;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType() != CellType.WALL) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public int getHealth() {
        return health;
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


    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getMinDamage() {
        return minDamage;
    }
}
