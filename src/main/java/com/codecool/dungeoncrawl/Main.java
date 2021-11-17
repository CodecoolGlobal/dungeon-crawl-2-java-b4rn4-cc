package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.FastSkeleton;
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
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Button pickUpButton = new Button("Pick Up");
    Label inventory = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);

        ui.add(new Label("Inventory: "), 0, 3);
        ui.add(inventory, 1, 3);

        ui.add(pickUpButton, 1, 10);
        pickUpButton.setVisible(false);
        pickUpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> pickUp());

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
                if (itemUnderPlayer()){
                    pickUp();
                }
                break;
            case R:
                MonstersMove();
                break;
            case F:
                if (map.getPlayer().consumeItem("freeze")){
                    map.getPlayer().setFreeze(2);
                }
                break;
            case Q:
                if (map.getPlayer().consumeItem("potion")){
                    map.getPlayer().addHealth(5);
                    printStats();
                }
                break;
        }
    }

    private void pickUp() {
        map.getPlayer().pickUp();
        canvas.requestFocus();
        pickUpButton.setVisible(false);
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
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

    private void printStats(){
        healthLabel.setText("" + map.getPlayer().getHealth());
        inventory.setText("" + map.getPlayer().toString());
    }

    private void playRound(){
        map.getPlayer().act();
        if (map.getPlayer().isInvalidMove()){
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

    private boolean itemUnderPlayer(){
        return map.getPlayer().getCell().getItem() != null;
    }

    private void handleGameOver(){
        if (map.getPlayer().getHealth() <= 0){
            System.exit(1);
        }
    }

    private void MonstersMove(){
        if (map.getPlayer().getFreeze() > 0){
            map.getPlayer().setFreeze(-1);
            handleGameOver();
            refresh();
            return;
        }
        for (int index = 0; index < map.getMonsterCells().size(); index++){
            Cell cell = map.getMonsterCells().get(index);
            if (isMonsterDead(cell)){
                cell.setActor(null);
                map.removeMonsterCells(cell);
                continue;
            }
            cell.getActor().act(map, index);
        }
        handleGameOver();
        refresh();
    }

    private boolean isMonsterDead(Cell cell){
        return cell.getActor().getHealth() <= 0;
    }
}
