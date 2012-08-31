package edu.ufl;

import java.util.ArrayList;
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

    //Members
    private int maxX;
    private int maxY;
    private ArrayList<ArrayList<Tile>> map;

    //Getters/Setters
    //(The maxX/Y variables don't have setters because their value is inherent in
    // the map, so changing them could only make them wrong).
    public int  getMaxX() { return this.maxX; }
    public int  getMaxY() { return this.maxY; }

    //Get an arbitrary tile in the map, assumes AIR if out of bounds
    public Tile get(int X, int Y) {
        Tile ret;
        try {
            ret = map.get(Y).get(X);
        } catch(IndexOutOfBoundsException e) {
            ret = new Tile(TileType.AIR__, X, Y);
        }
        return ret;
    }

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
    }

    //Stupid debugging method
    public void print() {
        System.out.print(this.getMaxX());
        System.out.print(" X ");
        System.out.print(this.getMaxY());
        System.out.print('\n');
        for (int i=this.getMaxY()-1; i>-1; i--) {
            for (int j=0; j<this.getMaxX(); j++) {
                System.out.print(this.get(j,i).getType().toString());
                System.out.print(' ');
            }
            System.out.print('\n');
        }
    }
}
