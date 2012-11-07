package edu.ufl;

import java.util.ArrayList;

import android.graphics.RectF;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import edu.ufl.Sprite.SpriteType;

public class LevelObject {

    protected RectF rectf;

    protected float x;
    protected float y;
    protected float h;
    protected float w;

    // Velocity dx, dy
    protected float dx;
    protected float dy;
    
    //Need to get rid of bitmap
    protected Bitmap bitmap;
    protected Sprite sprite;
    
    // Harmful parameters
    protected boolean isHarmful = false;
    protected boolean topHarmful = false;

    protected boolean facingLeft = false;
    
    

    LevelObject() { /* Don't do shit */ }
    LevelObject(float x, float y, float w, float h) {
        this.initRectF(x,y,w,h);
    }
    /* Copy constructor (fuckin java) */
    LevelObject(LevelObject lo) {
        this.initRectF(lo.x,lo.y,lo.w,lo.h);
        this.dx = lo.dx;
        this.dy = lo.dy;
        this.bitmap = lo.bitmap;
        if (lo.sprite != null) this.sprite = new Sprite(lo.sprite);
        this.isHarmful = lo.isHarmful;
        this.topHarmful = lo.topHarmful;
        this.facingLeft = lo.facingLeft;
    }

    public void initRectF(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        rectf  = new RectF(x,y,x+w,y+h);
    }

    public float getX()      { return x;  }
    public float getY()      { return y;  }
    public void  setX(float x) { this.x = x; commitPosition(); }
    public void  setY(float y) { this.y = y; commitPosition(); }

    public float getDX()     { return dx; }
    public float getDY()     { return dy; }
    public void  setDX(float dx) {this.dx = dx; }
    public void  setDY(float dy) {this.dy = dy; }

    public float getWidth()  { return w;  }
    public float getHeight() { return h;  }
	
    public RectF getRectF() { return rectf; }
    public RectF getHitbox() { return getRectF(); }
    
    public boolean getIsHarmful() { return isHarmful; }
    public boolean getTopHarmful() { return topHarmful; }

    public boolean getFacingLeft() { return facingLeft; }
    public boolean getFacingRight() { return !facingLeft; }
    public void faceLeft() { 
        if (sprite != null) { sprite.faceLeft(); }
        facingLeft = true; 
    }
    public void faceRight() {
        if (sprite != null) { sprite.faceRight(); }
        facingLeft = false;
    }
    public void changeDirection() {
        if (this.facingLeft) this.faceRight();
        else this.faceLeft();
    }

    public void draw(Canvas canvas, Camera camera) {
        camera.draw(null,this.getRectF(),bitmap,canvas);
    }

    protected void commitPosition() {
        rectf.offsetTo(Math.round(x),Math.round(y));
    }
	
    protected void changeSprite(SpriteType stype) {
        if (this.sprite.getType() != stype) {
            this.sprite = new Sprite(stype);
        }
    }

    protected void changeSpriteKeepDirection(SpriteType stype) {
        if (this.sprite.getType() != stype) {
            Sprite oldsprite = this.sprite;
            this.sprite = new Sprite(stype);
            if (oldsprite.getFlipped() != this.sprite.getFlipped()) 
                this.sprite.flip();
        }
    }
    
    public void collideTop(LevelObject lo) {
        setY(lo.getRectF().top - getHeight());
        setDY(0);
    }
    
    public void collideBottom(LevelObject lo) {
        setY(lo.getRectF().bottom);
        if (getDY() < 0) setDY(0);
    }
    
    public void collideLeft(LevelObject lo) {
        setX(lo.getRectF().left - getWidth());
        setDX(0);
    }
    
    public void collideRight(LevelObject lo) {
        setX(lo.getRectF().right);
        setDX(0);
    }

    public Tile getFromArray (ArrayList<Tile> toCollide, int i) {
        return toCollide.get(i);
    }
}
