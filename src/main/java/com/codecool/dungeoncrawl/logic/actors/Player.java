package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.inventory.Inventory;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.Objects;

public class Player extends Actor {
    private Inventory inventory = new Inventory();
    private GameMap firstLevel;
    private GameMap secondLevel;
    private GameMap thirdLevel;

    public Player(Cell cell) {
        super(cell);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void putItemInInventory(Item item){
        if (Objects.equals(item.getTileName(), "key")){
            inventory.setKeys((Key) item);
        }else if (item.getTileName().equals("weapon")){
            inventory.setWeapons((Weapon) item);
        }else if(item.getTileName().equals("shield")){
            inventory.setShields((Shield) item);
        }else if(item.getTileName().equals("potion")){
            inventory.setPotions((Potion) item);
        }
    }

    public void pickUp() {
        putItemInInventory(getCell().getItem());
        getCell().setItem(null);
    }

    @Override
    public String toString() {
        String result = "";
        if (inventory.getKeys() != null){
            result += "key: 1\n";
        }
        if (inventory.getWeapons() != null){
            result += "weapon: 1\n";
        }
        if (inventory.getShields() != null){
            result += "shield: 1\n";
        }
        result += "potions: ";
        result += inventory.getPotions().size();
        return result;
    }

    public String getTileName() {
        return "player";
    }

    public boolean hasKey() {
        return inventory.getKeys() != null;
    }

    public GameMap getFirstLevel() {
        return firstLevel;
    }

    public GameMap getSecondLevel() {
        return secondLevel;
    }

    public GameMap getThirdLevel() {
        return thirdLevel;
    }

    public void setFirstLevel(GameMap firstLevel) {
        this.firstLevel = firstLevel;
    }

    public void setSecondLevel(GameMap secondLevel) {
        this.secondLevel = secondLevel;
    }

    public void setThirdLevel(GameMap thirdLevel) {
        this.thirdLevel = thirdLevel;
    }

    public void removeKeyFromInventory() {
        inventory.setKeys(null);
    }
}
