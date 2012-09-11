package edu.ufl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    private double leftPortion;
    private double rightPortion;

    public GamePanel(Context context) {
        super(context);
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
        //GameLog.d("GamePanel", "Action " + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
            GameLog.d("GamePanel", "Coords: x=" + event.getX() + ",y=" + event.getY());
            if (event.getX() < leftPortion ) {
                // Left
                thread.moving = -1;
            }
            else if (event.getX() > rightPortion ) {
                // Right
                thread.moving = 1;
            }
            else if (event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity)getContext()).finish();
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP ) {
            thread.moving=0;
        }
        return true; // required to get Action_up event
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        if (w == 0) { /* Docs say this can happen, ignore it */ }
        else {
            leftPortion  = ((double)w)*(.2);
            rightPortion = ((double)w)*(.8);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }

}
