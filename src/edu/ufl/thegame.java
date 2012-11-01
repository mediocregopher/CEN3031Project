package edu.ufl;

import android.content.Intent;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.WindowManager;

public class thegame extends Activity
{
    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new GamePanel(this, getIntent().getIntExtra("lvl", R.raw.level1_1)));
        //setContentView(R.layout.main);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    
    @Override
    protected void onPause(){
    	SoundManager.pauseMedia();
    	super.onPause();
    	finish();
    }
    
    @Override
    protected void onStop() {
    	SoundManager.pauseMedia();
    	super.onStop();
    	finish();
    }

}
