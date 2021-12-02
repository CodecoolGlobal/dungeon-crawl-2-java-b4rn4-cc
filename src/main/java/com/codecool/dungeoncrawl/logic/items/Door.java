package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Door extends Item{
    private final boolean opened;
    private final String mapTo;

    public Door(Cell cell, String mapTo) {
        super(cell, false);
        this.opened = cell.getType() == CellType.FLOOR;
        this.mapTo = mapTo;
    }

    public Door (Cell cell, boolean opened, String mapTo) {
        super(cell, false);
        if (opened){
            cell.setType(CellType.FLOOR);
        }
        this.opened = opened;
        this.mapTo = mapTo;
    }

    @Override
    public String getTileName() {
        if (opened) {
            return "openedDoor";
        }
        return "closedDoor";
    }

    @Override
    public char getTileCharacter() { return 'c'; } // return 'o'


    public String getMapTo() {
        return mapTo;
    }
}
