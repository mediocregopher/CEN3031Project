package edu.ufl;

import android.content.Intent;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public Context context;
    private GameThread thread;
    private int lvlID;
    public  GameController controller;
    public boolean endedOnWin = false;

    public GamePanel(Context context, int lvlID) {
        super(context);
        getHolder().addCallback(this);
        this.lvlID = lvlID;
        thread     = new GameThread(getHolder(), context, this, lvlID);
        controller = new GameController();
        this.context = context;
        setFocusable(true);
    }

    public GameThread getThread() { return thread; }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (thread == null) {
            GameLog.d("GP", "thread is null");
            thread = new GameThread(getHolder(), context, this, lvlID);
        }
        thread.start();
        thread.setRunning(true);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        GameLog.d("GamePanel", "Surface is being destroyed, attempting to shut down thread");

        /* Block until we can shutdown the gamethread */
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                /* Try again */
            }
        }
        thread = null;
        
      //Show level win screen, we assume we won the level (for now)
        if (this.endedOnWin) {
            Intent intent = new Intent(context, LevelWin.class);
            context.startActivity(intent);
        }
        GameLog.d("GamePanel", "Thread was shut down cleanly");
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controller.panelTouchEvent(event);
        return true; // required to get Action_up event
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        if (w == 0) { /* Docs say this can happen, ignore it */ }
        else {
            controller.panelSizeChanged(w,h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }
    
    protected void saveState(Bundle map) {
        thread.saveState(map);
    }
    
    protected void restoreState(Bundle map) {
        if (thread != null) {
            if(thread.isAlive() && thread.isRunning()) {
                thread.restoreState(map);
            } else {
                thread = new GameThread(getHolder(), context, this, lvlID);
                thread.restoreState(map); 
            }
        } else {
            thread = new GameThread(getHolder(), context, this, lvlID);
            thread.restoreState(map);
        }
    }

}
