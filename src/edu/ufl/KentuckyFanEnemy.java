package edu.ufl;

import edu.ufl.Sprite.SpriteType;

public class KentuckyFanEnemy extends Enemy {

    public KentuckyFanEnemy(float x, float y) {
        super(x,y);
        this.standingSprite = SpriteType.KENTUCKYFAN_STANDING;
        this.walkingSprite = SpriteType.KENTUCKYFAN_WALKING;
        this.initSprite();
        this.initRectF(x,y-(this.sprite.getHeight() - Tile.SIZE),
                       this.sprite.getWidth(),this.sprite.getHeight());
    }

    public KentuckyFanEnemy(float x, float y, float ml) {
        super(x,y);
        this.standingSprite = SpriteType.KENTUCKYFAN_STANDING;
        this.walkingSprite = SpriteType.KENTUCKYFAN_WALKING;
        this.initSprite();
        this.initRectF(x,y,this.sprite.getWidth(),this.sprite.getHeight());
        this.movingLeft = ml;
        if (movingLeft < 0) {
            sprite.flip();
            facingLeft = true;
        }
    }
    
    @Override
    public char getType() {
        return 'k';
    }


}
