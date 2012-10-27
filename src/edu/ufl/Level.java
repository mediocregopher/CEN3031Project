package edu.ufl;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Bitmap;

import edu.ufl.Tile.TileType;
import edu.ufl.Util.IntersectRet;

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
	private ArrayList<Enemy> enemies;
	
    //Getters/Setters
    //(The maxX/Y variables don't have setters because their value is inherent in
    // the map, so changing them could only make them wrong).
    public int  getMaxX() { return this.maxX; }
    public int  getMaxY() { return this.maxY; }

    public int getMaxPixelsX() { return (int)Tile.SIZE*getMaxX(); }
    public int getMaxPixelsY() { return (int)Tile.SIZE*getMaxY(); } 
    
    public  Albert albert;
    private Bitmap background;
    private Bitmap clouds;

    private Tile   checkpoint;
    private ArrayList<Enemy> checkpointEnemies;
    private Albert checkpointAlbert;
    
    private boolean needsReset = false;
    public boolean needsReset() { return needsReset; } 

    private boolean isDone = false;
    public boolean isDone() { return isDone; }

    //Everytime update is called it updates this list with objects we should actually check.
    //Also looked at by draw, which is why it's an object variable.
    private ArrayList<Tile> tilesToLookAt;
	private ArrayList<Enemy> enemiesToLookAt;

    //Constructor
    public Level(ArrayList<ArrayList<Tile>> map, ArrayList<Enemy> enemies, Albert albert) {
        this.map = map;
		this.enemies = enemies;
        int maxX = 0;
        int maxY = map.size();
        for (int i=0; i<maxY; i++) {
            if (map.get(i).size() > maxX) { maxX = map.get(i).size(); }
        }
		
        this.maxX = maxX;
        this.maxY = maxY;
        this.albert = albert;

        this.background = ResourceManager.getBitmap(R.drawable.background);
        this.clouds = ResourceManager.getBitmap(R.drawable.clouds);
    }

    //Get an arbitrary tile in the map, assumes AIR if out of bounds
    public Tile get(int X, int Y) {
        Tile ret;
        try {
            ret = map.get(Y).get(X);
        } catch(IndexOutOfBoundsException e) {
            ret = new Tile(TileType.AIR, X*Tile.SIZE, Y*Tile.SIZE);
        }
        return ret;
    }
	
	public Enemy get(int X, boolean t) {
		Enemy ret;
		try {
			ret = enemies.get(X);
		} catch (IndexOutOfBoundsException e) {
			ret = null;
		}
		return ret;
	}
    public void update(GamePanel gamePanel, Camera camera) {
        albert.update(gamePanel.controller);

        // get level dimensions
        int MAX_X = getMaxPixelsX();
        int MAX_Y = getMaxPixelsY();

        // BEGIN Boundary conditions
        albert.setCanJump(false);

        if (albert.getX() < 0) {
            albert.setX(0);
        }
        else if ((albert.getX()+albert.getWidth()) > MAX_X)  {
            albert.setX(MAX_X-albert.getWidth());
        }

        if (albert.getY()+albert.getHeight() > MAX_Y) {
            if (albert.isDead()) {
                if (this.checkpointAlbert != null) {
                    this.albert = this.checkpointAlbert;
                    this.enemies = this.checkpointEnemies;
                }
                else {
                    needsReset = true;
                }
            } else {
                albert.kill();
            }
        }
        // END boundary conditions

        // Only do these if albert is alive
        // Allows the death animation to run
        if (!albert.isDead()) {

            tilesToLookAt = new ArrayList<Tile>();

            int xstart = (int)( camera.getX()/Tile.SIZE);
            if (xstart - UPDATE_OFFSET >=0) xstart -= UPDATE_OFFSET;
            int xend   = xstart + (int)( ((float)gamePanel.getWidth()) / Tile.SIZE) + UPDATE_OFFSET*2;

            for (int i=xstart; i<=xend; i++) {
                for (int j=0; j<getMaxY(); j++) {
                    Tile tile = get(i,j);
                    if (tile.getType() != TileType.AIR) { tilesToLookAt.add(tile); }
                }
            }

            enemiesToLookAt = new ArrayList<Enemy>();

            for (int i = 0; i <= enemies.size(); i++) {
                Enemy enemy = get(i,true);
                if (enemy != null) {
                    if (enemy.getX() > xstart*Tile.SIZE && enemy.getX() < xend*Tile.SIZE) {
                        enemy.update();
                        tileCollide(enemy,tilesToLookAt);
                        enemiesToLookAt.add(enemy);
                    }
                }
            }

            tileCollide(albert,tilesToLookAt);
            
            albertEnemyCollision();
            
            camera.offset(albert,this);

        }
    }

    public void draw(Canvas canvas, Camera camera) {
        canvas.drawARGB(255, 0x81, 0x43, 0xb6);
        camera.drawBackground(background, clouds,getMaxPixelsY(),canvas);
        for (int i=0; i<tilesToLookAt.size(); i++) {
            tilesToLookAt.get(i).draw(canvas,camera);
        }
        for (int i=0; i<enemiesToLookAt.size(); i++) {
            enemiesToLookAt.get(i).draw(canvas,camera);
        }
        albert.draw(canvas,camera);
    }
	
	public void killEnemy(int index) {
		enemies.remove(index);
	}
	
	public void killEnemy(Enemy e) {
		enemies.remove(e);
	}
	
    private void tileCollide(LevelObject obj, ArrayList<Tile> toCollide) {
        RectF objRectF = obj.getHitbox();
        
        for (int i = 0; i < toCollide.size(); i++) {
            Tile tile = obj.getFromArray(toCollide, i);
            if (tile == null) {
                continue;
            }

            IntersectRet intret = Util.intersect(objRectF, tile.getRectF());
            if (intret != IntersectRet.NONE) {

                if (obj instanceof Albert) {
                    if (tile.getType() == TileType.CHECKPOINT) {
                        gotCheckpoint();                    
                        continue;
                    }
                    else if (tile.getType() == TileType.LEVELEND) {
                        isDone = true;
                        continue;
                    }
                }

                switch (intret) {
                case TOP:
                    obj.collideTop(tile);
                    break;

                case BOTTOM:
                    obj.collideBottom(tile);
                    break;

                case LEFT:
                    obj.collideLeft(tile);
                    break;

                case RIGHT:
                    obj.collideRight(tile);
                    break;
                }
            }
        }
    }
    
    
    private void albertEnemyCollision() {
        RectF albertRectF = albert.getHitbox();
        for (int i = 0; i < enemiesToLookAt.size(); i++) {
            RectF enemyRectF = enemiesToLookAt.get(i).getHitbox();
            switch (Util.intersect(albertRectF, enemyRectF)) {
            case NONE: break;

            case TOP: albert.setY(enemyRectF.top - albert.getHeight());
            if (enemiesToLookAt.get(i).getTopHarmful()) {
                albert.kill();
            }
            else {
                albert.setDY(-albert.getJumpSpeed() / 2);
                killEnemy(enemiesToLookAt.get(i));
                SoundManager.playSound(3, 1.0f, false);
            }
            break;

            default:    
                // Shouldn't enemy.getIsHarmful always be true?
                if (enemiesToLookAt.get(i).getIsHarmful()) {
                    albert.kill();
                }
                else {
                    killEnemy(enemiesToLookAt.get(i));
                }
                break;
            }
        }
    }

    private void gotCheckpoint() {
        this.checkpointAlbert = new Albert(this.albert);
        this.checkpointEnemies = new ArrayList<Enemy>();
        for (int i = 0; i < this.enemies.size(); i++) {
            this.checkpointEnemies.add(new Enemy(this.enemies.get(i)));
        }
    }
    
}
