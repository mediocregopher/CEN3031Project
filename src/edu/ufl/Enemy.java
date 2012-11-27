package edu.ufl;

import java.util.ArrayList;

import android.graphics.Canvas;
import edu.ufl.Sprite.SpriteType;
import edu.ufl.Tile.TileType;

public class Enemy extends LevelObject {

	public float movingLeft = 5; //moving left value with be negative else it will be moving right if it is positive
	
    protected boolean isHarmful = true;
    protected boolean topHarmful = false;

    protected SpriteType standingSprite;
    protected SpriteType walkingSprite;
    protected SpriteType hurtSprite;

	public Enemy (float x, float y) {
        this.x = x;
        this.y = y;
	}

    /* Copy Constructor */
    public Enemy(Enemy e) {
        super(e);
        this.movingLeft = e.movingLeft;
        this.isHarmful  = e.isHarmful;
        this.topHarmful = e.topHarmful;
        this.standingSprite = e.standingSprite;
        this.walkingSprite  = e.walkingSprite;
        this.hurtSprite     = e.hurtSprite;
    }
	
	public float isMovingLeft() { return movingLeft; }
	public boolean getIsHarmful() { return isHarmful; }
	public boolean getTopHarmful() { return topHarmful; }

	public void setDirection(float movingLeft) { this.movingLeft = movingLeft; }
	
    public void initSprite() {
        this.sprite = new Sprite(this.walkingSprite);
    }
	
	public void kill() {
		//the enemy dies
	}
	
	public void update(){
		//updates the movement of the enemies
		setX(this.getX() + isMovingLeft());
		
		dy += (3f/4f)*Constants.GRAVITY;
		setY(y + dy * Constants.FPS_PERIOD);
        this.sprite.update();
	}
	
	public void collideLeft(LevelObject lo){
	    super.collideLeft(lo);
	    faceLeft();
        if (movingLeft > 0) movingLeft *= -1;
	}
	public void collideRight(LevelObject lo){
	    super.collideRight(lo);
	    faceRight();
	    if (movingLeft < 0) movingLeft *= -1;
	}

    public Tile getFromArray (ArrayList<Tile> toCollide, int i) {
        Tile temp = toCollide.get(i);
        if (temp.getType().equals(TileType.CHECKPOINT))
            return null;
        if (temp.getType().equals(TileType.LEVELEND))
            return null;
        if (temp.getType().equals(TileType.FOOTBALL))
            return null;
        return temp;
    }

    public void draw(Canvas canvas, Camera camera) {
        this.sprite.draw(this.x,this.y,canvas,camera);
    }

    public char getType() {
        GameLog.d("Enemy", "ONE OF THE ENEMY CLASSES DOES NOT IMPLEMENT getType()");
        return 'a';
    }
}
