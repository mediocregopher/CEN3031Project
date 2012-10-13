package edu.ufl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Albert extends LevelObject {

    // Physic constants
    private static final float SPEED = 200f/1000f; // 250 pixels/millisecond
    private static final float JUMP_SPEED = 600f/1000f;


    private boolean canJump;
    private boolean dead = false;

    public float getJumpSpeed() { return JUMP_SPEED; }
    public void setCanJump(boolean canJump) { this.canJump = canJump; }

    public boolean isDead() { return dead; }

    Albert(float x, float y) {
        this.bitmap = BitmapFactory.decodeResource( ResourceManager.getResources(),
                                                    R.drawable.albert );
        this.initRectF(x,y,bitmap.getWidth(),bitmap.getHeight());
    }
    
    public void update(GameController controller) {
        if (!dead) {
            if      (controller.isLeftPressed())  { dx = -SPEED; }
            else if (controller.isRightPressed()) { dx =  SPEED; }
            else    { dx = 0; }
        
            if (controller.isSprinting()) { dx *= 2; }

            if (controller.isJumpPressed() && canJump) { dy = -JUMP_SPEED; }
        }

        dy += Constants.GRAVITY;
        this.setX(rectf.left + dx * Constants.FPS_PERIOD);
        this.setY(rectf.top  + dy * Constants.FPS_PERIOD);
        
    }
    
    public void kill() {
        dead = true;
        // TODO: Play death sound
        // TODO: Decrement lives
        this.bitmap = BitmapFactory.decodeResource( ResourceManager.getResources(),
                                                    R.drawable.albert_dead );
        dx = 0;
        dy = -JUMP_SPEED;
    }

}
