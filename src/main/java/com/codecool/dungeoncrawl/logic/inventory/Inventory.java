package com.codecool.dungeoncrawl.logic.inventory;

import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Potion;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Weapon;

import java.util.ArrayList;

public class Inventory {
    private Key keys = null;
    private Weapon weapons = null;
    private Shield shields = null;
    private int freeze = 1;
    private int potion = 1;
    private ArrayList<Potion> potions = new ArrayList();

    public int getConsumable(String item){
        if (item.equals("freeze")){
            return freeze;
        } else if (item.equals("potion")){
            return potion;
        }
        return 0;
    }

    public void setConsumable(String item){
        if (item.equals("freeze")){
            freeze--;
        } else if (item.equals("potion")){
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

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public void setPotions(Potion potions) {
        this.potions.add(potions);
    }
}




