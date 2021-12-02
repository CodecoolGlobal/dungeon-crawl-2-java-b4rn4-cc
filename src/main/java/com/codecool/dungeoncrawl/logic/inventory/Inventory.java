package com.codecool.dungeoncrawl.logic.inventory;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Weapon;


public class Inventory {
    private Key keys = null;
    private Weapon weapons = null;
    private Shield shields = null;

    public int getFreezeAmount() {
        return freeze;
    }

    public int getPotionAmount() {
        return potion;
    }

    private int freeze = 0;
    private int potion = 1;

    public Inventory(){}

//    public Inventory(boolean hasKey, Weapon weapons, Shield shields, int freeze, int potion, Key key) {
//        if (hasKey) {
//            this.keys = key;
//        }
//        this.weapons = weapons;
//        this.shields = shields;
//        this.freeze = freeze;
//        this.potion = potion;
//    }

    public int getConsumable(String item){
        if (item.equals("freeze")){
            return freeze;
        } else if (item.equals("potion")){
            return potion;
        }
        return 0;
    }


    public void addConsumable(Player player, String item) {
        if (item.equals("freeze")) {
            freeze++;
        } else if (item.equals("potion")) {
            potion++;
        }
    }

    public void useConsumable(String item) {
        if (item.equals("freeze")) {
            freeze--;
        } else if (item.equals("potion")) {
            potion--;
        }
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }

    public void setPotion(int potion) {
        this.potion = potion;
    }

    public Key getKeys() {
        return keys;
    }

    public void setKeys(Key keys) {
        this.keys = keys;
    }

    public Weapon getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapon weapons) {
        this.weapons = weapons;
    }

    public Shield getShields() {
        return shields;
    }

    public void setShields(Shield shields) {
        this.shields = shields;
    }

    public int getFreezeValue() {
        return 2;
    }

    public int getPotionValue() {
        return 5;
    }
}




