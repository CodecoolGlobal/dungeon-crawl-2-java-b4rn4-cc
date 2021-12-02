package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import com.codecool.dungeoncrawl.model.ShieldModel;

import java.util.List;

public interface ShieldDao {
    public void add(ShieldModel shield );

    public void update(ShieldModel shield );

    public ShieldModel get(int id);

    public List<ShieldModel> getAll(int mapId);
}
