package edu.ufl;

import android.view.MotionEvent;

public class GameController {

    private double leftPortion;
    private double rightPortion;
    private double jumpPortion;

    private int    lrPointer = -1;

    private boolean  leftPressed = false;
    private boolean rightPressed = false;
    private boolean  jumpPressed = false;

    public boolean  isLeftPressed() { return  leftPressed; }
    public boolean isRightPressed() { return rightPressed; }
    public boolean  isJumpPressed() { return  jumpPressed; }


    public void panelSizeChanged(int w, int h) {
        leftPortion  = ((double)w)*(.2);
        rightPortion = ((double)w)*(.8);
        jumpPortion  = ((double)h)*(.8);
    }

    public void panelTouchEvent(MotionEvent event) {
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
                lrPointer    = event.getPointerId(index);
                leftPressed  = true;
            }
            else if (x > rightPortion && lrPointer == -1) {
                // Right
                lrPointer    = event.getPointerId(index);
                rightPressed = true;
            }
            else if (y > jumpPortion) {
                jumpPressed  = true;
            }
        }
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP ) {
            if (event.getPointerId(index) == lrPointer) {
                lrPointer    = -1;
                leftPressed  = false;
                rightPressed = false;
            }
            else {
                jumpPressed  = false;
            }
        }
     }
}

