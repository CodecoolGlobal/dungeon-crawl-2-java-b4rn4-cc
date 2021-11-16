package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Actor {
    private int bonusDamage;

    public Player(Cell cell) {
        super(cell, 3, 7);
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
