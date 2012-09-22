package edu.ufl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Camera {

    // Top left corner of camera with relation to the Level
    private int xLoc;
    private int yLoc;

    //placeholder
    LevelObject tile;

    //Gotta use this for height/width, since they could potentially change
    GamePanel gamePanel;

    public Camera(GamePanel gamePanel,LevelObject tile) {
        super();
        this.tile = tile;
        xLoc = 0;
        yLoc = 0;
        this.gamePanel = gamePanel;
    }

    public void offsetCamera(int x, int y) {
        int width = gamePanel.getWidth();
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
        return (yLoc+gamePanel.getHeight())-y;
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
        if (x != .5 * this.gamePanel.getWidth()) {
            offsetCamera((int) (x-.5*this.gamePanel.getWidth()), 0);
        }
    }
}
