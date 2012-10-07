package edu.ufl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Albert extends LevelObject {

    Albert(float x, float y) {
        this.bitmap = BitmapFactory.decodeResource( ResourceManager.getResources(),
                                                    R.drawable.albert );
        this.initRectF(x,y,bitmap.getWidth(),bitmap.getHeight());
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        camera.draw(this.getRectF(),bitmap,canvas);
    }

}
