package edu.ufl;

import edu.ufl.Sprite.SpriteType;

public class AuburnFanEnemy extends Enemy {

    public AuburnFanEnemy(float x, float y) {
        super(x,y);
        this.standingSprite = SpriteType.AUBURNFAN_STANDING;
        this.walkingSprite = SpriteType.AUBURNFAN_WALKING;
        this.initSprite();
        this.initRectF(x,y-(this.sprite.getHeight() - Tile.SIZE),
                       this.sprite.getWidth(),this.sprite.getHeight());
    }

}
