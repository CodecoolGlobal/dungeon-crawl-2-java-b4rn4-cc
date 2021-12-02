package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.monster.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void actMove_playerMove_moveUpdatesCells() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.setDirection(Direction.WEST);
        player.act();

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(null, gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void actMove_playerMoveIntoWall_cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1));
        player.setDirection(Direction.WEST);
        player.act();

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void actMove_playerMoveOutOfMap_cannotMoveOutOfMap() {
        Player player = new Player(gameMap.getCell(2, 1));
        player.setDirection(Direction.NORTH);
        player.act();

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void actMove_playerMoveIntoAnotherActor_cannotMoveIntoAnotherActor() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        Player player = new Player(gameMap.getCell(1, 1));
        player.setDirection(Direction.WEST);
        player.act();

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void actCombat_playerFightWithSkeleton_skeletonGetHit(){
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        Player player = new Player(gameMap.getCell(1, 1));
        player.setDirection(Direction.WEST);
        player.act();
        assertTrue(skeleton.getHealth() < 8);
    }

    @Test
    void putItemToInventory_itemPickUp_playerHasItemInInventory(){
        Player player = new Player(gameMap.getCell(1, 1));
        Weapon weapon = new Weapon(gameMap.getCell(1,1), "Frostmourne", 5, 5);
        player.putItemInInventory(player, weapon);
        assertNotNull(player.getInventory().getWeapons());
    }


}