package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Weapon;

import java.util.List;

public interface ShieldDao {
    public void add(Shield shield );

    public void update(Shield shield );

    public Shield get(int id);

    public List<Weapon> getAll();
}
