package edu.ufl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Camera {
    // Dimensions of the camera in terms of the level
    private int width;
    private int height;
    
    // Bottom left corner of camera with relation to the Level
    private int xLoc;
    private int yLoc;
    
    //placeholder
    LevelObject tile;
    
    private GameThread thread;
    
    public Camera(GameThread thread, LevelObject tile) {
        super();
        this.thread = thread;
        this.tile = tile;
        xLoc = 0;
        yLoc = 0;
        this.width = 800;
        this.height = 480;
    }
    
    public void offsetCamera(int x, int y) {
        int newXLoc = x + xLoc;
        if (newXLoc > 0 && newXLoc < /*level.maxX()*/ 1000 - width) {
            xLoc = newXLoc;
            // +x -> move everything -x
            tile.setX(tile.getX() -x);
        } else if (newXLoc > /*level.maxX()*/ 1000 - width) {
            xLoc = 1000-width;
        } else  {
            xLoc = 0;
        }
        
        
        yLoc += y;

        // +y -> move everything -y
        tile.setY(tile.getY()-y);
    }
    
    public float toCamCoordX(float x) {
        return x-xLoc;
    }
    
    public float toCamCoordY(float y) {
        return (yLoc+height)-y;
    }

    public void drawRect(Canvas canvas, RectF object, Paint color) {
        canvas.drawRect(
                new RectF(
                        toCamCoordX(object.left), 
                        toCamCoordY(object.top), 
                        toCamCoordX(object.right), 
                        toCamCoordY(object.bottom)),
                color);
        
    }

    public void updatePosition(LevelObject albert) {
        float x = toCamCoordX(albert.getX());
        if (x > .5 * width) {
            offsetCamera((int) (x-.5*width), 0);
        } else if (x < .5 * width) {
            offsetCamera((int) (x-.5*width), 0);
        }
        
    }
}
