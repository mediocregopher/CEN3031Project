package edu.ufl;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class thegame extends Activity
{
    GamePanel gp;
    Bundle state;
    private int lvlID;
    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.lvlID = getIntent().getIntExtra("lvl", R.raw.level1_1);
        gp = new GamePanel(this, lvlID);
        setContentView(gp);
        restore(savedInstanceState);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    
    private void restore(Bundle map) {
        if (map != null) {
            gp.restoreState(map);
            GameLog.d(this.getClass().getName(), "Restoring State");
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        restore(state);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // just have the View's thread save its state into our Bundle
        super.onSaveInstanceState(outState);
        if (outState != null) {
            state = new Bundle();
            gp.saveState(this.state);
            outState.putAll(this.state);
            GameLog.d(this.getClass().getName(), "Saving State");
        }
        
    }
    
    @Override
    protected void onPause(){
        SoundManager.pauseMedia();
        super.onPause();
    }
    
    @Override
    protected void onStop() {
        SoundManager.pauseMedia();
        super.onStop();
    }
    
    
}
