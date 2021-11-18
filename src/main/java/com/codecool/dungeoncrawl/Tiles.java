package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("fire", new Tile(15, 10));
        tileMap.put("Player", new Tile(27, 0));
        tileMap.put("Skeleton", new Tile(29, 6));
        tileMap.put("Fast Boi", new Tile(29, 2));
        tileMap.put("ImmortalSkeletonOff", new Tile(24, 3));
        tileMap.put("ImmortalSkeletonOn", new Tile(24, 7));
        tileMap.put("The Boss", new Tile(24, 9));
        tileMap.put("Boss Add", new Tile(27, 8));
        tileMap.put("Weapon", new Tile(0, 29));
        tileMap.put("Shield", new Tile(1,23));
        tileMap.put("Key", new Tile(16, 23));
        tileMap.put("Potion", new Tile(23, 23));
        tileMap.put("openedDoor", new Tile(8, 10));
        tileMap.put("closedDoor", new Tile(8, 11));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
