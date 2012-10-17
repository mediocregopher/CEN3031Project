package edu.ufl;

import android.graphics.Canvas;
import edu.ufl.Sprite.SpriteType;

public class Albert extends LevelObject {

    // Physic constants
    private float SPEED = 200f/1000f;
    private float JUMP_SPEED = 500f/1000f;

    private GameController controller;
    private boolean canJump;
    private boolean dead = false;

    public float getJumpSpeed() { return JUMP_SPEED; }
    public void setCanJump(boolean canJump) { this.canJump = canJump; }

    public boolean isDead() { return dead; }

    Albert(float x, float y) {
        this.sprite = new Sprite(SpriteType.ALBERT);
        this.initRectF(x,y,this.sprite.getWidth(),this.sprite.getHeight());

        //Scale *SPEED's
        SPEED = ResourceManager.dpToPx(SPEED);
        JUMP_SPEED = ResourceManager.dpToPx(JUMP_SPEED);
    }
    
    public void update(GameController controller) {
        this.controller = controller;
        
        if (!dead) {
            if      (controller.isLeftPressed())  { dx = -SPEED; }
            else if (controller.isRightPressed()) { dx =  SPEED; }
            else    { dx = 0; }

            if (dx == 0) { this.changeSprite(SpriteType.ALBERT);         }
            else {         this.changeSprite(SpriteType.ALBERT_WALKING); }
        
            if (controller.isSprinting()) { dx *= 2; }

            if (controller.isJumpPressed() && canJump) { 
                   dy = -JUMP_SPEED;
                   SoundManager.playSound(2, 1.5f, false);
            }
        }

        dy += Constants.GRAVITY;
        this.setX(rectf.left + dx * Constants.FPS_PERIOD);
        this.setY(rectf.top  + dy * Constants.FPS_PERIOD);
        this.sprite.update(); 
    }

    public void draw(Canvas canvas, Camera camera) {
        sprite.draw(this.getRectF(),canvas,camera);
    }
    
    public void kill() {
        dead = true;
        SoundManager.pauseMedia();
        SoundManager.playSound(4, 1.0f, false);
        // TODO: Decrement lives
        this.changeSprite(SpriteType.ALBERT_DEAD);
        dx = 0;
        dy = -JUMP_SPEED;
    }
    
    @Override
    public void collideTop(LevelObject lo) {
        super.collideTop(lo);
        setCanJump(!controller.isJumpPressed());
    }

}
