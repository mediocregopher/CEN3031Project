package edu.ufl;

public class Tile extends LevelObject{

    public final static float SIZE = 50f;

    //TileType def
    //(AIR has underscores because debugging is easier if all enums' names have
    // the same length)
    public static enum TileType {
        AIR__,
        BRICK,
        WATER,
        GRASS
    }

    //Extra Members
    private TileType type;
    public TileType getType() { return this.type; }
    public void setType(TileType type) { this.type = type; }

    //Constructor
    Tile(TileType t, float x, float y) {
        super(x,y,SIZE,SIZE);
        this.type = t;
    }

}
