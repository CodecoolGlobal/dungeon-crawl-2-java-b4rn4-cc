package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.inventory.Inventory;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.Objects;

public class Player extends Actor {
    private int damage = 10;

    private Inventory inventory = new Inventory();

    public Player(Cell cell) {
        super(cell);
    }

    public void setInventory(Item item){
        if (Objects.equals(item.getTileName(), "key")){
            inventory.setKeys((Key) item);
        }else if (item.getTileName() == "weapon"){
            inventory.setWeapons((Weapon) item);
        }else if(item.getTileName() == "shield"){
            inventory.setShields((Shield) item);
        }else if(item.getTileName() == "potion"){
            inventory.setPotions((Potion) item);
        }
    }

    public void pickUp() {
        setInventory(getCell().getItem());
        getCell().setItem(null);
    }


    @Override
    public String toString() {
        String result = "";
        return result;
    }

    public String getTileName() {
        return "player";
    }
}
