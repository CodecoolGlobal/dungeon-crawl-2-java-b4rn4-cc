package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.Weapon;

import java.util.List;

public interface WeaponDao {
    public void add(Weapon weapon);

    public void update(Weapon weapon);

    public Weapon get(int id);

    public List<Weapon> getAll();
}
