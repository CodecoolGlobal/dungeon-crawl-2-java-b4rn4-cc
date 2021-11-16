package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell, 100, 5, 10);
    }

    public String getTileName() {
        return "player";
    }
}
