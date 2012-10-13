package edu.ufl;

public class Constants {

    protected static final float FPS = 30;
    protected static final float FPS_PERIOD = 1000f/Constants.FPS;
    protected static float GRAVITY;


    public static void init() {
        //Scale GRAVITY
        GRAVITY = 1000f/1000f * (FPS_PERIOD/1000f);
        GRAVITY = ResourceManager.dpToPx(GRAVITY);
    }
}
