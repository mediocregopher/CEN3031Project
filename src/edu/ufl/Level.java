package edu.ufl;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Canvas;

import edu.ufl.Tile.TileType;

/*

Class for containing information about current playable level. It's returned from the LevelReader's
read method. It has three accessor methods:

getMaxY: returns height of the level (0-indexed)
getMaxX: returns width  of the level (0-indexed)
get: given an x and a y, returns the tile at that point. If that point is out of bounds
     constructs and returns an air tile.

The axis for the level as it is read from a level file looks like this:

Y
  ^
  |
  |
  |
  ------->
           X
*/
public class Level {

    //Number of tiles to either side of the camera we will also call update on
    private static final int UPDATE_OFFSET = 3;

    //Members
    private int maxX;
    private int maxY;
    private ArrayList<ArrayList<Tile>> map;

    //Getters/Setters
    //(The maxX/Y variables don't have setters because their value is inherent in
    // the map, so changing them could only make them wrong).
    public int  getMaxX() { return this.maxX; }
    public int  getMaxY() { return this.maxY; }

    public int getMaxPixelsX() { return (int)Tile.SIZE*getMaxX(); }
    public int getMaxPixelsY() { return (int)Tile.SIZE*getMaxY(); }

    public LevelObject albert;

    //Everytime update is called it updates this list with objects we should actually check.
    //Also looked at by draw, which is why it's an object variable.
    private ArrayList<LevelObject> toLookAt;

    //Constructor
    public Level(ArrayList<ArrayList<Tile>> map) {
        this.map = map;
        int maxX = 0;
        int maxY = map.size();
        for (int i=0; i<maxY; i++) {
            if (map.get(i).size() > maxX) { maxX = map.get(i).size(); }
        }
        this.maxX = maxX;
        this.maxY = maxY;
        this.albert = new LevelObject(10,10,40,90);
    }

    //Get an arbitrary tile in the map, assumes AIR if out of bounds
    public Tile get(int X, int Y) {
        Tile ret;
        try {
            ret = map.get(Y).get(X);
        } catch(IndexOutOfBoundsException e) {
            ret = new Tile(TileType.AIR__, X*Tile.SIZE, Y*Tile.SIZE);
        }
        return ret;
    }

    public void update(GamePanel gamePanel, Camera camera) {
        albert.update(gamePanel.controller);

        // get level dimensions
        int MAX_X = getMaxPixelsX();
        int MAX_Y = getMaxPixelsY();

        // Assume Albert can't jump
        albert.setCanJump(false);
        
        if (albert.getX() < 0) {
            albert.setX(0);
        }
        else if ((albert.getX()+albert.getWidth()) > MAX_X)  {
            albert.setX(MAX_X-albert.getWidth());
        }
        
        if (albert.getY()+albert.getHeight() > MAX_Y) {
            // TODO: fall death
            albert.setY(MAX_Y-albert.getHeight());
            albert.setDY(0);
            albert.setCanJump(!gamePanel.controller.isJumpPressed());
        }

        toLookAt = new ArrayList<LevelObject>();

        int xstart = (int)( camera.getX()/Tile.SIZE);
        if (xstart - 5 >=0) xstart -= 5;
        int xend   = xstart + (int)( (camera.getX()+gamePanel.getWidth()) / Tile.SIZE) + 5;


        for (int i=xstart; i<=xend; i++) {
            for (int j=0; j<getMaxY(); j++) {
                Tile tile = get(i,j);
                if (tile.getType() != TileType.AIR__) { toLookAt.add(tile); }
            }
        }

        RectF albertRectF = albert.getRectF();
        for (int i=0; i<toLookAt.size(); i++) {
            RectF tileRectF   = toLookAt.get(i).getRectF();
            switch(Util.intersect(albertRectF,tileRectF)) {
                case NONE:   break;

                case TOP:    albert.setY(tileRectF.top - albert.getHeight());
                             albert.setDY(0);
                             albert.setCanJump(!gamePanel.controller.isJumpPressed());
                             break;

                case BOTTOM: albert.setY(tileRectF.bottom);
                             if (albert.getDY() < 0) albert.setDY(0);
                             break;

                case LEFT:   albert.setX(tileRectF.left - albert.getWidth());
                             albert.setDX(0);
                             break;

                case RIGHT:  albert.setX(tileRectF.right);
                             albert.setDX(0);
                             break;
            }
        }

        camera.offset(albert,this);

    }

    public void draw(Canvas canvas, Camera camera) {
        canvas.drawARGB(255, 0x38, 0xAC, 0xEC);
        albert.draw(canvas,camera);
        for (int i=0; i<toLookAt.size(); i++) {
            toLookAt.get(i).draw(canvas,camera);
        }
    }
}
