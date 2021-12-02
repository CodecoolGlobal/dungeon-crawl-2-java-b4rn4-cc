package com.codecool.dungeoncrawl.logic.actors.monster;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.items.Freeze;
import com.codecool.dungeoncrawl.logic.items.Weapon;

public class LootSkeleton extends ImmortalSkeleton{
    private int freeze = 0;
    private String itemName = null;
    private int damage;
    private int crit;
    private boolean patrol = false;

    public LootSkeleton(Cell cell, String itemName, int damage, int crit) {
        super(cell, 3);
        this.itemName = itemName;
        this.damage = damage;
        this.crit = crit;
    }

    public LootSkeleton(Cell cell, String item) {
        super(cell, 2);
        freeze++;
    }

    @Override
    public char getTileCharacter() {
        if (itemName == null){
            return 'J';
        } else {
            return 'I';
        }
    }

    @Override
    protected void SetAlternativeDir() {
        if (patrol){
            int index = Actor.r.nextInt(4);
            direction = Direction.values()[index];
        } else {
            direction = Direction.NONE;
        }
    }

    public void getLoot(GameMap map, Cell cell){
        if (freeze == 1){
            cell.setItem(new Freeze(new Cell(map, 0, 0, CellType.FLOOR)));
        }
        if (itemName != null){
            cell.setItem(new Weapon(new Cell(map, 0, 0, CellType.FLOOR), itemName, damage, crit));
        }
    }
}
