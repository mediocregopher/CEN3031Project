package edu.ufl;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;


public class LevelWin extends Activity {
    protected boolean _active = true;
    protected int _showTime = 5000;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelwin);
        
        // Creates and loads SoundManager
        SoundManager.getInstance();
        SoundManager.initSounds(this.getApplicationContext());
        SoundManager.loadSounds();
        SoundManager.loadMedia();
        
        SoundManager.playMedia(1);
        
        Thread winThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _showTime)) {
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
                                        
                   // interrupt();
                    
                }
            }
        };
        winThread.start();
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


