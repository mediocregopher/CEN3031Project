package edu.ufl;

public class Constants {

    protected static final float FPS = 30;
    protected static final float FPS_PERIOD = 1000f/Constants.FPS;
    protected static final float GRAVITY = 1000f/1000f * (FPS_PERIOD/1000f);    // 0.009 pixels/millisecond^2 (remember +y moves down)

}
