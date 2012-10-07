package edu.ufl;

public class Tile extends LevelObject{

    public final static float SIZE = 50f;

    public static enum TileType {
        AIR,
        GROUND
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
