package com.buildman.buildman.expensestabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.buildman.buildman.activity.R;

public class Payer_List extends Activity {
    ListView listView ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_common_expenses);
        
        android.app.ActionBar bar=getActionBar();
        getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>Buildman </font>"));
        
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        
        // Defined Array values to show in ListView
        String[] values = new String[] { "Accounts", 
                                         "Self Account",
                                         "Account-1",
                                         "Account-2", 
                                         "Account-3", 
                                         "Account-4", 
                                         "Account-5", 
                                         "Account-6" 
                                        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        
        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
                
               // ListView Clicked item index
               int itemPosition     = position;
               
               // ListView Clicked item value
               String  itemValue    = (String) listView.getItemAtPosition(position);
                  
               
             Intent i=new Intent(Payer_List.this,Income_Expense_Description.class);
//             i.putExtra("payer_selected_item", itemValue);
//             passing payer_selected_item to consultation_summary class
			  SharedPreferences payer_item_pref = Payer_List.this.getSharedPreferences("Payer_item_MyPrefs", Context.MODE_PRIVATE);		
			  	SharedPreferences.Editor payer_item_edit = payer_item_pref.edit();	
			  	payer_item_edit.putString("Payer_item_KEY_PREF",itemValue);	
			  	payer_item_edit.commit();
             startActivity(i);
              }

         }); 
    }

}
