package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
    Player player = new Player(gameMap.getCell(0, 0));

    @Test
    public void testPutItemInInventory() {
        Weapon weapon = new Weapon(gameMap.getCell(1, 1), "Frostmourne", 10, 1);
        player.putItemInInventory(player, weapon);
        assertEquals("Frostmourne", player.getInventory().getWeapons().getName());
    }

    @Test
    public void testPutMultipleSameTypeItemInInventory() {
        Weapon weapon1 = new Weapon(gameMap.getCell(1, 1), "Frostmourne", 10, 1);
        Weapon weapon2 = new Weapon(gameMap.getCell(1, 1), "Kingslayer", 5, 2);
        player.putItemInInventory(player, weapon1);
        player.putItemInInventory(player, weapon2);
        assertEquals("Kingslayer", player.getInventory().getWeapons().getName());
        assertEquals(5, player.getInventory().getWeapons().getDamage());
    }
}
