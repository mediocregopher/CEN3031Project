package edu.ufl;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;

public class thegame extends Activity
{
	MediaPlayer song;
    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(new GamePanel(this));
        setContentView(R.layout.main);

        song = MediaPlayer.create(thegame.this, R.raw.go_gators_live);
        song.start();
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	finish();
    }
}
