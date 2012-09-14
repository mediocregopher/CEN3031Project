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
    private double leftPortion;
    private double rightPortion;
    private double jumpPortion;
    private int lrPointer = -1;

    public GamePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
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
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        float x = event.getX(index);
        float y = event.getY(index);
        
        /*
         * When the screen is touched - check for region
         * - L/R region:    track the ID and make player move - wait for touch to stop
         * - Jump region:   start jump then let physics handle it
         * - Attack region: ATTACK!!!
         * index: Android returns ALL current pointers, if there are multiple touches we need the index
         *        corresponding to the current action (index is not preserved across multiple function calls)
         * id: each pointer has a ID that is preserved across function calls - needed to track L/R touches
         */
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            GameLog.d("GamePanel", "Coords: x=" + event.getX() + ",y=" + event.getY());
            // "&& lrPointer == -1" - makes the current movement take priority over a new movement
            if (x < leftPortion && lrPointer == -1) {
                // Left
                lrPointer = event.getPointerId(index);
                thread.moving = -1;
            }
            else if (x > rightPortion && lrPointer == -1) {
                // Right
                lrPointer = event.getPointerId(index);
                thread.moving = 1;
            }
            else if (y > jumpPortion) {
                thread.jump();
            }
        }
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP ) {
            if (event.getPointerId(index) == lrPointer) {
                thread.moving=0;
                lrPointer = -1;
            }  
        }
        return true; // required to get Action_up event
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        if (w == 0) { /* Docs say this can happen, ignore it */ }
        else {
            leftPortion  = ((double)w)*(.2);
            rightPortion = ((double)w)*(.8);
            jumpPortion = ((double)h)*(.8);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }

}
