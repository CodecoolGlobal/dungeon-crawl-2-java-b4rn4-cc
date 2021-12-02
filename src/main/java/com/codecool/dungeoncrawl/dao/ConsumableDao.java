package com.codecool.dungeoncrawl.dao;


import com.codecool.dungeoncrawl.model.ConsumableModel;

import java.util.List;

public interface ConsumableDao {
    void add(ConsumableModel state);
    void update(ConsumableModel state);
    ConsumableModel get(int id);
    List<ConsumableModel> getAll(int mapId);
}
