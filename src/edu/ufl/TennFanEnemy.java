package edu.ufl;

import edu.ufl.Sprite.SpriteType;

public class TennFanEnemy extends Enemy {

    public TennFanEnemy(float x, float y) {
        super(x,y);
        this.standingSprite = SpriteType.TENNFAN_STANDING;
        this.walkingSprite = SpriteType.TENNFAN_WALKING;
        this.initSprite();
        this.initRectF(x,y-(this.sprite.getHeight() - Tile.SIZE),
                       this.sprite.getWidth(),this.sprite.getHeight());
    }

}
