package com.codecool.dungeoncrawl.model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class GameState extends BaseModel {
    private Date savedAt;
    private int currentMapNumber;
    private List<String> discoveredMaps = new ArrayList<>();
//    private PlayerModel player;
    private String gameStateName;

    public GameState(String gameStateName, Date savedAt, /*PlayerModel player,*/ int currentMapNumber) {
        this.gameStateName = gameStateName;
        this.savedAt = savedAt;
//        this.player = player;
        this.currentMapNumber = currentMapNumber;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public int getCurrentMapNumber() {
        return currentMapNumber;
    }

    public void setCurrentMapNumber(int currentMapNumber) {
        this.currentMapNumber = currentMapNumber;
    }

    public List<String> getDiscoveredMaps() {
        return discoveredMaps;
    }

    public void addDiscoveredMap(String map) {
        this.discoveredMaps.add(map);
    }

   /* public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }*/

    public String getGameStateName() {
        return gameStateName;
    }

    public void setGameStateName(String gameStateName) {
        this.gameStateName = gameStateName;
    }
}
