package edu.ufl;

import java.util.ArrayList;
import edu.ufl.Tile.TileType;
import edu.ufl.Sprite.SpriteType;
import android.graphics.Canvas;

public class Enemy extends LevelObject{

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

	public void changeDirection() {
        super.changeDirection();
		movingLeft = -1*movingLeft;
	}
	
	public void kill() {
		//the enemy dies
	}
	
	public void update(){
		//updates the movement of the enemies
		setX(this.getX() + isMovingLeft());
		
		dy += Constants.GRAVITY;
        this.sprite.update();
	}
	
	public void collideLeft(LevelObject lo){
	    super.collideLeft(lo);
	    changeDirection();
	}
	public void collideRight(LevelObject lo){
	    super.collideRight(lo);
	    changeDirection();
	}

    public Tile getFromArray (ArrayList<Tile> toCollide, int i) {
        Tile temp = toCollide.get(i);
        if (temp.getType().equals(TileType.CHECKPOINT))
            return null;
        if (temp.getType().equals(TileType.LEVELEND))
            return null;
        return temp;
    }

    public void draw(Canvas canvas, Camera camera) {
        this.sprite.draw(this.x,this.y,canvas,camera);
    }
}
