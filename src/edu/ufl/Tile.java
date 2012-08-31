package edu.ufl;

public class Tile {

    //TileType def
    //(AIR has underscores because debugging is easier if all enums' names have
    // the same length)
    public static enum TileType {
        AIR__,
        BRICK,
        WATER,
        GRASS
    }

    //Members
    private TileType type;
    private int X;
    private int Y;

    //Getters/Setters
    public int  getX()      { return this.X; }
    public void setX(int X) { this.X = X;    }

    public int  getY()      { return this.Y; }
    public void setY(int Y) { this.Y = Y;    }

    public TileType getType() { return this.type; }
    public void setType(TileType type) { this.type = type; }

    //Constructor
    Tile(TileType t, int X, int Y) {
        this.type = t;
        this.X = X;
        this.Y = Y;
    }

}
