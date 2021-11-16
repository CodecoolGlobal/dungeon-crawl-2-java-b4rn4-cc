package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;

public class Player extends Actor {
    private String route;
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
