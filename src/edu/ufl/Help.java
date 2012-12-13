package edu.ufl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Help extends Activity
{
	 
	ImageButton button;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
	}
	
	
	public void back(View v){
			try{
			        Class<?> myClass = Class.forName("edu.ufl.Menu");
			        Intent intent = new Intent(Help.this,myClass);
			        startActivity(intent);
			    }
			    catch(ClassNotFoundException e){
			        e.printStackTrace();
			    }
				
	}
	
	public void play(View v){
				try{
			        Class<?> myClass = Class.forName("edu.ufl.LevelSelect");
			        Intent intent = new Intent(Help.this,myClass);
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