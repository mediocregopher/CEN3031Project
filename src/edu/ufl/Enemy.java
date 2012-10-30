package edu.ufl;

import java.util.ArrayList;
import edu.ufl.Tile.TileType;

public class Enemy extends LevelObject{

	public static enum EnemyType {
        BASIC,
		TOP
    }

	EnemyType type;
	public float movingLeft = -5; //moving left value with be negative else it will be moving right if it is positive
	
    private boolean isHarmful = true;
    private boolean topHarmful = false;

	public Enemy (float x, float y, char c) {
		this.type = findEnemyType(c);
        this.bitmap = ResourceManager.getBitmap(R.drawable.enemy);
        this.initRectF(x,y,bitmap.getWidth(),bitmap.getHeight());
		
        if (type.equals(EnemyType.TOP))
            topHarmful = true;
	}

    /* Copy Constructor */
    public Enemy(Enemy e) {
        super(e);
        this.type = e.type;
        this.isHarmful  = e.isHarmful;
        this.topHarmful = e.topHarmful;
    }
	
	public EnemyType getEnemyType() { return type;}
	public float isMovingLeft() { return movingLeft; }
	public boolean getIsHarmful() { return isHarmful; }
	public boolean getTopHarmful() { return topHarmful; }

	
	public void setEnemyType(EnemyType type) { this.type = type; }
	public void setDirection(float movingLeft) { this.movingLeft = movingLeft; }
	

	public void changeDirection() {
		movingLeft = -1*movingLeft;
	}
	
	public void kill() {
		//the enemy dies
	}
	
	public void update(){
		//updates the movement of the enemies
		setX(this.getX() + isMovingLeft());
		
		dy += Constants.GRAVITY;
	}
	
	public EnemyType findEnemyType(char c) {
		EnemyType eT;
		switch (c) {
			case 'e': 
				eT = EnemyType.BASIC;
				break;
			case 't':
				eT = EnemyType.TOP;
				break;
			default:
				eT = EnemyType.BASIC;
				break;
		}
		return eT;
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
}
