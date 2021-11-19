package com.codecool.dungeoncrawl.logic.inventory;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Weapon;


public class Inventory {
    private Key keys = null;
    private Weapon weapons = null;
    private Shield shields = null;
    private int freeze = 0;
    private int potion = 1;

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




