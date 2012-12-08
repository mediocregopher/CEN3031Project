package edu.ufl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Option extends Activity
{
	 
	ImageButton button;
	int sound_off=0;
	int music_off=0;
	static int sound_off1=0;
	static int music_off1=0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option);
	}
	
	public void mute_music(View v){

		button=(ImageButton)findViewById(R.id.music);
		if(music_off==0){		
			button.setImageResource(R.drawable.mutemusic);
			music_off=1;
			SoundManager.pauseMedia();
			SoundManager.mute();
			setflag();
		}
		else{
			button.setImageResource(R.drawable.musicbutton);
			music_off=0;
			clearflag();
	/*		 SoundManager.getInstance();
		     SoundManager.initSounds(this.getApplicationContext());
		     SoundManager.loadSounds();
		     SoundManager.loadMedia();   
		     SoundManager.playMedia(1);*/
		}
		
	}
	
	public void mute_sound(View v){		
		button=(ImageButton)findViewById(R.id.sound);
		
		if(sound_off==0){		
			button.setImageResource(R.drawable.mutespeaker);
			sound_off=1;
			SoundManager.pauseMedia();
			SoundManager.mute();
			setflag();
		}
		else{
			button.setImageResource(R.drawable.soundbutton);
			sound_off=0;
			clearflag();
		}
		
	}
	
	public void back(View v){
			try{
			        Class<?> myClass = Class.forName("edu.ufl.Menu");
			        Intent intent = new Intent(Option.this,myClass);
			        startActivity(intent);
			    }
			    catch(ClassNotFoundException e){
			        e.printStackTrace();
			    }
				
	}
	
	public int soundStat(){
		return sound_off;
	}
	
	public int musicStat(){
		return music_off;
	}
	
	public void play(View v){
				try{
			        Class<?> myClass = Class.forName("edu.ufl.LevelSelect");
			        Intent intent = new Intent(Option.this,myClass);
			        startActivity(intent);
			    }
			    catch(ClassNotFoundException e){
			        e.printStackTrace();
			    }
		
	}
	
	public static void setflag(){
		music_off1=1;
		sound_off1=1;
		
	}
	
	public static void clearflag(){
		music_off1=0;
		sound_off1=0;
		
	}
	
	public static int getflag(){
		return music_off1;
	}
	
	 @Override
	    protected void onPause(){
	    	super.onPause();
	    }
	    
	    @Override
	    protected void onStop() {
	    	super.onStop();
	    }    
}