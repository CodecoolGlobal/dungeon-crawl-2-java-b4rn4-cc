package com.codecool.dungeoncrawl.model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class MapModel extends BaseModel {
    private int mapNumber;
    private int gameStateId;
    private String map;

    public MapModel(int mapNumber, int gameStateId, String map) {
        this.mapNumber = mapNumber;
        this.gameStateId = gameStateId;
        this.map = map;
    }

    public void setMapNumber(int mapNumber) {
        this.mapNumber = mapNumber;
    }

    public void setGameStateId(int gameStateId) {
        this.gameStateId = gameStateId;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public int getMapNumber() {
        return mapNumber;
    }

    public int getGameStateId() {
        return gameStateId;
    }

    public String getMap() {
        return map;
    }
}
