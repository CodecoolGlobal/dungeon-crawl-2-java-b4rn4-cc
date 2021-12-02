package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.monster.Boss;
import com.codecool.dungeoncrawl.logic.actors.monster.ImmortalSkeleton;
import com.codecool.dungeoncrawl.logic.inventory.Inventory;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.LinkedList;
import java.util.List;


public class Player extends Actor {
    private Inventory inventory = new Inventory();
    private GameMap firstLevel;
    private GameMap secondLevel;
    private GameMap thirdLevel;
    private boolean invalidMove = false;
    private int freeze = 0;
    private List<String> combatLog = new LinkedList<>();

    public String getCombatLog() {
        String str = "";
        for (String line : combatLog){
            str += line + "\n";
        }
        return str;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int count) {
        this.freeze += count;
    }

    public Player(Cell cell) {
        super(cell, 20, 3, 7, 10);
    }


    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void setDirection(Player player){}

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void putItemInInventory(Player player, Item item){
        if (item.getTileName().equals("Key")){
            inventory.setKeys((Key) item);
        }else if (item.getTileName().equals("Frostmourne") || item.getTileName().equals("Shadowmourne") || item.getTileName().equals("Kingslayer")){
            inventory.setWeapons((Weapon) item);
            addToCombatLog(String.format("Player picked up %s!", item.getTileName()));
            return;
        }else if(item.getTileName().equals("Wooden shield") || item.getTileName().equals("Iron shield")){
            inventory.setShields((Shield) item);
        }else if(item.getTileName().equals("Freeze spell")){
            inventory.addConsumable(player, "freeze");
        }else if(item.getTileName().equals("Potion")){
            inventory.addConsumable(player, "potion");
        }
        addToCombatLog(String.format("Player picked up a %s", item.getTileName()));
    }

    public boolean consumeItem(String item){
        if (item.equals("freeze")){
            if (inventory.getConsumable("freeze") > 0){
                inventory.useConsumable("freeze");
                return true;
            }
        } else if (item.equals("potion")){
            if (inventory.getConsumable("potion") > 0){
                inventory.useConsumable("potion");
                return true;
            }
        }
        return false;
    }

    public void pickUp() {
        Item oldItem = null;
        if (getCell().getItem() instanceof Weapon && inventory.getWeapons() != null){
            oldItem = inventory.getWeapons();
        }
        if (getCell().getItem() instanceof Shield && inventory.getShields() != null){
            oldItem = inventory.getShields();
        }
        putItemInInventory(this, getCell().getItem());
        getCell().setItem(oldItem);
    }

    @Override
    public String toString() {
        String result = "";
        if (inventory.getKeys() != null){
            result += "key: 1\n";
        }
        if (inventory.getWeapons() != null){
            result += String.format("%s\n", inventory.getWeapons().getTileName());
        }
        if (inventory.getShields() != null){
            result += String.format("%s\n", inventory.getShields().getTileName());
        }
        result += String.format("potions: %s\n", inventory.getConsumable("potion"));
        result += String.format("freeze: %s\n", inventory.getConsumable("freeze"));
//        result += inventory.getPotions().size();
        return result;
    }

    public void addToCombatLog(Actor p1, Actor p2, int damage, boolean isCrit){
        String p1Name = getNameForCombat(p1);
        String p2Name = getNameForCombat(p2);
        if (isCrit){
            combatLog.add(String.format("%s strikes %s Crit %s dmg",p1Name, p2Name, damage));
        } else {
            combatLog.add(String.format("%s strikes %s for %s dmg",p1Name, p2Name, damage));
        }
        if (combatLog.size() == 20){
            combatLog.remove(0);
        }
    }

    public void addToCombatLog(String msg){
        combatLog.add(msg + "");
        if (combatLog.size() == 20){
            combatLog.remove(0);
        }
    }

    public String getNameForCombat(Actor actor){
        String name = actor.getTileName();
        if (actor instanceof ImmortalSkeleton){
            name = "Immortal";
        } else if (actor instanceof Boss){
            name = "Witch";
        }
        return name;
    }


    @Override
    public String getTileName() {
        return "Player";
    }

    @Override
    public char getTileCharacter() { return '@'; }

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
            move(nextCell, this);
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

    public void castFreeze(GameMap map){
        map.removeFire();
        map.mortalizeBoss();
        map.getPlayer().setOnFireCount(0);
        map.getPlayer().setFreeze(2);
        map.getPlayer().addToCombatLog(String.format("Player froze enemies for %s turns", map.getPlayer().inventory.getFreezeValue()));
        for (Cell cell : map.getMonsterCells()){
            if (cell.getActor() instanceof Boss){
                map.getPlayer().addToCombatLog("Witch : \"Your little snow spell has no\neffect on me MUHAHAHA!\" ");
            }
        }
    }

    public void heal(GameMap map){
        map.getPlayer().addHealth(map.getPlayer().getInventory().getPotionValue());
        map.getPlayer().addToCombatLog("Player healed for 5 health points");
    }
}
