package edu.ufl;

import java.io.*;
import java.util.ArrayList;
import edu.ufl.Tile.TileType;

public class LevelReader {

    //Given a stream (presumably constructed from a file stream but it doesn't really matter)
    //interprets, constructs, and returns a Level object
    public static Level read(BufferedInputStream fh) throws IOException {
        int i;
        int x = 0;
        int y = 0;

        //make blank level map to pass into the Level object
        ArrayList<ArrayList<Tile>> map = new ArrayList<ArrayList<Tile>>();
        ArrayList<Tile> row = new ArrayList<Tile>();

        while ((i = fh.read()) != -1) {
            char c = (char)i;

            //If newline, start a new row and add the old to our map
            if (c == '\n') {
                y++;
                x=0;
                map.add(row);
                row = new ArrayList<Tile>();
            }

            //Else get the tile type and add that new tile to this row
            else {
                TileType type = charToTileType(c);
                row.add(new Tile(type,x,y));
                x++;
            }
        }

        //Since we read the rows from top to bottom, but our Y axis goes from bottom
        //to top, we need to reverse the rows.
        ArrayList<ArrayList<Tile>> reversemap = new ArrayList<ArrayList<Tile>>();
        for (i=map.size()-1; i>-1; i--) {
            reversemap.add(map.get(i));
        }

        //Create the new level and return it
        return new Level(reversemap);
    }

    //Given a char, returns the associated type. Defaults to AIR__
    private static TileType charToTileType(char c) {
        TileType type;
        switch (c) {
            case '#': type = TileType.BRICK; break;
            case '~': type = TileType.WATER; break;
            case '_': type = TileType.GRASS; break;
            default:  type = TileType.AIR__; break;
        }
        return type;
    }

}

