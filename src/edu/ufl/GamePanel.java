package edu.ufl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    public  GameController controller;
    private double leftPortion;
    private double rightPortion;
    private double jumpPortion;
    private int lrPointer = -1;

    public GamePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        thread     = new GameThread(getHolder(), this);
        controller = new GameController();
        setFocusable(true);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
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

}
