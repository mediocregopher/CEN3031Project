package edu.ufl;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Rect;
import android.graphics.Bitmap;
import android.graphics.Paint;

class Camera {

    private GamePanel gamePanel;
    private float x;
    private float y;
    
    private float cloudX;
    private static final float CLOUD_DX = 2f;

    Camera(GamePanel gP) {
        gamePanel = gP;
        x = 0;
        y = 0;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void draw(RectF mask, RectF loRectF, Bitmap bitmap, Canvas canvas) {
        if (Util.intersectLite(/*Rect1*/
                               loRectF.left, loRectF.top,
                               loRectF.right-loRectF.left, loRectF.bottom-loRectF.top,
                               /*Rect2*/
                               x,y, gamePanel.getWidth(),gamePanel.getHeight())) {

            float left = loRectF.left - x;
            float top  = loRectF.top  - y;

            RectF tmp = new RectF(loRectF);
            tmp.offsetTo(left,top);
            Rect dst = new Rect();
            tmp.round(dst);

            Rect src = null;
            if (mask != null) {
                src = new Rect();
                mask.round(src);
            }

            canvas.drawBitmap(bitmap,src,dst,null);
        }
    }

    public void drawRectF(RectF loRectF, Canvas canvas) {
        Paint color  = new Paint();
        color.setARGB(255, 0x04, 0x5f, 0x18);
        if (Util.intersectLite(/*Rect1*/
                               loRectF.left, loRectF.top,
                               loRectF.right-loRectF.left, loRectF.bottom-loRectF.top,
                               /*Rect2*/
                               x,y, gamePanel.getWidth(),gamePanel.getHeight())) {
            RectF rectf = new RectF(loRectF);

            float offX = rectf.left - x;
            float offY = rectf.top  - y;

            rectf.offsetTo(offX,offY);
            canvas.drawRect(rectf,color);
        }
    }

    public void offset(Albert albert, Level level) {

        float MAX_X = (float)level.getMaxPixelsX();
        float MAX_Y = (float)level.getMaxPixelsY();

        float albertX = albert.getX();
        float albertBottom = albert.getY() + albert.getHeight();

        float gPw  = gamePanel.getWidth();
        float gPw2 = gPw/2f;
        float gPh  = gamePanel.getHeight();
        float gPh2 = gPh/2f;

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

        if (albertBottom >= MAX_Y - gPh2) {
            y = MAX_Y - gPh;
        }
        else {
            y = albertBottom - gPh2;
        }
    }

    public void drawBackground(Bitmap background, Bitmap clouds, int MAX_Y, Canvas canvas) {
        float bx = 0;
        float by = (float)(MAX_Y - background.getHeight()) - getY();
        canvas.drawBitmap(background,bx,by,null);
        
        canvas.drawBitmap(clouds, cloudX - getX(), (float)(MAX_Y - clouds.getHeight()) - getY(), null);
        canvas.drawBitmap(clouds, cloudX - getX() - clouds.getWidth(), (float)(MAX_Y - clouds.getHeight()) - getY(), null);
        cloudX += CLOUD_DX;
        
        // This works for any GamePanel < 1600px which is the cloud bitmap width
        if (cloudX < getX()) {
            cloudX += clouds.getWidth();
        } else if (cloudX - clouds.getWidth() > getX()) {
            cloudX -= clouds.getWidth();
        }
    }

}
