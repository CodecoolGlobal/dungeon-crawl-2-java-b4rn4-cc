package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;


public class Boss extends Monster{
    private int waveCount = 1;
    private boolean isImmortal = true;

    public Boss(Cell cell) {
        super(cell, 30, 5, 7, 1);
    }


    @Override
    public String getTileName() {
        if (isImmortal){
            return "ImmortalSkeletonOn";
        }
        return "The Boss";
    }

    @Override
    public void setDirection(Player player) {

    }

    public void act(GameMap map, int index){
        if (canAttackPlayer()){
            Cell nextCell = getNextCell();
            combat(nextCell, map.getPlayer());
        }else if (isOnlyBossAlive(map) && isImmortal){
            startNextWave(map);
        }
    }

    private boolean canAttackPlayer(){
        for (int index = 0; index < 4; index++){
            direction = Direction.values()[index];
            Cell nextCell = getNextCell();
            if (collisionWithEnemy(nextCell)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOnlyBossAlive(GameMap map){
        return map.getMonsterCells().size() == 1 && map.getMonsterCells().get(0).getActor() instanceof Boss;
    }

    public boolean playerBeatWaves(){
        return waveCount == 3;
    }

    private void startNextWave(GameMap map){
        waveCount++;
        givePlayerPotion(map);
        summonSkeletons(map);
    }

    private void givePlayerPotion(GameMap map){
        map.getPlayer().getInventory().addConsumable("potion");
        map.getPlayer().addToCombatLog("Player received a potion");
    }

    private void summonSkeletons(GameMap map){
        for (int i = 0; i < waveCount + 2; i++){
            while (true){
                int x = getRandom(getX() + 7, getX() - 7);
                int y = getRandom(getY() + 6, getY() + 3 );
                if (map.getCell(x, y).getActor() == null){
                    new BossAdd(map.getCell(x, y));
                    map.addMonsterCell(map.getCell(x, y));
                    break;
                }
            }
        }
    }

    private int getRandom(int max, int min){
        return r.nextInt(max + 1 - min) + min;
    }

    public void mortalize(){
        isImmortal = false;
    }
}
