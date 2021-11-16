package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Item implements Drawable {
    private Cell cell;
    private boolean packable;

    public Item(Cell cell, boolean packable){
        this.cell = cell;
        this.cell.setItem(this);
        this.packable = packable;
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

    public boolean isPackable() {
        return packable;
    }
}
