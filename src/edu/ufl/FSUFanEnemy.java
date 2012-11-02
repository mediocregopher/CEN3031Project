package edu.ufl;

import edu.ufl.Sprite.SpriteType;

public class FSUFanEnemy extends Enemy {

    public FSUFanEnemy(float x, float y) {
        super(x,y);
        this.standingSprite = SpriteType.FSUFAN_STANDING;
        this.walkingSprite = SpriteType.FSUFAN_WALKING;
        this.initSprite();
        this.initRectF(x,y-(this.sprite.getHeight() - Tile.SIZE),
                       this.sprite.getWidth(),this.sprite.getHeight());
        this.topHarmful = true;
    }

}
