package edu.ufl;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;


public class SplashScreen extends Activity {
    protected boolean _active = true;
    protected int _splashTime = 5000;
    MediaPlayer mpTebow;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        mpTebow = MediaPlayer.create(SplashScreen.this, R.raw.orange_blue);
        mpTebow.start();
        
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                    // do nothing
                    
                } finally {
                	
                	mpTebow.release();
                    finish();
                    
                    Intent intent = new Intent(SplashScreen.this, Menu.class);
                    startActivity(intent);
                    
                   // interrupt();
                    
                }
            }
        };
        splashTread.start();
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	finish();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }
}

