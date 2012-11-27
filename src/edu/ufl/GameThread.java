package edu.ufl;

import java.io.*;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.app.Activity;

public class GameThread extends Thread {

    long beginTime;     // the time when the cycle begun
    long timeDiff;      // the time it took for the cycle to execute
    int sleepTime;      // ms to sleep (<0 if we're behind)

    // Surface holder that can access the physical surface
    private SurfaceHolder surfaceHolder;
    // The actual view that handles inputs and draws to the surface
    private GamePanel gamePanel;
    private Camera camera;

    private Level level;
    private int lvlID;

    /* Whether or not the thread is currently alive */
    private boolean running;
    private boolean paused = false;
    public void setRunning(boolean running) {
        this.running = running;
    }
    public boolean isRunning() {return running;}
    
    public boolean isPaused() {return paused;}

    public GameThread(SurfaceHolder surfaceHolder, Context context, GamePanel gamePanel, int lvlID) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
        this.camera = new Camera(gamePanel);
        this.lvlID = lvlID;

        ResourceManager.init(context);
        Constants.init();
        setLevel(lvlID);
        
    }
    
    protected void setLevel(int id){
        lvlID = id;
    	SoundManager.playMedia(2);
        level = null;
        try {        	
            BufferedInputStream bis = new BufferedInputStream( 
                                        ResourceManager.getResources().openRawResource(
                                          id) );
            this.level  = LevelReader.read(bis);
            bis.close();
        } catch (IOException e) {
            GameLog.d("GameThread","Could not create level for some reason");
            this.level  = LevelReader.blankLevel();
        } 
    }

    @Override
    public void run() {
        GameLog.d("GameThread", "Starting game loop");
        while (running) {
            beginTime = System.currentTimeMillis();

            /* Begin actual game loop stuff */

            Canvas c = null;
            try {
                c = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    if (!paused) {
                        update();
                    }
                    if (level.needsReset()) {
                    	SoundManager.pauseMedia();
                    	SoundManager.resetMedia();
                        setLevel(lvlID);
                    }
                    else if (level.isDone()) {
                        //Should send back to main menu or something
                        running = false;
                        gamePanel.endedOnWin = true;
                    	SoundManager.pauseMedia();
                    	SoundManager.resetMedia();
                        //setLevel(lvlID);
                    }
                    else {
                        draw(c);
                    }
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }

            /* End game loop stuff */

            //If there's time left over in this frame, sleep. This is what keeps us at
            //our set FPS
            timeDiff = System.currentTimeMillis() - beginTime;
            sleepTime = (int)(Constants.FPS_PERIOD - timeDiff);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) { /* Oh well */ }
            }

        }
        SoundManager.pauseMedia();
        SoundManager.resetMedia();

        if (level.isDone()) {
            ((Activity)(gamePanel.context)).finish();
        }
    }

    private void draw(Canvas canvas) {
        if (canvas != null) { // stupid undocumented change around android 4
            level.draw(canvas,camera);
        }
    }
    
    private void update() {
        level.update(gamePanel,camera);
        
    }
    
    public void pause() {
        paused = true;
    }
    
    public void unpause() {
        paused = false;
    }
    
    protected void saveState(Bundle map) {
        synchronized (surfaceHolder) {
            map.putBoolean("paused", paused);
            level.saveState(map);
        }
    }
    
    protected void restoreState(Bundle map) {
        synchronized (surfaceHolder) {
            paused = map.getBoolean("paused", false);
            level.restoreState(map);
            if (!level.canDraw()) {
                //TODO: make the toLookAt arrays
            }
        }
    }
}
