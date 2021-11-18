package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    FIRE("fire"),
    LETTERA("letterA"),
    LETTERG("letterG"),
    LETTERM("letterM"),
    LETTERE("letterE"),
    LETTERO("letterO"),
    LETTERV("letterV"),
    LETTERR("letterR"),
    LETTERY("letterY"),
    LETTERU("letterU"),
    LETTERW("letterW"),
    LETTERN("letterN"),
    DEAD("dead"),
    PILLARTOP("pillarTop"),
    PILLAR("pillar"),
    PILLARBOTTOM("pillarBottom");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
