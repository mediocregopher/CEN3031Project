package edu.ufl;

import android.app.ListActivity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity
{
	//basic startup menu
	String classes[] = {"thegame", "Help", "Settings", "Level", "High Score"};
	
	// SoundPool for menu selection
	private SoundPool soundPool;
	private int soundID;
	
	
    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(Menu.this,android.R.layout.simple_list_item_1,classes));
        
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundID = soundPool.load(this, R.raw.menu_beep, 1);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
    	String act = classes[position];
    	try{
    	super.onListItemClick(l, v, position, id);
    	Class myClass = Class.forName("edu.ufl." + act);
    	Intent intent = new Intent(Menu.this, myClass);
    	
    	// Get volume and play sound when item is selected
    	AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    	float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    	float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    	float volume = actualVolume / maxVolume;
    	soundPool.play(soundID, volume, volume, 1, 0, 1f);
    	
    	startActivity(intent);
    } catch(ClassNotFoundException e){
    	e.printStackTrace();
    	}
    }
    
}
