package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.MapModel;

import java.util.LinkedList;
import java.util.List;

public interface MapDao {
    void add(MapModel map);
    void update(MapModel map);
    MapModel get(int id);
    LinkedList<MapModel> getAll(int gameStateId);
}

