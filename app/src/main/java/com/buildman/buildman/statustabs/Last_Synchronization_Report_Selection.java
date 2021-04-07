package com.buildman.buildman.statustabs;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Last_Sync_Report_Model;

public class Last_Synchronization_Report_Selection extends Activity {
	DatabaseHandler db;	
//	private int party_id_fmLoginDB,org_id_fmLoginDB;
	private EditText mNoOfDays_Edt;
	private ImageView mBack_Img;	
	private Spinner mSpinnerDropDown_ModuleNames;
	private CheckBox mSetAsDefault_Cb;
	private Button mGetRecords_Btn;
	private String checkbox_value;
	private int mModule_Id_DropDown, module_Id_Global,number_of_days_Global;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.last_synchronization_report_selection);
        db=new DatabaseHandler(Last_Synchronization_Report_Selection.this);
        getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>Buildman</font>"));	    
        getActionBar().setSubtitle(Html.fromHtml("<font color='#6f6f6f'>Last Synchronization Report</font>"));
        
        getIds(); 
        bind_DataFmDB();
        spinner_DropDown_ModuleNames();   
        check_Box_Click();		
        getRecords_Btn_Click();
        focusEdtText() ;
       
    }
    @Override
   	public void onBackPressed() {
   			// do something on back.
//   		Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
       	Intent i=new Intent(Last_Synchronization_Report_Selection.this,StatusTabs.class);
   		startActivity(i);
   		finish();
   	return;
   		}	
   
	private void bind_DataFmDB() {
		// TODO Auto-generated method stub
		  db=new DatabaseHandler(Last_Synchronization_Report_Selection.this);
		 Last_Sync_Report_Model model= db.get_FirstRow_LastSynReport(1);
		 Integer module_Id_int,number_of_days_int;
		 module_Id_int=model.getModule_ID_LastSynReport() ;  
		 number_of_days_int=model.getNumber_Of_Days_LastSynReport(); 
		if(module_Id_int!= null){
			module_Id_Global=module_Id_int;
			number_of_days_Global=number_of_days_int;
		}else{
			module_Id_Global=0;
			number_of_days_Global=0;
		}
		 mNoOfDays_Edt.setText(String.valueOf(number_of_days_Global));
	}

	private void getRecords_Btn_Click() {
		// TODO Auto-generated method stub
    	mGetRecords_Btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mNoOfDays_Edt.getText().length()==0){
		        	mNoOfDays_Edt.setError("Please fill the field");		        	
		        }else{
		        	isInternetOn();					
				}				
				
			}
		});
	}
	public final boolean isInternetOn() {
		// TODO Auto-generated method stub
		 // get Connectivity Manager object to check connection
        ConnectivityManager connec =  
                       (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
         
           // Check for network connections
            if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                 connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            	
            	String selectedSpinner_ModuleName=String.valueOf(mSpinnerDropDown_ModuleNames.getSelectedItem());
				
				if(selectedSpinner_ModuleName.equals("All")){
					mModule_Id_DropDown=0;
				 }else if (selectedSpinner_ModuleName.equals("Material")) {
					 mModule_Id_DropDown=1;
				}else if (selectedSpinner_ModuleName.equals("Manpower")) {
					 mModule_Id_DropDown=2;
				}else if (selectedSpinner_ModuleName.equals("Status")) {
					 mModule_Id_DropDown=5;
				}
            	 Intent i=new Intent(Last_Synchronization_Report_Selection.this,Last_Synchronization_Report.class);	
            	 
            	 i.putExtra("module_Id_Send_LastSynReport", mModule_Id_DropDown);
            	 i.putExtra("NumberOfDays_Send_LastSynReport", mNoOfDays_Edt.getText().toString());
					startActivity(i);
					finish();
					
//					 Toast.makeText(getBaseContext(), "True"+mModule_Id_DropDown, Toast.LENGTH_SHORT).show();
                return true;
                 
            } else if ( 
              connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
              connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            	
            	 Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_SHORT).show();
                return false;
            }
          return false;	
	}
	
	private void spinner_DropDown_ModuleNames() {
		// TODO Auto-generated method stub
		
		    List<String> spinner_List = new ArrayList<String>();		   
		    spinner_List.add("All");
		    spinner_List.add("Material");
		    spinner_List.add("Manpower");
		    spinner_List.add("Status");
		  
		    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, spinner_List);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mSpinnerDropDown_ModuleNames.setAdapter(dataAdapter);	
				
				String mDropDown_Module_mName = null;
				if(module_Id_Global==0){
					mDropDown_Module_mName="All";
				 }else if (module_Id_Global==1) {
					 mDropDown_Module_mName="Material";
				}else if (module_Id_Global==2) {
					mDropDown_Module_mName="Manpower";
				}else if (module_Id_Global==5) {
					mDropDown_Module_mName="Status";
				}
				
				for(int i=0; i < dataAdapter.getCount(); i++) {
					  if(mDropDown_Module_mName.trim().equals(dataAdapter.getItem(i).toString())){
						  mSpinnerDropDown_ModuleNames.setSelection(i);
					    break;
					  }
					}
				
				// Set the ClickListener for Spinner
				mSpinnerDropDown_ModuleNames.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() { 

				              public void onItemSelected(AdapterView<?> adapterView, 
				             View view, int i, long l) { 
				             // TODO Auto-generated method stub
			             
				               }
				                // If no option selected
				    public void onNothingSelected(AdapterView<?> arg0) {
				     // TODO Auto-generated method stub			          
				    } 

				        });         
     
    }
	 private void check_Box_Click() {
			// TODO Auto-generated method stub
		 db=new DatabaseHandler(Last_Synchronization_Report_Selection.this);
		 mSetAsDefault_Cb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
				if(((CheckBox) v).isChecked())
				{
					if(mNoOfDays_Edt.getText().length()==0){
						mSetAsDefault_Cb.setChecked(false);
						mNoOfDays_Edt.setError("Please fill the field");
					}else{
					
						String selectedSpinner_ModuleName=String.valueOf(mSpinnerDropDown_ModuleNames.getSelectedItem());
						
						if(selectedSpinner_ModuleName.equals("All")){
							mModule_Id_DropDown=0;
						 }else if (selectedSpinner_ModuleName.equals("Material")) {
							 mModule_Id_DropDown=1;
						}else if (selectedSpinner_ModuleName.equals("Manpower")) {
							 mModule_Id_DropDown=2;
						}else if (selectedSpinner_ModuleName.equals("Status")) {
							 mModule_Id_DropDown=5;
						}
						int mNo_of_days=Integer.parseInt(mNoOfDays_Edt.getText().toString());
						if(db.getLastSynReportTable_Row_Count()==0){
								db.add_LastSynReports_Record(new Last_Sync_Report_Model(mModule_Id_DropDown, mNo_of_days));
						}else{
								Last_Sync_Report_Model model= db.get_FirstRow_LastSynReport(1);
								if(model!=null)
				            	  {    
				            		  model.setModule_ID_LastSynReport(mModule_Id_DropDown) ;  
				            		  model.setNumber_Of_Days_LastSynReport(mNo_of_days) ; 				            		
           		 
				            		  db.Update_LastSynReport_Row(model);                	                    		
           		  
				            	  }
				            	  db.Update_LastSynReport_Row(model);
							
						}
				
						
					}
					
				}else{
//					Toast.makeText(getBaseContext(), "false", Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		}

		private void focusEdtText() {
			// TODO Auto-generated method stub
			mNoOfDays_Edt.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					
					  if (hasFocus) {					  
							  
						  mNoOfDays_Edt.setText("");
						   } 
						 
						   else {
							  
							 
						   }
				}
			});
		}
	private void getIds() {
		// TODO Auto-generated method stub
		mSpinnerDropDown_ModuleNames = (Spinner)findViewById(R.id.spinner_modulenames_lastsynreportselection);
		mNoOfDays_Edt=(EditText)findViewById(R.id.no_of_days_edt_lastsynreportselection);
		mSetAsDefault_Cb = (CheckBox)findViewById(R.id.set_as_default_checkbox_lastsynreportselection);
		mGetRecords_Btn = (Button)findViewById(R.id.get_records_lastsynreportselection);
	}

}
