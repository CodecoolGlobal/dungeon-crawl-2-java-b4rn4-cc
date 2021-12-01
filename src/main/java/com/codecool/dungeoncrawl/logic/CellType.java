package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty", ' '),
    FLOOR("floor", '.'),
    WALL("wall", '#'),
    FIRE("fire", '-'),
    LETTERA("letterA", 'a'),
    LETTERG("letterG", 'g'),
    LETTERM("letterM", 'n'),
    LETTERE("letterE", 'e'),
    LETTERO("letterO", 'o'),
    LETTERV("letterV", 'v'),
    LETTERR("letterR", 'r'),
    LETTERY("letterY", 'y'),
    LETTERU("letterU", 'U'),
    LETTERW("letterW", 'W'),
    LETTERN("letterN", 'N'),
    DEAD("dead", 'd'),
    PILLARTOP("pillarTop", 't'),
    PILLAR("pillar", 'j'),
    PILLARBOTTOM("pillarBottom", 'x');

    private final String tileName;
    private final char tileCharacter;

    CellType(String tileName, char tileCharacter) {
        this.tileName = tileName;
        this.tileCharacter = tileCharacter;
    }

    public String getTileName() {
        return tileName;
    }

    public char getTileCharacter() {
        return tileCharacter;
    }
}
