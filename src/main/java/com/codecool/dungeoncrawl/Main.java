package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.actors.monster.Boss;
import com.codecool.dungeoncrawl.logic.actors.monster.ImmortalSkeleton;
import com.codecool.dungeoncrawl.logic.actors.monster.LootSkeleton;
import com.codecool.dungeoncrawl.logic.items.Door;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
public class Main extends Application {
    String route = "/map.txt";
    GameMap map = MapLoader.loadMap(route);
    Canvas canvas = new Canvas(
            Math.min(map.getWidth(), 25) * Tiles.TILE_WIDTH,
            Math.min(map.getHeight(), 30) * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventory = new Label();
    Label combatLog = new Label();
    Button pickUpButton = new Button("Pick Up");
    private boolean hasWon = false;
    private boolean isGameOver = false;
    GameDatabaseManager dbManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupDbManager();
        GridPane ui = new GridPane();
        ui.setPrefWidth(310);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);

        ui.add(new Label("Inventory: "), 0, 4);
        ui.add(inventory, 1, 6);

        ui.add(pickUpButton, 1, 12);
        pickUpButton.setVisible(false);
        pickUpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> pickUp());

        ui.add(new Label("CombatLog: "), 0, 14);
        ui.add(combatLog, 1, 17);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        showModal(stage);
    }

    private void showModal(Stage stage) {
        stage.setTitle("Save Game");
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Label saveGameName = new Label("Name:");
        TextField name = new TextField();
        GridPane gridPane = new GridPane();
        Button cancelButton = new Button("Cancel");
        Button saveButton = new Button("Save");
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> cancelSave(stage));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(30);
        gridPane.setVgap(30);
        gridPane.add(saveGameName, 0, 0);
        gridPane.add(name, 1, 0);
        gridPane.add(cancelButton, 4, 4);
        gridPane.add(saveButton, 0, 4);
        Scene scene = new Scene(gridPane);
        stage.setWidth(600);
        stage.setHeight(300);
        stage.setX((primaryScreenBounds.getWidth() - primaryScreenBounds.getWidth() / 2) - stage.getWidth() / 2);
        stage.setY((primaryScreenBounds.getHeight() - primaryScreenBounds.getHeight() / 2) - stage.getHeight() / 2);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void cancelSave(Stage modal) {
        modal.close();
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
    }
    /* case S:
                Player player = map.getPlayer();
                dbManager.savePlayer(player);
                break; */

    private void onKeyPressed(KeyEvent keyEvent) {
        if (hasWon || isGameOver){
            System.exit(1);
        }
        switch (keyEvent.getCode()) {
            case UP:
            case W:
                map.getPlayer().setDirection(Direction.NORTH);
                playRound();
                break;
            case DOWN:
            case S:
                map.getPlayer().setDirection(Direction.SOUTH);
                playRound();
                break;
            case LEFT:
            case A:
                map.getPlayer().setDirection(Direction.EAST);
                playRound();
                break;
            case RIGHT:
            case D:
                map.getPlayer().setDirection(Direction.WEST);
                playRound();
                break;
            case E:
                if (itemUnderPlayer()) {
                    pickUp();
                }
                break;
            case R:
                MonstersMove();
                if (handleGameOver()){
                    refreshFixed();
                    return;
                } else {
                    refresh();
                }
                break;
            case F:
                if (map.getPlayer().consumeItem("freeze")) {
                    map.getPlayer().castFreeze(map);
                    refresh();
                }
                break;
            case Q:
                if (map.getPlayer().consumeItem("potion")) {
                    map.getPlayer().heal(map);
                    printStats();
                }
                break;
            case I:
                addInventoryLog();
                refresh();
        }
    }

    private void pickUp() {
        map.getPlayer().pickUp();
        canvas.requestFocus();
        pickUpButton.setVisible(false);
        if (map.getPlayer().hasKey()) {
            map.getNextDoor().getCell().setType(CellType.FLOOR);
            map.getNextDoor().getCell().setItem(new Door(map.getNextDoor().getCell(), MapLoader.getNextMap()));
        }
        refresh();
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        int centerX = (int) (canvas.getWidth() / (Tiles.TILE_WIDTH * 2));
        int centerY = (int) (canvas.getHeight() / (Tiles.TILE_WIDTH * 2)) - 1;
        int[] shift = {0, 0};
        if (map.getPlayer().getX() > centerX) {
            shift[0] = map.getPlayer().getX() - centerX;
        }
        if (map.getPlayer().getY() > centerY) {
            shift[1] = map.getPlayer().getY() - centerY;
        }
        for (int x = 0; x + shift[0] < map.getWidth(); x++) {
            for (int y = 0; y + shift[1] < map.getHeight(); y++) {
                Cell cell = map.getCell(x + shift[0], y + shift[1]);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        printStats();
    }
    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }

    private void printStats() {
        healthLabel.setText("" + map.getPlayer().getHealth());
        inventory.setText("" + map.getPlayer().toString());
        combatLog.setText("" + map.getPlayer().getCombatLog());
    }

    private void playRound() {
        map.getPlayer().act();
        printStats();
        if (map.getPlayer().isInvalidMove()) {
            return;
        }

        if (itemUnderPlayer()) {
            pickUpButton.setVisible(true);
            canvas.requestFocus();
        } else {
            pickUpButton.setVisible(false);
        }

        MonstersMove();

        if (hasPlayerEnteredDoor()){
            handleMapChanging();
        }

        if (handleGameOver()){
            refreshFixed();
        } else {
            refresh();
        }

    }

    private boolean itemUnderPlayer() {
        return map.getPlayer().getCell().getItem() != null && map.getPlayer().getCell().getItem().isPackable();
    }

    private boolean handleGameOver() {
        if (hasWon){
            map = MapLoader.loadMap("/won.txt");
            return true;
        }
        if (map.getPlayer().getHealth() <= 0) {
            isGameOver = true;
            map = MapLoader.loadMap("/gameOver.txt");
            return true;
        }
        return false;
    }

    private void MonstersMove() {
        for (int index = 0; index < map.getMonsterCells().size(); index++) {
            Cell cell = map.getMonsterCells().get(index);
            if (isMonsterDead(cell)) {
                if (cell.getActor() instanceof Boss){
                    hasWon = true;
                }
                if (cell.getActor() instanceof LootSkeleton){
                    ((LootSkeleton) cell.getActor()).getLoot(map, cell);
                }
                cell.setActor(null);
                map.removeMonsterCells(cell);
                continue;
            }
            if (!isMapFrozen()){
                cell.getActor().act(map, index);
            }
        }
        printStats();

        if (hasPlayerBeatWaves()){
            map.removeFire();
            ((Boss) map.getMonsterCells().get(0).getActor()).mortalize();
        }

        if (isMapFrozen()) {
            map.getPlayer().setFreeze(-1);
            proceedMonsterCounters();
        }
    }

    private boolean isMapFrozen(){
        return map.getPlayer().getFreeze() > 0;
    }

    private boolean hasPlayerBeatWaves(){
        if (map.getMonsterCells().size() == 1){
            Actor monster = map.getMonsterCells().get(0).getActor();
            if (monster instanceof Boss){
                return ((Boss) monster).hasPlayerBeatWaves(map);
            }
        }
        return false;
    }


    private void proceedMonsterCounters() {
        for (Cell cell : map.getMonsterCells()) {
            if (cell.getActor() instanceof ImmortalSkeleton) {
                ((ImmortalSkeleton) cell.getActor()).proceedCounter();
            }
        }
    }

    private boolean isMonsterDead(Cell cell) {
        return cell.getActor().getHealth() <= 0;
    }

    private boolean hasPlayerEnteredDoor(){
        if (map.getPlayer().getCell().getItem() != null) {
            return  map.getPlayer().getCell().getItem().getTileName().equals("openedDoor");
        }
        return false;
    }

    private void handleMapChanging() {
        Player player = map.getPlayer();
        if (map.getNextDoor() != null && map.getPlayer().getCell().equals(map.getNextDoor().getCell())) {
            moveToNextMap(player);
        } else if (map.getPlayer().getCell().equals(map.getPrevDoor().getCell())) {
            moveToPreviousMap(player);
        }
    }

    private void addInventoryLog(){
        Player player = map.getPlayer();
        player.addToCombatLog("");
        player.addToCombatLog("----------------------------------------------");
        player.addToCombatLog(String.format("Potion: Heals for %s", player.getInventory().getPotionValue()));
        player.addToCombatLog(String.format("Freeze: Stuns monsters for %s steps", player.getInventory().getFreezeValue()));
        if (player.getInventory().getWeapons() != null){
            Weapon weapon = player.getInventory().getWeapons();
            player.addToCombatLog(String.format("%s: Damage: %s / Crit: %s%%",weapon.getTileName(), weapon.getDamage(), weapon.getCrit()));
        }
        if (player.getInventory().getShields() != null){
            Shield shield = player.getInventory().getShields();
            player.addToCombatLog(String.format("%s: %s damage reduction",shield.getTileName(), shield.getFlatDefense()));
        }
        if (player.getInventory().getKeys() != null){
            Key key = player.getInventory().getKeys();
            player.addToCombatLog(String.format("%s: opens a door", key.getTileName()));
        }
        player.addToCombatLog("----------------------------------------------");
    }

    private void setupPlayer(Player oldPlayer) {
        Player newPlayer = map.getPlayer();
        oldPlayer.setCell(newPlayer.getCell());
        oldPlayer.setX(newPlayer.getX());
        oldPlayer.setY(newPlayer.getY());
        map.setPlayer(oldPlayer);
    }

    private void moveToNextMap(Player player) {
        route = map.getNextDoor().getMapTo();
        if (MapLoader.getCurrentLevel() == 1 && player.getSecondLevel() != null) {
            player.setFirstLevel(map);
            map = player.getSecondLevel();
            MapLoader.increaseLevel();
        } else if (MapLoader.getCurrentLevel() == 2 && player.getThirdLevel() != null) {
            player.setSecondLevel(map);
            map = player.getThirdLevel();
            MapLoader.increaseLevel();
        } else if (MapLoader.getCurrentLevel() == 3) {
            player.setThirdLevel(map);
            MapLoader.increaseLevel();
        } else {
            if (MapLoader.getCurrentLevel() == 1) {
                player.setFirstLevel(map);
            } else if (MapLoader.getCurrentLevel() == 2) {
                player.setSecondLevel(map);
            }
            player.removeKeyFromInventory();
            MapLoader.increaseLevel();
            map = MapLoader.loadMap(route);
            Player newPlayer = map.getPlayer();
            newPlayer.setFirstLevel(player.getFirstLevel());
            newPlayer.setSecondLevel(player.getSecondLevel());
            newPlayer.setThirdLevel(player.getThirdLevel());
        }
        Player newPlayer = map.getPlayer();
        newPlayer.setHealth(player.getHealth());
        newPlayer.setInventory(player.getInventory());
    }

    private void moveToPreviousMap(Player player) {
        if (MapLoader.getCurrentLevel() == 2) {
            player.setSecondLevel(map);
            map = player.getFirstLevel();
            map.getPlayer().setSecondLevel(player.getSecondLevel());
        } else if (MapLoader.getCurrentLevel() == 3) {
            player.setThirdLevel(map);
            map = player.getSecondLevel();
            map.getPlayer().setThirdLevel(player.getThirdLevel());
        }
//                setupPlayer(player);
        map.getPlayer().setHealth(player.getHealth());
        map.getPlayer().setInventory(player.getInventory());
        MapLoader.decreaseLevel();
    }

    private void refreshFixed() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
    }
}
