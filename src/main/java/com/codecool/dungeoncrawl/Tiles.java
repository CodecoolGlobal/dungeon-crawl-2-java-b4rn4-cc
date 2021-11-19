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
        tileMap.put("Sword", new Tile(0, 29));
        tileMap.put("Axe", new Tile(6, 30));
        tileMap.put("Dagger", new Tile(0, 28));
        tileMap.put("Wooden shield", new Tile(1,23));
        tileMap.put("Iron shield", new Tile(4,23));
        tileMap.put("Key", new Tile(16, 23));
        tileMap.put("Freeze spell", new Tile(16, 25));
        tileMap.put("Potion", new Tile(23, 22));
        tileMap.put("openedDoor", new Tile(8, 10));
        tileMap.put("closedDoor", new Tile(8, 11));
        tileMap.put("pillarTop", new Tile(3, 11));
        tileMap.put("pillar", new Tile(3, 12));
        tileMap.put("pillarBottom", new Tile(3, 13));
        tileMap.put("dead", new Tile(0, 14));
        tileMap.put("letterG", new Tile(25, 30));
        tileMap.put("letterA", new Tile(19, 30));
        tileMap.put("letterM", new Tile(31, 30));
        tileMap.put("letterE", new Tile(23, 30));
        tileMap.put("letterO", new Tile(20, 31));
        tileMap.put("letterV", new Tile(27, 31));
        tileMap.put("letterR", new Tile(23, 31));
        tileMap.put("letterY", new Tile(30, 31));
        tileMap.put("letterU", new Tile(26, 31));
        tileMap.put("letterW", new Tile(28, 31));
        tileMap.put("letterN", new Tile(19, 31));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
