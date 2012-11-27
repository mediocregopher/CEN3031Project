package edu.ufl;

import edu.ufl.Sprite.SpriteType;

public class AlabamaFanEnemy extends Enemy {

    public AlabamaFanEnemy(float x, float y) {
        super(x,y);
        this.standingSprite = SpriteType.ALABAMAFAN_STANDING;
        this.walkingSprite = SpriteType.ALABAMAFAN_WALKING;
        this.initSprite();
        this.initRectF(x,y-(this.sprite.getHeight() - Tile.SIZE),
                       this.sprite.getWidth(),this.sprite.getHeight());
    }
    
    public AlabamaFanEnemy(float x, float y, float movingLeft) {
        super(x,y);
        this.standingSprite = SpriteType.ALABAMAFAN_STANDING;
        this.walkingSprite = SpriteType.ALABAMAFAN_WALKING;
        this.initSprite();
        this.initRectF(x,y,this.sprite.getWidth(),this.sprite.getHeight());
        this.movingLeft = movingLeft;
        if (movingLeft < 0) {
            sprite.flip();
            facingLeft = true;
        }
    }
    
    @Override
    public char getType() {
        return 'b';
    }

}
