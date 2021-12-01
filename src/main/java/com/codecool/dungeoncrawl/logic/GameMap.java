package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.monster.Boss;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Door;

import java.util.LinkedList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private final List<Cell> monsterCells = new LinkedList<>();
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

    public void removeFire(){
        for (int row = 0; row < width; row++){
            for (int col = 0; col < height; col++){
                if (cells[row][col].getType() == CellType.FIRE){
                    cells[row][col].setType(CellType.FLOOR);
                }
            }
        }
    }

    public List<Cell> getMonsterCells() {
        return monsterCells;
    }

    public void removeMonsterCells(Cell cell){
        monsterCells.remove(cell);
    }

    public void updateMonsterCells(int index, Cell nextCell){
        monsterCells.set(index, nextCell);
    }

    public void addMonsterCell(Cell monsterCell){
        monsterCells.add(monsterCell);
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

    public void mortalizeBoss(){
        for (Cell cell : monsterCells){
            if (cell.getActor() instanceof Boss){
                ((Boss) cell.getActor()).mortalize();
            }
        }
    }

    public String createCurrentMap(){
        String currentMap = String.format("%s %s", width, height);
        for (int row = 0; row < height; row++){
            currentMap += "\n";
            for (int col = 0; col < width; col++){
                Cell cell = getCell(col, row);
                if (cell.getActor() != null){
                    currentMap += cell.getActor().getTileCharacter();
                    continue;
                }
                currentMap += cell.getTileCharacter();
            }
        }
        return currentMap;
    }

}
