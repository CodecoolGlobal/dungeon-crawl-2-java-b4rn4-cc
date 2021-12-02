package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.WeaponModel;

import java.util.List;

public interface WeaponDao {
    public void add(WeaponModel weapon);

    public void update(WeaponModel weapon);

    public WeaponModel get(int id);

    public List<WeaponModel> getAll();
}
