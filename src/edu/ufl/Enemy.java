package edu.ufl;

import java.util.HashMap;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Enemy extends LevelObject{
	EnemyType type;
	public float range;
	public boolean movingLeft;//if true its moving -x else its moving +x
	
	public static enum EnemyType {
        BASIC,
		TOP
    }
	
	public Enemy (float x, float y, char c) { 
		type = findEnemyType(c);
		this.bitmap = BitmapFactory.decodeResource( ResourceManager.getResources(), R.drawable.enemy );
        this.initRectF(x,y,bitmap.getWidth(),bitmap.getHeight());
		
		 isHarmful = true;
		 if (type.equals(EnemyType.TOP))
			topHarmful = true;
		determineRange();
	}
	
	public EnemyType getEnemyType() { return type;}
	public float getRange() { return range; }
	public boolean getDirection() { return movingLeft; }
	
	public void setEnemyType(EnemyType type) { this.type = type; }
	public void setRange(float range) { this.range = range; }
	public void setDirection(boolean movingLeft) { this.movingLeft = movingLeft; }
	
	public void determineRange() {
		if (type.equals(EnemyType.BASIC))
			range = 50;
		else
			range = 20;
	}
	
	public void changeDirecetions() {
		movingLeft = !movingLeft;
	}
	
	public void kill() {
		//the enemy dies
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