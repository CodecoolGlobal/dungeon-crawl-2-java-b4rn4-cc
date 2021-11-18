package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Boss;
import com.codecool.dungeoncrawl.logic.actors.FastSkeleton;
import com.codecool.dungeoncrawl.logic.actors.ImmortalSkeleton;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Door;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(300);
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

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
            case W:
                map.getPlayer().setDirection(Direction.NORTH);
                playRound();
                if (map.getPlayer().getCell().getItem() != null && map.getPlayer().getCell().getItem().isPackable()) {
                    pickUpButton.setVisible(true);
                    canvas.requestFocus();
                }
                refresh();
                break;
            case DOWN:
            case S:
                map.getPlayer().setDirection(Direction.SOUTH);
                playRound();
                if (map.getPlayer().getCell().getItem() != null && map.getPlayer().getCell().getItem().isPackable()) {
                    pickUpButton.setVisible(true);
                    canvas.requestFocus();
                }
                if (map.getPlayer().getCell().getItem() != null) {
                    if (map.getPlayer().getCell().getItem().getTileName().equals("openedDoor")) {
                        handleMapChanging();
                    }
                }
                refresh();
                break;
            case LEFT:
            case A:
                map.getPlayer().setDirection(Direction.EAST);
                playRound();
                if (map.getPlayer().getCell().getItem() != null && map.getPlayer().getCell().getItem().isPackable()) {
                    pickUpButton.setVisible(true);
                    canvas.requestFocus();
                }
                refresh();
                break;
            case RIGHT:
            case D:
                if (map.getPlayer().getCell().getItem() != null && map.getPlayer().getCell().getItem().isPackable()) {
                    pickUpButton.setVisible(true);
                    canvas.requestFocus();
                }
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
                break;
            case F:
                if (map.getPlayer().consumeItem("freeze")) {
                    map.removeFire();
                    map.mortalizeBoss();
                    map.getPlayer().setOnFireCount(0);
                    map.getPlayer().setFreeze(2);
                    map.getPlayer().addToCombatLog("Player Cast Freeze for 2 turns");
                    printStats();
                    refresh();
                }
                break;
            case Q:
                if (map.getPlayer().consumeItem("potion")) {
                    map.getPlayer().addHealth(5);
                    map.getPlayer().addToCombatLog("Player healed for 5 health points");
                    printStats();
                }
                break;
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
        printStats();
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

    private void printStats() {
        healthLabel.setText("" + map.getPlayer().getHealth());
        inventory.setText("" + map.getPlayer().toString());
        combatLog.setText("" + map.getPlayer().getCombatLog());
    }

    private void playRound() {
        map.getPlayer().act();
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
    }

    private boolean itemUnderPlayer() {
        return map.getPlayer().getCell().getItem() != null && map.getPlayer().getCell().getItem().isPackable();
    }

    private void handleGameOver() {
        if (map.getPlayer().getHealth() <= 0) {
            System.exit(1);
        }
    }

    private void MonstersMove() {
        if (map.getPlayer().getFreeze() > 0) {
            map.getPlayer().setFreeze(-1);
            proceedMonsterCounters();
            handleGameOver();
            refresh();
            return;
        }
        for (int index = 0; index < map.getMonsterCells().size(); index++) {
            Cell cell = map.getMonsterCells().get(index);
            if (isMonsterDead(cell)) {
                cell.setActor(null);
                map.removeMonsterCells(cell);
                continue;
            }
            cell.getActor().act(map, index);
        }
        handleGameOver();
        refresh();
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

    private void handleMapChanging() {
        Player player = map.getPlayer();
        if (map.getNextDoor() != null && map.getPlayer().getCell().equals(map.getNextDoor().getCell())) {
            moveToNextMap(player);
        } else if (map.getPlayer().getCell().equals(map.getPrevDoor().getCell())) {
            moveToPreviousMap(player);
        }
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
}
