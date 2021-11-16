package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.HashMap;

public class Player extends Actor {
    private HashMap<Item, Integer> inventory = new HashMap<>();

    public Player(Cell cell) {
        super(cell);
    }

    public void setInventory(Item item){
        inventory.put(item, 1);
    }

    public void pickUp() {
        setInventory(getCell().getItem());
        getCell().setItem(null);
    }

    public String getTileName() {
        return "player";
    }
}
