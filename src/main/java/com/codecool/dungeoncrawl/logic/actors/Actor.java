package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.monster.Monster;

import java.util.Random;

public abstract class Actor implements Drawable {
    protected Cell cell;
    protected static final Random r = new Random();
    protected Direction direction;
    private int health;
    protected final int MAX_DAMAGE;
    protected final int MIN_DAMAGE;
    protected int critChance;

    public void setOnFireCount(int onFireCount) {
        this.onFireCount = onFireCount;
    }

    private int onFireCount = 0;


    public Actor(Cell cell, int health, int minDmg, int maxDmg, int critChance) {
        this.cell = cell;
        this.cell.setActor(this);
        this.health = health;
        this.MAX_DAMAGE = maxDmg;
        this.MIN_DAMAGE = minDmg;
        this.critChance = critChance;
    }

    public abstract void act(GameMap map, int index);

    public abstract void setDirection(Player player);

    protected void combat(Cell nextCell, Player player){
        int damage = getDamage();
        int critChance = this.critChance + getBonusCrit();
        boolean isCrit = false;
        if (isCriticalStrike(critChance)){
            damage *= 2;
            isCrit = true;
        }
        if (isEnemyImmortal(nextCell)){
            addToCombatLog(player, this, nextCell.getActor(), 0, false);
            handleFire(player);
            return;
        }
        handleFire(player);
        nextCell.getActor().getHit(player,this, nextCell.getActor(), damage, isCrit);

    }

    public void getHit(Actor actor, int damage, String cause, Player player){
        damage = getActualDamage(damage);
        health -= damage;
        String name = player.getNameForCombat(actor);
        player.addToCombatLog(String.format("%s suffered %s damage from %s", name, damage, cause));
    }


    public void getHit(Player player, Actor hitting, Actor gettingHit, int damage, boolean isCrit){
        damage = getActualDamage(damage);
        health -= damage;
        addToCombatLog(player, hitting, gettingHit, damage, isCrit);
    }

    private int getActualDamage(int damage){
        if (this instanceof Player){
            int def = ((Player) this).getShieldDefense();
            if (damage - def > 0){
                damage -= def;
            } else {
                damage = 0;
            }
        }
        return damage;
    }

    private void addToCombatLog(Player player, Actor hitting, Actor gettingHit, int damage, boolean isCrit){
        player.addToCombatLog(hitting, gettingHit, damage, isCrit);
    }

    private boolean isEnemyImmortal(Cell nextCell){
        return nextCell.getActor().getTileName().equals("ImmortalSkeletonOn");
    }


    protected int getDamage(){
        return r.nextInt(MAX_DAMAGE + 1 - MIN_DAMAGE) + MIN_DAMAGE;
    }

    protected int getBonusCrit(){
        return 0;
    }

    protected  boolean isCriticalStrike(int critChance){
        return r.nextInt(100) + 1 <= critChance;
    }


    protected boolean collisionWithEnemy(Cell nextCell){
        if (nextCell.getActor() != null){
            return (this instanceof Monster && nextCell.getActor() instanceof Player) || this instanceof Player;
        }
        return false;
    }


    protected void move(Cell nextCell, Player player){
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
        handleFire(player);
    }

    protected boolean canMove(Cell nextCell){
        int x = nextCell.getX();
        int y = nextCell.getY();
        int width = nextCell.getBoardWidth();
        int height = nextCell.getBoardHeight();
        return nextCell.getType() != CellType.WALL && nextCell.getActor() == null && x >= 0 && y >= 0 && x < width && y < height;
    }


    protected Cell  getNextCell(){
        int dx = 0;
        int dy = 0;
        switch (direction){
            case EAST:
                dx = -1;
                break;
            case WEST:
                dx = 1;
                break;
            case NORTH:
                dy = -1;
                break;
            case SOUTH:
                dy = 1;
                break;
            case NONE:
                return null;
        }
        return cell.getNeighbor(dx, dy);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void addHealth(int health){
        this.health += health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void setX(int x) {cell.setX(x);}

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setY(int y) {cell.setY(y);}

    private void handleFire(Player player){
        if (cell.getType() == CellType.FIRE){
            onFireCount = 3;
        }
        if (onFireCount > 0){
            getHit(this,6, "Fire", player);
            onFireCount--;
        }
    }
}
