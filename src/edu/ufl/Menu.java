package edu.ufl;

import android.app.Activity;
import android.content.Intent;
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
				try{
					Class myClass = Class.forName("edu.ufl.thegame");
					Intent intent = new Intent(Menu.this, myClass);
					startActivity(intent);
				}
				catch(ClassNotFoundException e){
			    	e.printStackTrace();
			    	}
//			  		Toast.makeText(Menu.this,"Play Button is Clicked", Toast.LENGTH_SHORT).show();
				}
			});
		
		imageButton = (ImageButton) findViewById(R.id.helpbutton);		
		imageButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {				
			  	Toast.makeText(Menu.this,"Use right and left side of touchscreen to navigate. Use bottom middle part of toughscreen to jump.", 
			  			Toast.LENGTH_SHORT).show();
				}
			});
 
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
