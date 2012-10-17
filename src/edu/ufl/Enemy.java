package edu.ufl;

import android.graphics.BitmapFactory;

public class Enemy extends LevelObject{

	public static enum EnemyType {
        BASIC,
		TOP
    }

	EnemyType type;
	public float range;
	public float movingLeft = -5; //moving left value with be negative else it will be moving right if it is positive
	
	public float orgX;

    private boolean isHarmful = true;
    private boolean topHarmful = false;

	public Enemy (float x, float y, char c) {
		this.type = findEnemyType(c);
		this.bitmap = BitmapFactory.decodeResource( ResourceManager.getResources(), R.drawable.enemy );
        this.initRectF(x,y-bitmap.getHeight(),bitmap.getWidth(),bitmap.getHeight());
        this.orgX = getX();
      //  this.setX(x);
      //  this.setY(y);
		
        if (type.equals(EnemyType.TOP))
            topHarmful = true;
		determineRange();
	}
	
	public EnemyType getEnemyType() { return type;}
	public float getRange() { return range; }
	public float isMovingLeft() { return movingLeft; }
	public boolean getIsHarmful() { return isHarmful; }
	public boolean getTopHarmful() { return topHarmful; }

	
	public void setEnemyType(EnemyType type) { this.type = type; }
	public void setRange(float range) { this.range = range; }
	public void setDirection(float movingLeft) { this.movingLeft = movingLeft; }
	
	public void determineRange() {
		if (type.equals(EnemyType.BASIC))
			range = 50;
		else
			range = 20;
	}
	
	public void changeDirection() {
		movingLeft = -1*movingLeft;
	}
	
	public void kill() {
		//the enemy dies
	}
	
	public void update(){
		//updates the movement of the enemies
		if(Math.abs(getX() - orgX) >= this.range){
			changeDirection();
		}
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
}
