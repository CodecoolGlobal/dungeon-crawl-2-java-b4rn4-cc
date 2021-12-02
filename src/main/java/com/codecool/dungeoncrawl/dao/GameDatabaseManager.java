package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        // PlayerModel model = new PlayerModel(player);
        // playerDao.add(model);
    }

    public void saveGame(GameMap map, String name, int currentMap) {
        Date date = new Date(System.currentTimeMillis());
        GameState state = new GameState(name, date, currentMap);
        gameStateDao.add(state);
        Integer gameStateId = state.getId();
        // TODO: 2021. 12. 01. Continue the other jdbc classes
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
