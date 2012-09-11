package edu.ufl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private GameThread thread;

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
		GameLog.d("GamePanel", "Action " + event.getAction());
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
			if (event.getX() < getWidth()*(.25-.05)) {
				// Left
				thread.moving = -1;
			} else if (event.getX() > getWidth()*(1 - (.25-.05))) {
				// Right
				thread.moving = 1;
			}
			if (event.getY() > getHeight() - 50) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				GameLog.d("GamePanel", "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		}
		if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP) {
			thread.moving=0;
		}
		//return super.onTouchEvent(event);
		return true; // required to get Action_up event
	}

	@Override
	protected void onDraw(Canvas canvas) {
	}

}
