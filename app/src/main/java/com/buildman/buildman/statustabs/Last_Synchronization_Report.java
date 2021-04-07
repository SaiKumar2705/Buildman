package com.buildman.buildman.statustabs;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.LastSynchronizationReport_Expandable_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.LastSyncReport_Child_Model;
import com.buildman.buildman.model.LastSyncReport_Parent_Model;
import com.buildman.buildman.model.Last_Sync_Report_Model;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.utils.Utils;
import com.buildman.buildman.web.ServerConnection;


public class Last_Synchronization_Report extends Activity {
	DatabaseHandler db;
	private ProgressDialog pDialog;
	private int party_id_fmLoginDB,org_id_fmLoginDB;
	private String module_id_string,number_of_days_string;
	private ImageView mBack_Img,mFillter_Img;	
    private ExpandableListView mExpandableList;
    private static String url_Get_LastSyn_Report;
    LastSynchronizationReport_Expandable_Adapter adapter;
    ArrayList<Last_Sync_Report_Model> site_Details_List = new ArrayList<Last_Sync_Report_Model>();
    ArrayList<Last_Sync_Report_Model> syn_Details_List = new ArrayList<Last_Sync_Report_Model>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.last_synchronization_report);
        db=new DatabaseHandler(Last_Synchronization_Report.this);
        getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>Buildman</font>"));	    
        getActionBar().setSubtitle(Html.fromHtml("<font color='#6f6f6f'>Last Synchronization Report</font>"));
        getIds(); 
        getData();
        get_Ids_FmDB_LoginTable();      
        isInternetOn();
        back_Img_Click();
        fillter_Img_Click();        
    }
   
    @Override
	public void onBackPressed() {
			// do something on back.
//		Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
    	Intent i=new Intent(Last_Synchronization_Report.this,StatusTabs.class);
		startActivity(i);
		finish();
	return;
		}	

	


	private void getData() {
		// TODO Auto-generated method stub
		if(db.getLastSynReportTable_Row_Count()==0){
    		module_id_string=String.valueOf(getIntent().getIntExtra("module_Id_Send_LastSynReport", 0));
    		number_of_days_string=getIntent().getStringExtra("NumberOfDays_Send_LastSynReport");
    	}else {
    		Last_Sync_Report_Model model= db.get_FirstRow_LastSynReport(1);
    		int module_Id=model.getModule_ID_LastSynReport() ;  
    		int number_of_days=model.getNumber_Of_Days_LastSynReport() ; 	
    		module_id_string=String.valueOf(module_Id);
    		number_of_days_string=String.valueOf(number_of_days);
		}
	}

	private void fillter_Img_Click() {
		// TODO Auto-generated method stub
mFillter_Img.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Last_Synchronization_Report.this,Last_Synchronization_Report_Selection.class);
				startActivity(i);
				finish();
			}
		});
	}
	
	private void getIds() {
		// TODO Auto-generated method stub
    	
    	mExpandableList = (ExpandableListView)findViewById(R.id.expandablelist_lastsynreport);
    	mBack_Img=(ImageView)findViewById(R.id.back_img_lastsynreport);
    	mFillter_Img=(ImageView)findViewById(R.id.fillter_img_lastsynreport);
	}
    private void get_Ids_FmDB_LoginTable() {
		// TODO Auto-generated method stub
	    //  get party_id and org_id fm Login table which always have only 1 row
	        db=new DatabaseHandler(Last_Synchronization_Report.this);    
			Login_Model login_model= db.get_FirstRow_Login(1);
			party_id_fmLoginDB=login_model.getParty_ID();
			org_id_fmLoginDB=login_model.getOrg_ID();
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
                
            	new Get_LastSyn_Report().execute();    	
            	
                return true;
                 
            } else if ( 
              connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
              connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            	
           	 Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_SHORT).show();
                return false;
            }
          return false;	
	}
    private void back_Img_Click() {
		// TODO Auto-generated method stub
    	mBack_Img.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Last_Synchronization_Report.this,StatusTabs.class);
				startActivity(i);
				finish();
			}
		});
	}
    private class Get_LastSyn_Report extends AsyncTask<Void, Void, Void> {
    	ArrayList<Integer> check_site_idsList = new ArrayList<Integer>();
    	@Override
		protected void onPreExecute(){
			super.onPreExecute();
		 pDialog=new ProgressDialog(Last_Synchronization_Report.this);			 
		 pDialog.setMessage("Please Wait.");
		 pDialog.setCancelable(false);
		 pDialog.show();
		}
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			 // Creating service handler class instance	
			String party_id_string=String.valueOf(party_id_fmLoginDB);	
			String org_id_string=String.valueOf(org_id_fmLoginDB);			
			
			
			StringBuffer lastSyn_Report_sb=new StringBuffer();			

           
        	
			lastSyn_Report_sb.append(Utils.URL+"GetLastSyncReport"+"?"+"PartyId="+party_id_string+"&OrgId="+org_id_string+"&NoOfDays="+number_of_days_string+"&ModuleId="+module_id_string);
	       
	        url_Get_LastSyn_Report=lastSyn_Report_sb.toString();
	      
	       System.out.println("get last syn report string>>>>>>>>"+url_Get_LastSyn_Report);
			ServerConnection hh = new ServerConnection();
			String jsonStr = hh.getmethods(url_Get_LastSyn_Report);
            System.out.println("json string>>>>>>>>"+jsonStr);
  if(jsonStr!=null){
    	try {
                // Extract JSON array from the response
                JSONArray jArray = new JSONArray(jsonStr);
                System.out.println(jArray.length());
                if(jArray.length()!=0)
                {
                		for(int i=0; i<jArray.length(); i++)
                		{                				
                		JSONObject jObject1 = jArray.getJSONObject(i); 
                		
                				String site_id=jObject1.getString("SiteId");  		                	        		
                				String site_name =jObject1.getString("SiteName");  
                				String module_id =jObject1.getString("ModuleId");  
                				String module_name=jObject1.getString("ModuleName");  
                				String numbOfDays=jObject1.getString("NoOfDays");  
                				String party_id=jObject1.getString("PartyId");  
                				String mobile_numb=jObject1.getString("MobileNo");  
                				String identifer=jObject1.getString("Identifier");
                				
                				int	mSite_Id_result=Integer.parseInt(site_id);
                				int	mModule_Id_result=Integer.parseInt(module_id);
                				int	mNumbOfDays_result=Integer.parseInt(numbOfDays);                				
                				int	mParty_Id_result=Integer.parseInt(party_id);
                				int	mIdentifier_result=Integer.parseInt(identifer);
                				
                				Last_Sync_Report_Model last_syn_report_model1 = new Last_Sync_Report_Model
                						(mSite_Id_result,site_name,mobile_numb,mIdentifier_result,mModule_Id_result,
                								module_name,mNumbOfDays_result	);
                			
                				
//                				if (check_site_idsList.contains(mSite_Id_result)) {
//                				    System.out.println("Account  found"+check_site_idsList.size());
//                				} else {
//                					site_Details_List.add(last_syn_report_model1);  					
//                					
//                					check_site_idsList.add(mSite_Id_result);                				    
//                				}                				
                				site_Details_List.add(last_syn_report_model1);
                				
            	 	          Last_Sync_Report_Model last_syn_report_model2= new Last_Sync_Report_Model
              						(site_name,mModule_Id_result,module_name,mNumbOfDays_result,mParty_Id_result);
                					 
            	 	         syn_Details_List.add(last_syn_report_model2); 	
            	 	         
            	 	      //create a custom object            	 	      
//            	 	        Last_Sync_Report_Model user1 = new Last_Sync_Report_Model(site_name);	      
//            	 	        
//            	 	        if (syn_Details_List.contains(user1)) {
//            	 	        	 System.out.println("list contains  is---------: "+syn_Details_List.size());
//            				} else {
//            					 syn_Details_List.add(last_syn_report_model2); 	           					
//            					 System.out.println("not contains is---------: "+syn_Details_List.size());            				    
//            				}
            	 	         
            	 	         
         				 			
//         					for loop endimg  		 
                			}
                		  runOnUiThread(new Runnable(){

                  	          @Override
                  	          public void run(){
                  	           
                  	            // display toast here 
                  	        	  Toast.makeText(getApplicationContext(), "Synchronization report collected", Toast.LENGTH_SHORT).show();
                  	        
                  	          }
                  	       });                
//              if loop ending		         				
                }
               else {
            	   runOnUiThread(new Runnable(){

            	          @Override
            	          public void run(){
            	           
            	            // display toast here 
            	        	  Toast.makeText(getApplicationContext(), "Synchronization report not available", Toast.LENGTH_SHORT).show();
            	          }
            	       });
            	  
               		}
    	} 
    	catch (JSONException e)
    	{
                // TODO Auto-generated catch block
                    	e.printStackTrace();
    	}	                  
   }
else {
              Log.e("ServiceHandler", "Couldn't get any data from the url");
              runOnUiThread(new Runnable(){

    	          @Override
    	          public void run(){
    	           
    	            // display toast here 
    	        	  Toast.makeText(getApplicationContext(), "Couldn't get any data from the url", Toast.LENGTH_SHORT).show();
    	          }
    	       });
          }
        	return null;	        	
		}
    	protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(pDialog.isShowing());
			pDialog.dismiss();
			int module_Id=Integer.parseInt(module_id_string);
//			int no_of_days=Integer.parseInt(number_of_days_string);
			if(module_Id==0 ){
			sort_List_BySiteName();
			addData_toExpandableListview();
			}else{
				addData_toExpandableListview();
			}
			
		   
			
				}
	
    }
	private void sort_List_BySiteName() {
		// TODO Auto-generated method stub
		Collections.sort(site_Details_List, Last_Sync_Report_Model.SiteDetailsList_SiteNameComparator);
	 	  for(Last_Sync_Report_Model str: site_Details_List){
				System.out.println("name="+str.getSite_Name_LastSynReport());
		   }
	 	  
	 	 Collections.sort(syn_Details_List, Last_Sync_Report_Model.SynDetailsList_SiteNameComparator);
	 	  for(Last_Sync_Report_Model str: syn_Details_List){
				System.out.println("name="+str.getSite_Name_LastSynReport());
		   }
	}
    private void addData_toExpandableListview() {
		// TODO Auto-generated method stub
    	 ArrayList<LastSyncReport_Parent_Model> arrayParents = new ArrayList<LastSyncReport_Parent_Model>();
         ArrayList<LastSyncReport_Child_Model> arrayChildren = new ArrayList<LastSyncReport_Child_Model>();
         ArrayList<String> check_Site_Module_Details_List = new ArrayList<String>();
        
         LinearLayout relative = (LinearLayout) findViewById(R.id.listview_linearlayout_lastsynreport);
         if(site_Details_List.size()==0){
       	
       	  relative.setBackgroundResource(0); 
         }else {
       	  relative.setBackgroundResource(R.drawable.box_bottom_gray_border);
         }
         
         
         //here we set the parents and the children
         for (int i = 0; i < site_Details_List.size(); i++){
        	 Last_Sync_Report_Model model=site_Details_List.get(i);
	    	 String site_names = model.getSite_Name_LastSynReport();
	    	 int site_id = model.getSite_ID_LastSynReport();	    	 
	    	 String mobile_number = model.getMobile_Numb_LastSynReport();
	    	 int identifier_value=model.getIdentifier_LastSynReport();
	    	 int module_id=model.getModule_ID_LastSynReport();
	    	 String module_Name=model.getModule_Name_LastSynReport();
	    	 int number_of_Days=model.getNumber_Of_Days_LastSynReport();
	    	 System.out.println("id_valuee"+identifier_value);
	    	 
	    	 arrayChildren = new ArrayList<LastSyncReport_Child_Model>();	    	 
	    	    
//             for (int j = 0; j < syn_Details_List.size(); j++) {
//            	 Last_Sync_Report_Model syn_model=syn_Details_List.get(j);
//            	 
//            	 
//            	 
//            	String site_name=syn_model.getSite_Name_LastSynReport();
//            	 int module_id=syn_model.getModule_ID_LastSynReport();
//            	 String module_name=syn_model.getModule_Name_LastSynReport();
//            	 int number_Of_Days=syn_model.getNumber_Of_Days_LastSynReport();
//            	 
//            	 
//            	           	 
//            	 LastSyncReport_Child_Model child_model = new LastSyncReport_Child_Model();
//            	 child_model.setModule_ID_Child_LastSynReport(module_id);
//            	 child_model.setModule_Name_Child_LastSynReport(module_name);
//            	 child_model.setNumberOfDays_Child_LastSynReport(number_Of_Days);           	
//            	 
//            	 
//            	
//            	 
//	 	      arrayChildren.add(child_model);
//            	 
//            	
//            	 
//             }
	    	 
	    	 LastSyncReport_Child_Model child_model = new LastSyncReport_Child_Model();
        	 child_model.setModule_ID_Child_LastSynReport(module_id);
        	 child_model.setModule_Name_Child_LastSynReport(module_Name);
        	 child_model.setNumberOfDays_Child_LastSynReport(number_of_Days); 
        	 
        	 arrayChildren.add(child_model);
           
  
             LastSyncReport_Parent_Model appadd = new LastSyncReport_Parent_Model(site_names,mobile_number,identifier_value ,arrayChildren);
             arrayParents.add(appadd);   
         }
         adapter = new LastSynchronizationReport_Expandable_Adapter(Last_Synchronization_Report.this,arrayParents);
         mExpandableList.setAdapter(adapter);
        
	}
}
