package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;

public class Player extends Actor {
    private ArrayList<Item> inventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell);
    }

    public void setInventory(Item item){
        inventory.add(item);
    }

    public void pickUp(){
        if (getCell().getItem() != null){
            setInventory(getCell().getItem());
        }
    }

    public String getTileName() {
        return "player";
    }
}
