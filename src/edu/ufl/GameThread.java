package edu.ufl;

import android.util.Log;
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

    /* Whether or not the thread is currently alive */
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}

	public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	@Override
	public void run() {
		long tickCount = 0L;
		GameLog.d("GameThread", "Starting game loop");
		while (running) {
            beginTime = System.currentTimeMillis();


            /* Begin actual game loop stuff */
			tickCount++;
            /* End game loop stuff */


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
	
}
