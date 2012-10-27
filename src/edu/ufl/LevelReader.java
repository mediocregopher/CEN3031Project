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
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        ArrayList<Tile> row = new ArrayList<Tile>();

        //Declare an albert, put him in a precarious place
        Albert albert = new Albert(50,50);

        //Declare a new tiletype to initialize the Tile.SIZE variable
        new Tile(TileType.GROUND,0,0);

        while ((i = fh.read()) != -1) {
            char c = (char)i;

            //If newline, start a new row and add the old to our map
            if (c == '\n') {
                y++;
                x=0;
                map.add(row);
                row = new ArrayList<Tile>();
            }

            //Else get the level object and add it
            else {
                switch (c) {
                    //ALBERT!
                    case 'a':
                        albert.setX(x*Tile.SIZE);
                        albert.setY(y*Tile.SIZE-(albert.getHeight() - Tile.SIZE - 5));
                        break;
                        
					case 'e':
						enemyCase(c, enemies, x, y);
						break;
						
					case 't':
					    enemyCase(c, enemies, x, y);
                        break;
                }
                //Assume it's a tile if nothing else
                TileType type = charToTileType(c);
                row.add(new Tile(type,x*Tile.SIZE,y*Tile.SIZE));
                x++;
            }
        }

        //Create the new level and return it
        return new Level(map, enemies, albert);
    }

    public static Level blankLevel() {
        ArrayList<ArrayList<Tile>> map = new ArrayList<ArrayList<Tile>>();
        Albert albert = new Albert(50,50);
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        return new Level(map,enemies,albert);
    }

    //Given a char, returns the associated type. Defaults to AIR
    private static TileType charToTileType(char c) {
        TileType type;
        switch (c) {
            case '#': type = TileType.GROUND; break;
            case '*': type = TileType.AIBOUND; break;
            case 'c': type = TileType.CHECKPOINT; break;
            default:  type = TileType.AIR;  break;
        }
        return type;
    }
	
    public static void enemyCase(char c, ArrayList<Enemy> enemies, float x, float y) {
        enemies.add(new Enemy(x*Tile.SIZE, y*(Tile.SIZE)+Tile.SIZE, c));
    }

}

