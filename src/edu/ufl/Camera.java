package edu.ufl;

import android.graphics.Canvas;
import android.graphics.RectF;

class Camera {

    private GamePanel gamePanel;
    float x;
    float y;

    Camera(GamePanel gP) {
        gamePanel = gP;
        x = 0;
        y = 0;
    }

    public void draw(LevelObject lo, Canvas canvas) {
        RectF rectf = new RectF(lo.getRectF());

        float offX = rectf.left - x;
        float offY = rectf.top  - y;

        rectf.offsetTo(offX,offY);
        canvas.drawRect(rectf,lo.color);
    }

    public void offset(LevelObject albert) {
        x = albert.getX() - gamePanel.getWidth()/2f;
        y = albert.getY() - gamePanel.getHeight()/2f;
    }

}
