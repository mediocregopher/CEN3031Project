package edu.ufl;

import android.graphics.RectF;
import android.graphics.Canvas;
import android.graphics.Bitmap;

public class LevelObject {

    protected RectF rectf;

    protected float x;
    protected float y;
    protected float h;
    protected float w;

    // Velocity dx, dy
    protected float dx;
    protected float dy;
    
    protected Bitmap bitmap;

    LevelObject() { /* Don't do shit */ }
    LevelObject(float x, float y, float w, float h) {
        this.initRectF(x,y,w,h);
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

    public void draw(Canvas canvas, Camera camera) {
        camera.draw(this.getRectF(),bitmap,canvas);
    }

    protected void commitPosition() {
        rectf.offsetTo(Math.round(x),Math.round(y));
    }
	

}
