package edu.ufl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Menu extends Activity
{
	ImageButton imageButton;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		addListenerOnButton();
		
		// Plays go_gators_studio
 //       SoundManager.playMedia(3);
        
		}
 
	public void addListenerOnButton() {
		imageButton = (ImageButton) findViewById(R.id.playbutton);
		
		imageButton.setOnClickListener(new OnClickListener() {
 
			public void onClick(View arg0) {
				try{
					Class myClass = Class.forName("edu.ufl.thegame");
					Intent intent = new Intent(Menu.this, myClass);
	//				SoundManager.pauseMedia();
	//				SoundManager.resetMedia();
	//				SoundManager.playSound(1, 1.0f, false);
					startActivity(intent);
				}
				catch(ClassNotFoundException e){
			    	e.printStackTrace();
			    	}
//			  		Toast.makeText(Menu.this,"Play Button is Clicked", Toast.LENGTH_SHORT).show();
				}
			});
 
		}

    @Override
    protected void onPause(){
   // 	SoundManager.pauseMedia();
    	super.onPause();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
  //  	SoundManager.resetMedia();
    }    
}
