package edu.ufl;

import android.graphics.RectF;
import android.graphics.Paint;
import android.graphics.Canvas;

public class LevelObject {

    private RectF object;

    // Physic constants
    private static final double PHYS_GRAVITY = 9f/1000f;    // 0.009 pixels/millisecond^2 (remember +y moves down)
    private static final double PHYS_PLAYER_SPEED = 250f/1000f; // 250 pixels/millisecond
    private static final double PHYS_PLAYER_JUMP = 1;
    private static final double PHYS_ELAPSED_TIME = 1000/(float)GameThread.FPS;

    // Velocity dx, dy
    private double dx;
    private double dy;
    
    // Minimum y - placeholder until we get level collision
    private static final int MAX_Y = 300;

    LevelObject(float x, float y, float w, float h) {
        object = new RectF(x,y,x+w,y+h);
    }

    public void update(GamePanel gamePanel) {
        if      (gamePanel.controller.isLeftPressed())  { dx = -PHYS_PLAYER_SPEED; }
        else if (gamePanel.controller.isRightPressed()) { dx =  PHYS_PLAYER_SPEED; }
        else    { dx = 0; }

        if (gamePanel.controller.isJumpPressed()) { dy = -PHYS_PLAYER_JUMP; }

        dy += PHYS_GRAVITY * PHYS_ELAPSED_TIME;
        
        float x = (float) (object.centerX() + dx * PHYS_ELAPSED_TIME);
        float y = (float) (object.centerY() + dy * PHYS_ELAPSED_TIME);
        
        
        // check for collisions
        if (x < 0) {
            x=0;
        } else if (x > gamePanel.getWidth()) {
            x = gamePanel.getWidth();
        }
        
        if (y > MAX_Y) {
            y = MAX_Y;
            dy = 0;
        }
        
        object.offsetTo(x - object.width()/2, y - object.height()/2);
    }

    public void draw(Canvas canvas) {
        Paint color = new Paint();
        color.setARGB(255, 0x04, 0x5f, 0x18);
        canvas.drawRect(object, color);
    }

}
