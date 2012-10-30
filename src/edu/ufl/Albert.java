package edu.ufl;

import java.util.ArrayList;

import android.graphics.RectF;
import android.graphics.Canvas;
import edu.ufl.Sprite.SpriteType;
import edu.ufl.Tile.TileType;

public class Albert extends LevelObject {

    // Physic constants
    private float SPEED = 200f/1000f;
    private float JUMP_SPEED = 610f/1000f;
    private float ALBERT_TAIL = 20f;
    private float ALBERT_ATTACK_RANGE = 50f;

    private GameController controller;
    private boolean canJump;
    private boolean dead = false;

    private RectF hitbox;

    public float getJumpSpeed() { return JUMP_SPEED; }
    public void setCanJump(boolean canJump) { this.canJump = canJump; }

    public boolean isDead() { return dead; }

    Albert(float x, float y) {
        //Scale *SPEED's
        SPEED = ResourceManager.dpToPx(SPEED);
        JUMP_SPEED = ResourceManager.dpToPx(JUMP_SPEED);
        ALBERT_TAIL = ResourceManager.dpToPx(ALBERT_TAIL);
        ALBERT_ATTACK_RANGE = ResourceManager.dpToPx(ALBERT_ATTACK_RANGE);

        this.sprite = new Sprite(SpriteType.ALBERT);
        this.initRectF(x,y,this.sprite.getWidth(),this.sprite.getHeight());
        this.hitbox = new RectF(x+ALBERT_TAIL,y,x+this.sprite.getWidth(),y+this.sprite.getHeight());
    }

    /* Copy Constructor */
    Albert(Albert a) {
        super(a);
        this.controller = a.controller;
        this.canJump = a.canJump;
        this.dead = a.dead;
        this.hitbox = new RectF(a.hitbox);
        this.SPEED = a.SPEED;
        this.JUMP_SPEED = a.JUMP_SPEED;
        this.ALBERT_TAIL = a.ALBERT_TAIL;
        this.ALBERT_ATTACK_RANGE = a.ALBERT_ATTACK_RANGE;
    }
    
    public void update(GameController controller) {
        this.controller = controller;

        if (!dead) {
            /* If left pressed, face left */
            if (controller.isLeftPressed())  { 
                dx = -SPEED;
                this.faceLeft();
                this.handleMovementSpritesAndSprinting(controller);
            }

            /* If right pressed, face right */
            else if (controller.isRightPressed()) {
                dx = SPEED;
                this.faceRight();
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

            if (dy != 0) {
                this.changeSpriteKeepDirection(SpriteType.ALBERT_FALLING);
            }
        }

        dy += Constants.GRAVITY;
        this.setX(x + dx * Constants.FPS_PERIOD);
        this.setY(y + dy * Constants.FPS_PERIOD);
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

    @Override
    public RectF getHitbox() { return hitbox; }

    @Override 
    public void setX(float x) {
        super.setX(x);
        if (getFacingLeft())
            this.hitbox.offsetTo(x,this.y);
        else
            this.hitbox.offsetTo(x+ALBERT_TAIL,this.y);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        this.hitbox.offsetTo(this.hitbox.left,y);
    }


    public void draw(Canvas canvas, Camera camera) {
        sprite.draw(this.getRectF(),canvas,camera);
    }
    
    public void kill() {
        dead = true;
        SoundManager.pauseMedia();
        SoundManager.playSound(4, 1.0f, false);
        // TODO: Decrement lives
        //Change size of rect for death animation. Doesn't really matter at this point
        this.rectf.set(
            this.rectf.left,
            this.rectf.top,
            this.rectf.left + ResourceManager.dpToPx(108),
            this.rectf.top  + ResourceManager.dpToPx(108)
        );
        this.changeSpriteKeepDirection(SpriteType.ALBERT_DEAD);
        dx = 0;
        dy = -JUMP_SPEED;
    }
    
    @Override
    public void collideTop(LevelObject lo) {
        super.collideTop(lo);
        setCanJump(!controller.isJumpPressed());
    }

    public Tile getFromArray (ArrayList<Tile> toCollide, int i) {
        Tile temp = toCollide.get(i);
        if (temp.getType().equals(TileType.AIBOUND))
            return null;
        return temp;
    }
    
    public RectF attackHitbox() {
        RectF attackHitbox;
        if (this.facingLeft) {
            attackHitbox = new RectF(hitbox.left-ALBERT_ATTACK_RANGE, hitbox.top, hitbox.left, hitbox.top+ALBERT_ATTACK_RANGE);
        }
        else {
            attackHitbox = new RectF(hitbox.right, hitbox.top, hitbox.right+ALBERT_ATTACK_RANGE, hitbox.top+ALBERT_ATTACK_RANGE);
        }
        return attackHitbox; //creating area of attack, change the numbers to calibrate it 
    }
    
}
