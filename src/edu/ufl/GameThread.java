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
    private RectF albert;
    // Albert moving 1 = right, -1 = left
    public int moving = 0;
    // Physic constants
    private static final double PHYS_GRAVITY = 90;    // 90 pixels/second^2 (remember +y moves down)
    private static final double PHYS_PLAYER_SPEED = 50; // 50 pixels/second
    private static final double PHYS_PLAYER_JUMP = 100; // 100 pixels/second^2
    private static final double PHYS_ELAPSED_TIME = 1/(float)FPS;
    
    // Velocity dx, dy
    private double dx;
    private double dy;
    
    // Minimum y - placeholder until we get level collision
    private static final int MAX_Y = 300;
    

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
                    // Should use a "physics" method and give albert a speed and multiply by elapsed time
                    updatePhysics();
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
        // draw background
        canvas.drawARGB(255, 0x38, 0xAC, 0xEC);
        
        // draw Albert
        Paint color = new Paint();
        color.setARGB(255, 0x04, 0x5f, 0x18);
        canvas.drawRect(albert, color);
    }
    
    private void updatePhysics() {

        if      (gamePanel.controller.isLeftPressed())  { moving = -1; }
        else if (gamePanel.controller.isRightPressed()) { moving = 1; }
        else    { moving = 0; }

        if (gamePanel.controller.isJumpPressed()) { jump(); }

        dx = moving * PHYS_PLAYER_SPEED;
        dy += PHYS_GRAVITY * PHYS_ELAPSED_TIME;
        
        float x = (float) (albert.centerX() + dx * PHYS_ELAPSED_TIME);
        float y = (float) (albert.centerY() + dy * PHYS_ELAPSED_TIME);
        
        
        // check for collisions
        if (x < 0) {
            x=0;
        } else if (x > gamePanel.getWidth()) {
            x = gamePanel.getWidth();
        }
        
        if (y > MAX_Y) {
            y = MAX_Y;
            dy = 0;
        }
        
        albert.offsetTo(x - albert.width()/2, y - albert.height()/2);
    }
    
    public void jump() {
        // do more
        dy = -PHYS_PLAYER_JUMP;
    }
}
