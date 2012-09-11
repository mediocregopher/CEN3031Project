package edu.ufl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private final static int FPS = 30;
    private final static int FPS_PERIOD = 1000/FPS;

    long beginTime;     // the time when the cycle begun
    long timeDiff;      // the time it took for the cycle to execute
    int sleepTime;      // ms to sleep (<0 if we're behind)

	// Surface holder that can access the physical surface
	private SurfaceHolder surfaceHolder;
	// The actual view that handles inputs and draws to the surface
	private GamePanel gamePanel;
	// Albert is a rectangle?
	public RectF albert;
	// Albert moving 1 = right, -1 = left
	public int moving;


    /* Whether or not the thread is currently alive */
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}

	public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
		albert = new RectF(10,10,30,50);
	}

	@Override
	public void run() {
		long tickCount = 0L;
		GameLog.d("GameThread", "Starting game loop");
		while (running) {
            beginTime = System.currentTimeMillis();

            /* Begin actual game loop stuff */

			tickCount++;
			Canvas c = null;
			try {
                c = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                	// IF running - and not paused
                	// Should use a "physics" method and give albert a speed and mulitply by elasped time
                	albert.offset(5*moving, 0);
                	// END IF

                    draw(c);
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
            sleepTime = (int)(FPS_PERIOD - timeDiff);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) { /* Oh well */ }
            }

		}
		GameLog.d("GameThread", "Game loop executed " + tickCount + " times");
	}
	

	
	private void draw(Canvas canvas) {
		canvas.drawARGB(255, 255, 0, 0);
		Paint color = new Paint();
		color.setARGB(255, 0, 0, 255);
		canvas.drawRect(albert, color);
	}
}
