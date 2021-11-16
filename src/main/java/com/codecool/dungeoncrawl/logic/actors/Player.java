package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.HashMap;

public class Player extends Actor {
    private int bonusDamage;
    private HashMap<Item, Integer> inventory = new HashMap<>();

    public Player(Cell cell) {
        super(cell, 10, 3, 7);
    }


    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void setDirection(){}

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

    public String getTileName() {
        return "player";
    }


    @Override
    public int getDamage(){
        setBonusDamage();
        return r.nextInt(MAX_DAMAGE + 1 + bonusDamage - MIN_DAMAGE - bonusDamage) + MIN_DAMAGE + bonusDamage;
    }

    public void setBonusDamage() {
        bonusDamage = 10;
    }

}
