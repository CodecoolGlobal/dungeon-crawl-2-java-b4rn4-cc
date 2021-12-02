package com.codecool.dungeoncrawl.dao;


import com.codecool.dungeoncrawl.model.ConsumableModel;

public interface ConsumableDao {
    void add(ConsumableModel state);
    void update(ConsumableModel state);
    ConsumableModel get(int id);
}
