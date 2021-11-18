package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.inventory.Inventory;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.Objects;

public class Player extends Actor {
    private Inventory inventory = new Inventory();
    private GameMap firstLevel;
    private GameMap secondLevel;
    private GameMap thirdLevel;
    private boolean invalidMove = false;
    private int freeze = 0;
    private String combatLog = "\n";

    public String getCombatLog() {
        return combatLog;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int count) {
        this.freeze += count;
    }

    public Player(Cell cell) {
        super(cell, 10, 3, 7, 10);
    }


    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void setDirection(Player player){}

    public void setInventory(Item item){
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void putItemInInventory(Item item){
        if (item.getTileName().equals("key")){
            inventory.setKeys((Key) item);
        }else if (item.getTileName().equals("weapon")){
            inventory.setWeapons((Weapon) item);
        }else if(item.getTileName().equals("shield")){
            inventory.setShields((Shield) item);
        }else if(item.getTileName().equals("potion")){
            inventory.setPotions((Potion) item);
        }
    }

    public boolean consumeItem(String item){
        if (item.equals("freeze")){
            if (inventory.getConsumable("freeze") > 0){
                inventory.setConsumable("freeze");
                return true;
            }
        } else if (item.equals("potion")){
            if (inventory.getConsumable("potion") > 0){
                inventory.setConsumable("potion");
                return true;
            }
        }
        return false;
    }

    public void pickUp() {
        putItemInInventory(getCell().getItem());
        addToCombatLog(String.format("Player picked up a %s", getCell().getItem().getTileName()));
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
        result += String.format("potions: %s\n", inventory.getConsumable("potion"));
        result += String.format("freeze: %s\n", inventory.getConsumable("freeze"));
//        result += inventory.getPotions().size();
        return result;
    }

    public void addToCombatLog(Actor p1, Actor p2, int damage, boolean isCrit){
        if (isCrit){
            combatLog += String.format("%s strikes %s Crit %s dmg\n",p1.getTileName().charAt(0), p2.getTileName().charAt(0), damage);
        } else {
            combatLog += String.format("%s strikes %s for %s dmg\n",p1.getTileName().charAt(0), p2.getTileName().charAt(0), damage);
        }
    }

    public void addToCombatLog(String msg){
        combatLog += msg + "\n";
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
            combat(nextCell, this);
        } else if (canMove(nextCell)){
            move(nextCell);
        } else {
            invalidMove = true;
        }
    }

    @Override
    public int getDamage(){
        int bonusDamage = getBonusDamage();
        return r.nextInt(MAX_DAMAGE + 1 + bonusDamage - MIN_DAMAGE - bonusDamage) + MIN_DAMAGE + bonusDamage;
    }

    private int getBonusDamage() {
        if (inventory.getWeapons() != null){
            return inventory.getWeapons().getDamage();
        }
        return 0;
    }

    @Override
    protected int getBonusCrit(){
        if (inventory.getWeapons() != null){
            return inventory.getWeapons().getCrit();
        }
        return 0;
    }

    public int getShieldDefense(){
        if (inventory.getShields() != null){
            return inventory.getShields().getFlatDefense();
        }
        return 0;
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
