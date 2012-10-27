package edu.ufl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Menu extends Activity
{
	ImageButton imageButton;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		addListenerOnButton();
		}
 
	public void addListenerOnButton() {
		imageButton = (ImageButton) findViewById(R.id.playbutton);
		
		imageButton.setOnClickListener(new OnClickListener() {
 
			public void onClick(View arg0) {
			   Toast.makeText(Menu.this,"Play Button is Clicked", Toast.LENGTH_SHORT).show();
				}
			});
 
		}

    @Override
    protected void onPause(){
    	SoundManager.pauseMedia();
    	super.onPause();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	SoundManager.resetMedia();
    }    
}
