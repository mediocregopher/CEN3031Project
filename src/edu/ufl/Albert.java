package edu.ufl;

import java.util.ArrayList;

import android.graphics.Canvas;
import edu.ufl.Sprite.SpriteType;
import edu.ufl.Tile.TileType;

public class Albert extends LevelObject {

    // Physic constants
    private float SPEED = 200f/1000f;
    private float JUMP_SPEED = 610f/1000f;

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
            /* If left pressed, face left */
            if (controller.isLeftPressed())  { 
                dx = -SPEED;
                this.sprite.faceLeft();
                this.handleMovementSpritesAndSprinting(controller);
            }

            /* If right pressed, face right */
            else if (controller.isRightPressed()) {
                dx = SPEED;
                this.sprite.faceRight();
                this.handleMovementSpritesAndSprinting(controller);
            }

            /* Otherwise, stand still */
            else {
                this.changeSpriteKeepDirection(SpriteType.ALBERT);
                dx = 0;
            }

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

    /* We have to do this when either isLeftPressed() or isRightPressed(),
     * figured I might as well stick it in a function
     */
    private void handleMovementSpritesAndSprinting(GameController controller) {
        if (controller.isSprinting()) {
            dx *= 2; 
            this.changeSpriteKeepDirection(SpriteType.ALBERT_SPRINTING);
        }
        else 
            this.changeSpriteKeepDirection(SpriteType.ALBERT_WALKING);
    }

    public void draw(Canvas canvas, Camera camera) {
        sprite.draw(this.getRectF(),canvas,camera);
    }
    
    public void kill() {
        dead = true;
        SoundManager.pauseMedia();
        SoundManager.playSound(4, 1.0f, false);
        // TODO: Decrement lives
        this.changeSpriteKeepDirection(SpriteType.ALBERT_DEAD);
        dx = 0;
        dy = -JUMP_SPEED;
    }
    
    @Override
    public void collideTop(LevelObject lo) {
        super.collideTop(lo);
        setCanJump(!controller.isJumpPressed());
    }

    public LevelObject getFromArray (ArrayList<? extends LevelObject> toCollide, int i) {
        LevelObject temp = toCollide.get(i);
        if (((Tile)temp).getType().equals(TileType.AIBOUND))
            return null;
        return temp;
    }
}
