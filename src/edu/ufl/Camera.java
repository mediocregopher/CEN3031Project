package edu.ufl;

import android.graphics.Canvas;
import android.graphics.RectF;

class Camera {

    private GamePanel gamePanel;
    private float x;
    private float y;

    Camera(GamePanel gP) {
        gamePanel = gP;
        x = 0;
        y = 0;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void draw(LevelObject lo, Canvas canvas) {
        RectF loRectF = lo.getRectF();
        if (Util.intersectLite(/*Rect1*/
                               loRectF.left, loRectF.top,
                               loRectF.right-loRectF.left, loRectF.bottom-loRectF.top,
                               /*Rect2*/
                               x,y, gamePanel.getWidth(),gamePanel.getHeight())) {
            RectF rectf = new RectF(lo.getRectF());

            float offX = rectf.left - x;
            float offY = rectf.top  - y;

            rectf.offsetTo(offX,offY);
            canvas.drawRect(rectf,lo.color);
        }
    }

    public void offset(LevelObject albert, Level level) {

        float MAX_X = (float)level.getMaxPixelsX();
        float MAX_Y = (float)level.getMaxPixelsY();

        float albertX = albert.getX();
        float albertY = albert.getY();

        float gPw  = gamePanel.getWidth();
        float gPw2 = gPw/2f;
        float gPh  = gamePanel.getHeight();

        if (albertX < gPw2) {
            // force camera to stick to side
            // albertX may not equal gPw2 when crossing boundary b/c speed != 1;
            // so x needs to be forced
            x = 0;
        }
        else if (albertX > MAX_X - gPw2) {
            x = MAX_X - gPw;
        }
        else {
            x = albertX - gPw2;
        }

        y = albertY - 3*gPh/4f;
    }

}
