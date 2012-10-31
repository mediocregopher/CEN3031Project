package edu.ufl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Menu extends Activity
{
	ImageButton imageButton;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
	}
	
	public void play(View v) {
	    try{
	        Class<?> myClass = Class.forName("edu.ufl.thegame");
	        Intent intent = new Intent(Menu.this, myClass);
	        startActivity(intent);
	    }
	    catch(ClassNotFoundException e){
	        e.printStackTrace();
	    }
	    //          Toast.makeText(Menu.this,"Play Button is Clicked", Toast.LENGTH_SHORT).show();
	}

	
	public void help(View v) {
	    Toast.makeText(Menu.this,"Use right and left side of touchscreen to navigate. Use bottom middle part of toughscreen to jump.", 
                Toast.LENGTH_SHORT).show();
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
