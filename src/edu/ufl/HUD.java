package edu.ufl;

import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Canvas;
import android.graphics.Bitmap;

public class HUD {

    private Typeface smbTypeface;
    private Paint livesPaint;
    private Paint livesPaintStroke;
    private float livesX;
    private float livesY = 45;
    private float albertX = 10;
    private float albertY = 10;
    private Bitmap albertHead;
    
    private Paint pointsPaint;
    private Paint pointsPaintStroke;
    private float pointsX;
    private float pointsY = 108.5f;
    private float footballX = 15;
    private float footballY = 75;
    private Bitmap football;


    HUD() {
        Typeface smbTypeface = Typeface.createFromAsset(ResourceManager.getAssetManager(),"smb.ttf");

        livesPaint = new Paint();
        livesPaint.setTextSize(50); 
        livesPaint.setTypeface(smbTypeface);
        livesPaint.setColor(Color.WHITE);
        livesPaint.setStrokeWidth(0);

        livesPaintStroke = new Paint();
        livesPaintStroke.setColor(Color.BLACK); 
        livesPaintStroke.setTextSize(50); 
        livesPaintStroke.setTypeface(smbTypeface);
        livesPaintStroke.setStyle(Paint.Style.STROKE);
        livesPaintStroke.setStrokeWidth(10);

        albertHead = ResourceManager.getBitmap(R.drawable.albert_head_icon);

        albertX = ResourceManager.dpToPx(albertX);
        albertY = ResourceManager.dpToPx(albertY);

        livesX = albertX + albertHead.getWidth() + ResourceManager.dpToPx(10);
        livesY = ResourceManager.dpToPx(livesY);

        //Use same font for points as for lives
        pointsPaint = livesPaint;
        pointsPaintStroke = livesPaintStroke;
        
        football = ResourceManager.getBitmap(R.drawable.football);
        footballX = ResourceManager.dpToPx(footballX);
        footballY = ResourceManager.dpToPx(footballY);
        pointsX = footballX + football.getWidth() + ResourceManager.dpToPx(10);
        pointsY = ResourceManager.dpToPx(pointsY);

    }

    public void drawLives(Canvas canvas, int lives) {
        String livesText = "X"+String.valueOf(lives);
        canvas.drawText(livesText,livesX,livesY,livesPaintStroke);
        canvas.drawText(livesText,livesX,livesY,livesPaint);
        canvas.drawBitmap(albertHead,albertX,albertY,null);
    }

    public void drawPoints(Canvas canvas, int points) {
        String pointsText = "X"+String.valueOf(points);
        canvas.drawText(pointsText,pointsX,pointsY,pointsPaintStroke);
        canvas.drawText(pointsText,pointsX,pointsY,pointsPaint);
        canvas.drawBitmap(football,footballX,footballY,null);
    }

}

