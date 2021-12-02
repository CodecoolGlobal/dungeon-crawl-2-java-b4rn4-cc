package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.DoorModel;


public interface DoorDao {
    void add(DoorModel door);
    void update(DoorModel door);
    DoorModel get(int id);
}

