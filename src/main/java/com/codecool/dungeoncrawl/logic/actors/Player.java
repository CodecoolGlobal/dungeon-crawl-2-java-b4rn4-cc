package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.HashMap;

public class Player extends Actor {
    private HashMap<Item, Integer> inventory = new HashMap<>();
    private String route;

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

    @Override
    public String toString() {
        String result = "";
        for (Item key: inventory.keySet()){
            result += key.getTileName();
            result += ": 1,\n";
        }
        return result;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }

    public String getTileName() {
        return "player";
    }
}
