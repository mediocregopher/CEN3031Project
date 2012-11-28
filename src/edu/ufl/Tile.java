package edu.ufl;

import java.util.HashMap;

public class Tile extends LevelObject{

    public static float SIZE;
    private boolean active;
    
    public static enum TileType {
        AIR,
        GROUND,
        AIBOUND,
        CHECKPOINT,
        LEVELEND,
        FOOTBALL,
        ASPHALT,
        BRICK,
        WOOD
    }

    private static HashMap<TileType,Integer> tileTextures;
    static {
        tileTextures = new HashMap<TileType,Integer>(10); //Increase if we have over 10 tile types
        tileTextures.put(TileType.GROUND, R.drawable.ground_tile);
        tileTextures.put(TileType.AIBOUND, R.drawable.blank_tile);
        tileTextures.put(TileType.CHECKPOINT, R.drawable.yellow_flag);
        tileTextures.put(TileType.LEVELEND, R.drawable.red_flag);
        tileTextures.put(TileType.FOOTBALL, R.drawable.football);
        tileTextures.put(TileType.ASPHALT, R.drawable.seamless_asphalt_texture);
        tileTextures.put(TileType.BRICK, R.drawable.seamless_brick_tile);
        tileTextures.put(TileType.WOOD, R.drawable.seamless_wood_planks);
    }

    //Extra Members
    private TileType type;
    public TileType getType() { return this.type; }
    public void setType(TileType type) { this.type = type; }
    public boolean getActive() {return this.active; }
    public void setActive(boolean active) {this.active = active;}

    //Constructor
    Tile(TileType t, float x, float y) {
        this.type = t;
        this.active = true;
        if (t != TileType.AIR) {
            this.bitmap = ResourceManager.getBitmap( Tile.tileTextures.get(t).intValue() );
            this.initRectF(x,y,bitmap.getWidth(),bitmap.getHeight());

            /*
             * We assume all tiles are going to be the same size....
             * Can't do this in the static block because it depends on ResourceManager being initialized. Bleh
             */
            Tile.SIZE = bitmap.getWidth();
        }
        else {
            this.initRectF(x,y,0,0);
        }
    }

    /* Copy constructor */
    Tile(Tile t) {
        super(t);
        this.type = t.type;
    }

}
