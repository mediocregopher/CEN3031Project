package edu.ufl;

import android.graphics.RectF;
import android.graphics.Canvas;

public class LevelObject {

    private RectF rectf;

    // Physic constants
    private static final float FPS_PERIOD = (float)GameThread.FPS_PERIOD;
    private static final float GRAVITY = 1000f/1000f * (FPS_PERIOD/1000f);    // 0.009 pixels/millisecond^2 (remember +y moves down)
    private static final float SPEED = 200f/1000f; // 250 pixels/millisecond
    private static final float JUMP_SPEED = 600f/1000f;


    private float x;
    private float y;
    private float h;
    private float w;

    // Velocity dx, dy
    private float dx;
    private float dy;
    
    private boolean canJump;

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
    
    public void setCanJump(boolean canJump) { this.canJump = canJump; }

    public void update(GameController controller) {
        if      (controller.isLeftPressed())  { dx = -SPEED; }
        else if (controller.isRightPressed()) { dx =  SPEED; }
        else    { dx = 0; }
        
        if (controller.isSprinting()) { dx *= 2; }

        if (controller.isJumpPressed() && canJump) { dy = -JUMP_SPEED; }

        dy += GRAVITY;
        
        this.setX(rectf.left + dx * FPS_PERIOD);
        this.setY(rectf.top  + dy * FPS_PERIOD);
        
    }

    public void draw(Canvas canvas,Camera camera) {
        camera.drawRectF(this.getRectF(),canvas);
    }

    private void commitPosition() {
        rectf.offsetTo(Math.round(x),Math.round(y));
    }


}
