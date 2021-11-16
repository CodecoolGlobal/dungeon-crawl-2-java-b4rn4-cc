package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Door extends Item{
    private final boolean opened;
    private final String mapTo;

    public Door(Cell cell, String mapTo) {
        super(cell);
        this.opened = cell.getType() == CellType.FLOOR;
        this.mapTo = mapTo;
    }

    @Override
    public String getTileName() {
        if (opened) {
            return "openedDoor";
        }
        return "closedDoor";
    }

    public String getMapTo() {
        return mapTo;
    }
}
