package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Boss extends Actor{
    public Boss(Cell cell) {
        super(cell, 200, 10, 20);
    }


    public void setDirection(){

    }

    @Override
    public String getTileName() {
        return "boss";
    }
}
