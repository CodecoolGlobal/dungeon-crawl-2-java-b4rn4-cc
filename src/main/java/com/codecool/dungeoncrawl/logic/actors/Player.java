package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.HashMap;

public class Player extends Actor {
    private int bonusDamage;
    private HashMap<Item, Integer> inventory = new HashMap<>();

    public Player(Cell cell) {
        super(cell, 3, 7);
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


    @Override
    public int getDamage(){
        setBonusDamage();
        return r.nextInt(MAX_DAMAGE + bonusDamage - MIN_DAMAGE + bonusDamage) + MAX_DAMAGE + bonusDamage;
    }

    public void setBonusDamage() {
        this.bonusDamage = 0;
    }

}
