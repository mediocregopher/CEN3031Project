package edu.ufl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;


public class SplashScreen extends Activity {
    protected boolean _active = true;
    protected int _splashTime = 5000;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        // Creates and loads SoundManager
        SoundManager.getInstance();
        SoundManager.initSounds(this.getApplicationContext());
        SoundManager.loadSounds();
        SoundManager.loadMedia();
        
        SoundManager.playMedia(1);
        
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
                	SoundManager.pauseMedia();
                	SoundManager.resetMedia();
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

