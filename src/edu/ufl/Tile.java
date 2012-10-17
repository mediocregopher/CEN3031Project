package edu.ufl;

import java.util.HashMap;
import android.graphics.BitmapFactory;

public class Tile extends LevelObject{

    public static float SIZE;

    public static enum TileType {
        AIR,
        GROUND
    }

    private static HashMap<TileType,Integer> tileTextures;
    static {
        tileTextures = new HashMap<TileType,Integer>(10); //Increase if we have over 10 tile types
        tileTextures.put(TileType.GROUND, R.drawable.ground_tile);
    }

    //Extra Members
    private TileType type;
    public TileType getType() { return this.type; }
    public void setType(TileType type) { this.type = type; }

    //Constructor
    Tile(TileType t, float x, float y) {
        this.type = t;
        if (t != TileType.AIR) {
            this.bitmap = BitmapFactory.decodeResource( ResourceManager.getResources(),
                                                        Tile.tileTextures.get(t).intValue() );
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

}
