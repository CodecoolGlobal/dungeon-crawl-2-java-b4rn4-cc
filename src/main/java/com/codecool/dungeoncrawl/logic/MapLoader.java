package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Potion;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import com.codecool.dungeoncrawl.logic.items.Door;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    private static int currentLevel = 1;

    public static void increaseLevel() {
        MapLoader.currentLevel += 1;
    }

    public static void decreaseLevel() {
        MapLoader.currentLevel -= 1;
    }
    public static GameMap loadMap(String currentRoute) {
        InputStream is = MapLoader.class.getResourceAsStream(currentRoute);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'm':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Weapon(cell);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Shield(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new Potion(cell);
                            break;
                        case 'c':
                            cell.setType(CellType.WALL);
                            map.setNextDoor(new Door(cell, getNextMap()));
                            break;
                        case 'o':
                            cell.setType(CellType.FLOOR);
                            map.setPrevDoor(new Door(cell, getPrevMap()));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }
    public static String getPrevMap() {
        String prevRoute;
        switch (currentLevel) {
            default:
                prevRoute = null;
                break;
            case 2:
                prevRoute = "/map.txt";
                break;
            case 3:
                prevRoute = "/map2.txt";
                break;
        }
        return prevRoute;
    }

    public static String getNextMap() {
        String nextRoute;
        switch (currentLevel) {
            default:
                nextRoute = "/map2.txt";
                break;
            case 2:
                nextRoute = "/map3.txt";
                break;
            case 3:
                nextRoute = null;
                break;
        }
        return nextRoute;
    }
}
