package com.buildman.buildman.manpowertabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.buildman.buildman.activity.Home;
import com.buildman.buildman.activity.R;

public class Indent_Manpower extends Activity {
	
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.indent_manpower);       

}
	 @Override
	 public void onBackPressed() {
	 				// do something on back.
//	 			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
	 		 Intent i=new Intent(Indent_Manpower.this,Home.class);		
	 		startActivity(i);
	 		finish();
	 		return;
	 			}		
}