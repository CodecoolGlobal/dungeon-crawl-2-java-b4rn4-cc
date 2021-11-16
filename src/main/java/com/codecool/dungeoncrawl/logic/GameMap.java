package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Door;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private Door nextDoor;
    private Door prevDoor;

    private Player player;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setNextDoor(Door nextDoor) {
        this.nextDoor = nextDoor;
    }

    public void setPrevDoor(Door prevDoor) {
        this.prevDoor = prevDoor;
    }

    public Door getNextDoor() {
        return nextDoor;
    }

    public Door getPrevDoor() {
        return prevDoor;
    }
}
