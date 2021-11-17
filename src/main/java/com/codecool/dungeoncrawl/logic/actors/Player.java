package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.inventory.Inventory;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.Objects;

public class Player extends Actor {
    private final Inventory inventory = new Inventory();
    private int bonusDamage;
    private boolean invalidMove = false;

    public Player(Cell cell) {
        super(cell, 10, 3, 7);
    }


    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void setDirection(Player player){}

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


    public void act(GameMap map, int index) { }

    public boolean isInvalidMove() {
        if (invalidMove){
            invalidMove = false;
            return true;
        }
        return false;
    }

    public void act() {
        Cell nextCell = getNextCell();
        if (collisionWithEnemy(nextCell)){
            combat(nextCell);
        } else if (canMove(nextCell)){
            move(nextCell);
        } else {
            invalidMove = true;
        }
    }

    @Override
    public int getDamage(){
        setBonusDamage();
        return r.nextInt(MAX_DAMAGE + 1 + bonusDamage - MIN_DAMAGE - bonusDamage) + MIN_DAMAGE + bonusDamage;
    }

    public void setBonusDamage() {
        if (inventory.getWeapons() != null){
            bonusDamage = inventory.getWeapons().getDamage();
        }
    }

}
