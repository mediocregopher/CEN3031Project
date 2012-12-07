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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option);
	}
	
	public void mute_music(View v){
/*		try{
	        Class<?> myClass = Class.forName("edu.ufl.Option");
	        Intent intent = new Intent(Option.this,myClass);
	        startActivity(intent);
	    }
	    catch(ClassNotFoundException e){
	        e.printStackTrace();
	    }
*/		
		button=(ImageButton)findViewById(R.id.music);
		if(music_off==0){		
			button.setImageResource(R.drawable.mutemusic);
			music_off=1;
		}
		else{
			button.setImageResource(R.drawable.musicbutton);
			music_off=0;
		}
		
	}
	
	public void mute_sound(View v){
		/*		try{
			        Class<?> myClass = Class.forName("edu.ufl.Option");
			        Intent intent = new Intent(Option.this,myClass);
			        startActivity(intent);
			    }
			    catch(ClassNotFoundException e){
			        e.printStackTrace();
			    }
		*/		
		button=(ImageButton)findViewById(R.id.sound);
		if(sound_off==0){		
			button.setImageResource(R.drawable.mutespeaker);
			sound_off=1;
		}
		else{
			button.setImageResource(R.drawable.soundbutton);
			sound_off=0;
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
	
	 @Override
	    protected void onPause(){
	    	super.onPause();
	    }
	    
	    @Override
	    protected void onStop() {
	    	super.onStop();
	    }    
}