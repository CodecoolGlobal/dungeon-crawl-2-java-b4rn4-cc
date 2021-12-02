package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.inventory.Inventory;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.model.*;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;
    private MapDao mapDao;
    private InventoryDao inventoryDao;
    private DoorDao doorDao;
    private ConsumableDao consumableDao;
    private ShieldDao shieldDao;
    private WeaponDao weaponDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
        mapDao = new MapDaoJdbc(dataSource);
        inventoryDao = new InventoryDaoJdbc(dataSource);
        doorDao = new DoorDaoJdbc(dataSource);
        consumableDao = new ConsumableDaoJdbc(dataSource);
        shieldDao = new ShieldDaoJdbc(dataSource);
        weaponDao = new WeaponDaoJdbc(dataSource);
    }

    public void saveGame(GameMap map, String name, int currentMap) {
        Date date = new Date(System.currentTimeMillis());
        GameState state = new GameState(name, date, currentMap);
        gameStateDao.add(state);
        int gameStateId = state.getId();

        PlayerModel player = new PlayerModel(map.getPlayer(), gameStateId);
        playerDao.add(player);

        InventoryModel inventory = new InventoryModel(map.getPlayer().getInventory(), player.getId());
        inventoryDao.add(inventory);

        if(map.getPlayer().getInventory().getWeapons() != null) {
            WeaponModel inventoryWeapon = new WeaponModel(map.getPlayer().getInventory().getWeapons(), inventory.getId(), -1);
            weaponDao.add(inventoryWeapon);
        }
        if(map.getPlayer().getInventory().getShields() != null) {
            ShieldModel inventoryShield = new ShieldModel(map.getPlayer().getInventory().getShields(), inventory.getId(), -1);
            shieldDao.add(inventoryShield);
        }

        MapModel mapModel = new MapModel(currentMap, gameStateId, map.createCurrentMap());
        mapDao.add(mapModel);
        saveItemFromMap(map, mapModel.getId());
        doorSave(map, inventory.hasKey(), mapModel);
    }

    private void doorSave(GameMap map, boolean hasKey, MapModel mapModel) {
        if(map.getPrevDoor() != null) {
            DoorModel prevDoor = new DoorModel(map.getPrevDoor(), true, mapModel.getId());
            doorDao.add(prevDoor);
        }
        DoorModel nextDoor = new DoorModel(map.getNextDoor(), hasKey, mapModel.getId());
        doorDao.add(nextDoor);
    }

    private void saveItemFromMap(GameMap map, int mapId) {
        Arrays.stream(map.getCells())
                .forEach(cells -> Arrays.stream(cells)
                        .forEach(cell -> {
                            if (cell.getItem() instanceof Weapon) {
                                WeaponModel weapon = new WeaponModel((Weapon) cell.getItem(), -1, mapId);
                                weaponDao.add(weapon);
                            }
                            if (cell.getItem() instanceof Shield) {
                                ShieldModel shield = new ShieldModel((Shield) cell.getItem(), -1, mapId);
                                shieldDao.add(shield);
                            }
                            if (cell.getItem() instanceof Freeze || cell.getItem() instanceof Potion) {
                                ConsumableModel consumable = new ConsumableModel(cell.getItem(), mapId);
                                consumableDao.add(consumable);
                            }
                        }));
    }

    public void updateSave(GameState gameState) {
        Date date = new Date(System.currentTimeMillis());
//        GameState gameState = new GameState(name, date, currentMap);
        gameState.setSavedAt(date);
        gameStateDao.update(gameState);
        Integer gameSateId = gameState.getId();
    }

    public List<GameState> getAllSaves() {
        return gameStateDao.getAll();
    }

    public GameState getMatchingName(String name) {
        return gameStateDao.getMatch(name);
    }

    public PlayerModel getPlayerFromSave(GameState gameState) {
        return playerDao.get(gameState.getId());
    }

    public InventoryModel getRawInventoryFromSave(int playerId) {
        return inventoryDao.get(playerId);
    }

    public WeaponModel getWeaponForInventory(int inventoryId) {
        return weaponDao.get(inventoryId);
    }

    public ShieldModel getShieldForInventory(int inventoryId) {
        return shieldDao.get(inventoryId);
    }

    public MapModel getMapFromSave(GameState gameState) {
        return mapDao.get(gameState.getId());
    }

    public List<WeaponModel> getWeaponsOnMapFromSave(int mapId) {
        return weaponDao.getAll(mapId);
    }

    public List<ShieldModel> getShieldOnMapFromSave(int mapId) {
        return shieldDao.getAll(mapId);
    }

    public List<ConsumableModel> getConsumableOnMapFromSave(int mapId) {
        return consumableDao.getAll(mapId);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("PSQL_DB_NAME");
        String user = System.getenv("PSQL_USER_NAME");
        String password = System.getenv("PSQL_PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
