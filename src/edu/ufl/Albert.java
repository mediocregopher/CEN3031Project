package edu.ufl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Albert extends LevelObject {

    private Bitmap bitmap;

    Albert(float x, float y) {
        super(x,y,40,90);
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inScaled = false;

        this.bitmap = BitmapFactory.decodeResource( ResourceManager.getResources(),
                                                    R.drawable.albert,
                                                    o );
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        camera.draw(this.getRectF(),bitmap,canvas);
    }

}
