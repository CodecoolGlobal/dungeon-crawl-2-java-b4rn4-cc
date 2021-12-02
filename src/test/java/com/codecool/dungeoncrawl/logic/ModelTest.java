package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.model.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelTest {

    @Test
    void getWeaponModelDependentIds() {
        WeaponModel weaponModel = new WeaponModel(1, 2, "name", 5, 7, 1, 2);
        weaponModel.setId(3);

        assertEquals(1, weaponModel.getInventoryId());
        assertEquals(2, weaponModel.getMapId());
        assertEquals(3, weaponModel.getId());
    }

    @Test
    void getShieldModelDependentIds() {
        ShieldModel shieldModel = new ShieldModel(3, 5, "name", 4, 3, 2);
        shieldModel.setId(1);

        assertEquals(1, shieldModel.getId());
        assertEquals(2, shieldModel.getMapId());
        assertEquals(3, shieldModel.getInventoryId());
    }

    @Test
    void getConsumableModelDependentIds() {
        ConsumableModel consumableModel = new ConsumableModel("potion", 3, 2, 1);
        consumableModel.setId(2);

        assertEquals(1, consumableModel.getMapId());
        assertEquals(2, consumableModel.getId());
    }

    @Test
    void getDoorModelDependentIds() {
        DoorModel doorModel = new DoorModel(1, 3, false, 2, "map2.txt");
        doorModel.setId(1);

        assertEquals(1, doorModel.getId());
        assertEquals(2, doorModel.getMapId());
    }

    @Test
    void getInventoryModelDependentIds() {
        InventoryModel inventoryModel = new InventoryModel(true, 2, 3, 2);
        inventoryModel.setId(1);

        assertEquals(1, inventoryModel.getId());
        assertEquals(2, inventoryModel.getPlayerId());
    }

    @Test
    void getMapModeDependentIds() {
        MapModel mapModel = new MapModel(2, 1, "###323#Đfg");
        mapModel.setId(2);

        assertEquals(1, mapModel.getGameStateId());
        assertEquals(2, mapModel.getId());
    }

    @Test
    void getPlayerModelDependentIds() {
        PlayerModel playerModel = new PlayerModel(2, 3, 10, 1);
        playerModel.setId(2);

        assertEquals(1, playerModel.getGameStateId());
        assertEquals(2, playerModel.getId());
    }

    @Test
    void getGameStateDependentIds() {
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);

        GameState gameState = new GameState("game_save", date, 2);
        gameState.setId(1);
        assertEquals(1, gameState.getId());
    }
}
