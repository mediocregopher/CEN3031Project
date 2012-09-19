package edu.ufl;

import android.graphics.RectF;
import android.graphics.Paint;
import android.graphics.Canvas;

public class LevelObject {

    private RectF object;

    // Physic constants
    private static final float GRAVITY = 9f/1000f;    // 0.009 pixels/millisecond^2 (remember +y moves down)
    private static final float SPEED = 250f/1000f; // 250 pixels/millisecond
    private static final float JUMP_SPEED = 1;
    private static final float FPS_PERIOD = (float)GameThread.FPS_PERIOD;

    private float oldx;
    private float oldy;
    private float x;
    private float y;
    private float h;
    private float w;

    // Velocity dx, dy
    private float dx;
    private float dy;
    

    LevelObject(float x, float y, float w, float h) {
        this.x    = x;
        this.oldx = x;
        this.y    = y;
        this.oldy = y;
        this.w    = w;
        this.h    = h;
        object = new RectF(x,y,x+w,y+h);
    }

    public float getX()      { return x; }
    public float getY()      { return y; }
    public float getWidth()  { return w; }
    public float getHeight() { return h; }

    public void update(GamePanel gamePanel) {
        if      (gamePanel.controller.isLeftPressed())  { dx = -SPEED; }
        else if (gamePanel.controller.isRightPressed()) { dx =  SPEED; }
        else    { dx = 0; }

        if (gamePanel.controller.isJumpPressed()) { dy = -JUMP_SPEED; }

        dy += GRAVITY * FPS_PERIOD;
        
        x = object.centerX() + dx * FPS_PERIOD;
        y = object.centerY() + dy * FPS_PERIOD;
        
    }

    public void commitUpdate() {
        oldx = x;
        oldy = y;
        //GameLog.d("LevelObject", "committing: " + String.valueOf(x) + ", " + String.valueOf(y));
        //WHY DOESN'T THIS WORK?
        //object.offsetTo(x, y);
        object.offsetTo(x - object.width()/2, y - object.height()/2);
    }

    public void resetX() { x = oldx; }
    public void resetY() { y = oldy; }

    public void draw(Canvas canvas) {
        Paint color = new Paint();
        color.setARGB(255, 0x04, 0x5f, 0x18);
        canvas.drawRect(object, color);
    }

}
