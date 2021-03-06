package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class PlayerModel extends BaseModel {
    private int hp;
    private int x;
    private int y;
    private int gameStateId;

    public PlayerModel(int x, int y, int hp, int gameStateId) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.gameStateId = gameStateId;
    }

    public PlayerModel(Player player, int gameStateId) {
        this.x = player.getX();
        this.y = player.getY();

        this.hp = player.getHealth();
        this.gameStateId = gameStateId;
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

    public void setGameStateId(int gameStateId) {
        this.gameStateId = gameStateId;
    }

    public int getGameStateId() {
        return gameStateId;
    }
}
