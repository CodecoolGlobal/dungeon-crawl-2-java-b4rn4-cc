package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class PlayerModel extends BaseModel {
    private int hp;
    private int x;
    private int y;

    public PlayerModel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PlayerModel(Player player) {
        this.x = player.getX();
        this.y = player.getY();

        this.hp = player.getHealth();
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
