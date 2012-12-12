package edu.ufl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;

public class thegame extends Activity
{
    GamePanel gp;
    Bundle state;
    private int lvlID;
    private AlertDialog pauseDialog;
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
            pause();
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
    
    private void pause() {
        gp.getThread().pause();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("PAUSED")
        .setTitle("SAW");
        // Add the buttons
        builder.setPositiveButton("Unpause", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                gp.getThread().unpause();
            }
        });
        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        // Create the AlertDialog
        pauseDialog = builder.create();
        pauseDialog.setCanceledOnTouchOutside(false);
        pauseDialog.show();
        GameLog.d("thegame", "Showing pause dialog");
        SoundManager.pauseMedia();
    }
    
    @Override
    protected void onPause(){
        GameLog.d(this.getClass().getName(), "onPause");
        if (pauseDialog != null)
            pauseDialog.dismiss();
        super.onPause();
    }
    
    @Override
    public void onBackPressed() {
        pause();
    }
    
    @Override
    protected void onStop() {
        SoundManager.pauseMedia();
        super.onStop();
    }
    
    
}