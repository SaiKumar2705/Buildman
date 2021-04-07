package com.buildman.buildman.manpowertabs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.activity.Home;
import com.buildman.buildman.activity.Login;
import com.buildman.buildman.activity.R;
import com.buildman.buildman.activity.UserSites;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.Manpower_Model;
import com.buildman.buildman.model.UserSites_Model;
import com.buildman.buildman.utils.Utils;
import com.buildman.buildman.web.ServerConnection;


public class ManpowerTabs extends TabActivity {
    /** Called when the activity is first created. */
	TabHost tabHost;
	int selected_SiteID, org_id,party_id,project_id,module_Id=2;
	private String mDate_Time_Now,mCurrent_date,versionName,selected_SiteName;
    public final static int SUPPLY= 0;
	public final static int CONTRACT = 1;
	private static final int RAISE_INDENT = 2;
	private ProgressDialog pDialog_UserType,pDialog_GetNewManPower_SUP,pDialog_Send_MP_SUP,
	pDialog_Get_MP_CONT,pDialog_AppVersion,	pDialog_SynMaster;
	DatabaseHandler db;
	private static String url_GetUserType;
	private static String url_GetNewManPower_SUP;
	private static String url_send_InsertMpSupplyContractSyncMaster_SUP;
	private static String url_GetNewLabourContractWorkDetails_CONT;
	private static String url_UpdateAppVersion;
	private static String url_send_SynMaster;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manpowertabs);
        android.app.ActionBar bar=getActionBar();
        
        SharedPreferences versionName_pref1 = getSharedPreferences("VersonName_Login_MyPrefs", Context.MODE_PRIVATE);		 
        versionName= versionName_pref1.getString("VersionName_Login_KEY_PREF", "");
        
        SharedPreferences siteName_pref1 = getSharedPreferences("Selected_SiteName_UserSite_MyPrefs", Context.MODE_PRIVATE);		 
        selected_SiteName= siteName_pref1.getString("Selected_SiteName_UserSite_KEY_PREF", "");
	  
	    SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);		 
	    selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);
	      
      getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>"+selected_SiteName+"</font>"));
      getActionBar().setSubtitle(Html.fromHtml("<font color='#6f6f6f'>Manpower </font>"));
      
      
       tabHost = getTabHost();

        // Tab for Photos
        TabSpec photospec = tabHost.newTabSpec("Supply");
        // setting Title and Icon for the Tab
        photospec.setIndicator("Supply");
        Intent photosIntent = new Intent(this, Supply_Manpower.class);
        photospec.setContent(photosIntent);
         
        // Tab for Songs
        TabSpec songspec = tabHost.newTabSpec("Contract");        
        songspec.setIndicator("Contract");
        Intent songsIntent = new Intent(this, Contract_Manpower.class);
        songspec.setContent(songsIntent);
        
        // Tab for Raise Indent
        TabSpec indentspec = tabHost.newTabSpec("Indent");        
        indentspec.setIndicator("Indent");
        Intent indentIntent = new Intent(this, Indent_Manpower.class);
        indentspec.setContent(indentIntent);
      
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(photospec); // Adding photos tab
        tabHost.addTab(songspec); // Adding songs tab
        tabHost.addTab(indentspec); // Adding songs tab
        
       
// styling tabs strip before selection after selection
        
        TabWidget widget = tabHost.getTabWidget();
        for(int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);

            // Look for the title view to ensure this is an indicator and not a divider.
            TextView tv = (TextView)v.findViewById(android.R.id.title);
            tv.setTextSize(getResources().getDimension(R.dimen.tab_textview));
            if(tv == null) {
                continue;
            }
            v.setBackgroundResource(R.drawable.tab_indicator);
        }

        System.out.println(""+tabHost.getCurrentTab());
      
        int type = 0;
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("from_manpower")) {
                type = getIntent().getExtras().getInt("from_manpower");
                switch (type) {
                case SUPPLY:
                	tabHost.setCurrentTab(0);
                case CONTRACT:
                	tabHost.setCurrentTab(1);
                case RAISE_INDENT:
                	tabHost.setCurrentTab(2);
               
                default:
                	tabHost.setCurrentTab(1);
                }
                if(type==0){
                	tabHost.setCurrentTab(0);
                }else if (type==1) {
                	tabHost.setCurrentTab(1);
				}else if (type==2) {
					tabHost.setCurrentTab(2);
				}
            }
        }
        

    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manpower_menu, menu);
       
        return super.onCreateOptionsMenu(menu);
    }
 
  
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        super.onOptionsItemSelected(item);
 
        switch(item.getItemId()){
        case R.id.home_manpowermenu:
            break;        

           case R.id.house_manpowermenu:              
           	Intent i_home=new Intent(ManpowerTabs.this,Home.class);
             	 startActivity(i_home);
             	finish();
		           break;       
		           
           case R.id.switchsite_manpowermenu:                
           	Intent i_switchsite=new Intent(ManpowerTabs.this,UserSites.class);
             	 startActivity(i_switchsite);
             	finish();
		           break;           
       
	        case R.id.logout_manpowermenu:
	        	Intent i=new Intent(ManpowerTabs.this,Login.class);
	        	startActivity(i);
	        	finish();
		           break;
		           
           case R.id.syn_manpowermenu: 
        	   dateTime_Now();
        	   getParameters_IDfmDB();         
        	   isInternetOn();  
               break;

          
            }
            return true;
 
    }  
	private void dateTime_Now() {
		// TODO Auto-generated method stub
		mDate_Time_Now=Utils.sdf.format(new Date());
		mCurrent_date=Utils.sdf.format(new Date());
	}
private void getParameters_IDfmDB() {
		// TODO Auto-generated method stub
	 db=new DatabaseHandler(ManpowerTabs.this);
	 
	 Login_Model login_model=db.get_FirstRow_Login(1);
     org_id=login_model.getOrg_ID();
	 party_id=login_model.getParty_ID(); 	
 	
 	UserSites_Model usersites_model=db.find_Site_ID_UserSitesTable(selected_SiteID);
 	  project_id=usersites_model.getProject_ID();    	
 	
		
		
	}


public final boolean isInternetOn() {
        
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =  
                       (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
         
           // Check for network connections
            if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                 connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
                
                // if connected with internet   
            	 new Get_UserType().execute();  
//                 check usertype is primary or not             	
              		check_UserType();            	
            	 new GetNewManPower_Supply().execute();            	
            	 new GetNewLabourContractWorkDetails_MP_Contract().execute();
            	 new UpdateAppVersion().execute(); 
            	 new Send_SynMaster().execute();
        	  
                return true;
                 
            } else if ( 
              connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
              connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
               
                Toast.makeText(this, "Please connect to internet for Synchornization", Toast.LENGTH_SHORT).show();
                return false;
            }
          return false;
        }
private class Get_UserType extends AsyncTask<Void, Void, Void> {
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
	 pDialog_UserType=new ProgressDialog(ManpowerTabs.this);
	 
	 pDialog_UserType.setMessage("Please Wait.");
	 pDialog_UserType.setCancelable(false);
	 pDialog_UserType.show();
	}
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		 // Creating service handler class instance
		db=new DatabaseHandler(ManpowerTabs.this); 
		
		
		StringBuffer sb_UserType=new StringBuffer();
		sb_UserType.append(Utils.URL+"GetUserType"+"?"+"PartyId="+party_id+"&SiteId="+selected_SiteID+"&OrgId="+org_id);
        url_GetUserType=sb_UserType.toString();
        System.out.println("usertype:"+url_GetUserType.toString());
		ServerConnection hh = new ServerConnection();
		String jsonStr = hh.getmethods(url_GetUserType);
        System.out.println("json string of user_type>>>>>>>>"+jsonStr);
if(jsonStr!=null){
	try {
            // Extract JSON array from the response
            JSONArray jArray = new JSONArray(jsonStr);
            System.out.println(jArray.length());
            if(jArray.length()!=0)
            {
            		
            			String user_type_portal=jArray.getString(0); 
            			System.out.println("User Type is:"+user_type_portal);      
            			UserSites_Model model=db.find_PartyID_SiteID_UserSitesTable(party_id, selected_SiteID);
            			if(model!=null)
            			{    
 	                    		 model.setUserType(user_type_portal);
 	                    		 db.Update_UserSitesTable(model);
 	                    		 runOnUiThread(new Runnable(){

 	                  	          @Override
 	                  	          public void run(){
 	                  	           
 	                  	            // display toast here 
	                  	        	  Toast.makeText(getApplicationContext(), "User type updated", Toast.LENGTH_SHORT).show();
 	                  	          }
 	                  	       });
            			}
            			 db.Update_UserSitesTable(model);              			
            }
           else {
        	   runOnUiThread(new Runnable(){

        	          @Override
        	          public void run(){
        	           
        	            // display toast here 
//        	        	  Toast.makeText(getApplicationContext(), "invalid User type", Toast.LENGTH_SHORT).show();
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
		if(pDialog_UserType.isShowing());
		pDialog_UserType.dismiss();		
			}

}
private void check_UserType() {
	// TODO Auto-generated method stub
	UserSites_Model model=db.find_PartyID_SiteID_UserSitesTable(party_id, selected_SiteID);
	if(model!=null)
	{    
     		String userType_inDB= model.getUserType();
     		if(userType_inDB.equals("PRIMRY")){
//     			transaction data of materials to be synched for only primary_type user
     			 new send_MP_Supply().execute();
     		}else{
     			Toast.makeText(this, "User type is restricted to transfer data", Toast.LENGTH_SHORT).show();
     		}
	}
	
}
private class GetNewManPower_Supply extends AsyncTask<Void, Void, Void> {
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		pDialog_GetNewManPower_SUP=new ProgressDialog(ManpowerTabs.this);
	 
		pDialog_GetNewManPower_SUP.setMessage("Please Wait.");
		pDialog_GetNewManPower_SUP.setCancelable(false);
		pDialog_GetNewManPower_SUP.show();
	}
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		 // Creating service handler class instance
		String party_Id_String=String.valueOf(party_id);
		String site_Id_String=String.valueOf(selected_SiteID);
		String project_Id_String=String.valueOf(project_id);		
		String org_Id_String=String.valueOf(org_id);
		String module_Id_String=String.valueOf(module_Id);
		
		StringBuffer sb_New_MP_SUP=new StringBuffer();
		sb_New_MP_SUP.append(Utils.URL+"GetNewManPower"+"?"+"PartyId="+party_Id_String+"&SiteId="+site_Id_String+"&ProjectId="+project_Id_String+"&OrgId="+org_Id_String+"&MobModuleId="+module_Id_String);

		url_GetNewManPower_SUP=sb_New_MP_SUP.toString();
	   	System.out.println("send to server "+url_GetNewManPower_SUP);
		ServerConnection hh = new ServerConnection();
		String jsonStr = hh.getmethods(url_GetNewManPower_SUP);
        
       
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
     			JSONArray jArray1 = jArray.getJSONArray(i); 
        		
    			String contract_project_id_MP_SUP_portal=jArray1.getString(0);   	
    	        String contractor_id_MP_SUP_portal =jArray1.getString(1);    		
    			String contractor_name_MP_SUP_portal=jArray1.getString(2);	
    			String project_id_MP_SUP_portal=jArray1.getString(3);
    			String site_id_MP_SUP_portal=jArray1.getString(4);
    			String labour_type_id_MP_SUP_portal=jArray1.getString(5);
    			String labour_type_name_MP_SUP_portal=jArray1.getString(6);
    			String total_till_date_qty_MP_SUP_portal=jArray1.getString(7);
    			String rate_MP_SUP_portal=jArray1.getString(8);
    			String total_paid_amt_MP_SUP_portal=jArray1.getString(9);
    			String balc_amt_MP_SUP_portal=jArray1.getString(10);
    			String constant_String_portal=jArray1.getString(11);	
    			
    			int contract_project_id_MP_SUP_int=Integer.parseInt(contract_project_id_MP_SUP_portal);
    			int contractor_id_MP_SUP_int=Integer.parseInt(contractor_id_MP_SUP_portal);
    			int project_id_MP_SUP_int=Integer.parseInt(project_id_MP_SUP_portal);
    			int site_id_MP_SUP_int=Integer.parseInt(site_id_MP_SUP_portal);
    			int labour_type_id_MP_SUP_int=Integer.parseInt(labour_type_id_MP_SUP_portal);
    			
//    			Total_billed_amt calculation
				 float total_Billed_Amt_float=((Float.parseFloat(total_till_date_qty_MP_SUP_portal))*(Float.parseFloat(rate_MP_SUP_portal)));			      
				String total_Billed_Amt_Result=String.format("%.2f", total_Billed_Amt_float);  
				
//    			balc_amt calculation=total_billed_amt-total_paid_amt 
				 float balc_Amt_float=(total_Billed_Amt_float)-(Float.parseFloat(total_paid_amt_MP_SUP_portal));			      
				String balance_Amt_Result=String.format("%.2f", balc_Amt_float);  
				 
    			 System.out.println("consta value"+constant_String_portal);
     				
     				 			if(constant_String_portal.equals("I")){
//				 						inserting in database     				 			
     				 				db.add_MP_Supply_Record(new Manpower_Model(contract_project_id_MP_SUP_int,contractor_id_MP_SUP_int,contractor_name_MP_SUP_portal,project_id_MP_SUP_int,site_id_MP_SUP_int,labour_type_id_MP_SUP_int,labour_type_name_MP_SUP_portal,"0.00","0.00",total_till_date_qty_MP_SUP_portal,rate_MP_SUP_portal,total_Billed_Amt_Result,total_paid_amt_MP_SUP_portal,balance_Amt_Result,"Y","19-10-1952","19-10-1952","Y"));
     				 				 System.out.println("inserting in table");
     				 				 }else if (constant_String_portal.equals("DI"))
     				 				 {
									              if((db.CountRecords_Matched_SiteId_ContractorId_LabourTypeId_MP_SUPPLY_Table(site_id_MP_SUP_int, contractor_id_MP_SUP_int,labour_type_id_MP_SUP_int))==0){
									            	  db.add_MP_Supply_Record(new Manpower_Model(contract_project_id_MP_SUP_int,contractor_id_MP_SUP_int,contractor_name_MP_SUP_portal,project_id_MP_SUP_int,site_id_MP_SUP_int,labour_type_id_MP_SUP_int,labour_type_name_MP_SUP_portal,"0.00","0.00",total_till_date_qty_MP_SUP_portal,rate_MP_SUP_portal,total_Billed_Amt_Result,total_paid_amt_MP_SUP_portal,balance_Amt_Result,"Y","19-10-1952","19-10-1952","Y"));
									              }else{
								
									            	  Manpower_Model manpower_model= db.find_SiteId_ContractorId_LabourTypeId_Displayflag_MP_SUPPLY_Table(site_id_MP_SUP_int, contractor_id_MP_SUP_int,labour_type_id_MP_SUP_int,"Y");
									            	  if(manpower_model!=null)
									            	  {    
									            		manpower_model.setContract_Project_Id_MP_SUP(contract_project_id_MP_SUP_int);
									  	            	manpower_model.setContractor_Id_MP_SUP(contractor_id_MP_SUP_int);
									  	            	manpower_model.setContractor_Name_MP_SUP(contractor_name_MP_SUP_portal);
									  	            	manpower_model.setProj_Id_MP_SUP(project_id_MP_SUP_int);	            	
									  	           		manpower_model.setSite_Id_MP_SUP(site_id_MP_SUP_int);
									  	           		manpower_model.setLabourType_Id_MP_SUP(labour_type_id_MP_SUP_int);
									  	           		manpower_model.setLabourType_Name_MP_SUP(labour_type_name_MP_SUP_portal);
									  	           		manpower_model.setEntered_Qty_MP_SUP("0.00");
									  	           		manpower_model.setTodayEntered_QtySum_MP_SUP("0.00");
									  	           		manpower_model.setTotalTillDate_Qty_MP_SUP(total_till_date_qty_MP_SUP_portal);
									  	           		manpower_model.setRate_MP_SUP(rate_MP_SUP_portal);	           		
									  	           		manpower_model.setTotalBilledAmt_MP_SUP(total_Billed_Amt_Result);
									  	           		manpower_model.setTotalPaidAmt_MP_SUP(total_paid_amt_MP_SUP_portal);
									  	           		manpower_model.setBalance_Amt_MP_SUP(balance_Amt_Result);	           	
									  	           		manpower_model.setSyn_Flag_MP_SUP("Y");
									  	           		manpower_model.setCreated_Date_MP_SUP(mDate_Time_Now);
									  	           		manpower_model.setTransaction_Date_MP_SUP(mDate_Time_Now);           		
									  	           		manpower_model.setDisplay_Flag_MP_SUP("Y");
         	                    		 
         	                    		 
									            		  db.Update_MP_Supply_Row(manpower_model);                	                    		
         		                		  
									            	  	}
									            	  db.Update_MP_Supply_Row(manpower_model);
									              	}
									              
     				 				 }
     				
        		
        			 
//				 for loop ending
     		}
     		 		runOnUiThread(new Runnable(){

     		 			@Override
     		 			public void run(){
    	           
    	            // display toast here 
    	        	  Toast.makeText(getApplicationContext(), "Supply data collected", Toast.LENGTH_SHORT).show();
    	        	
     		 			}
     		 		});
     		
//  jArray.length() if loop ending      			
        }
       else {
    	   runOnUiThread(new Runnable(){

    	          @Override
    	          public void run(){
    	           
    	            // display toast here 
    	        	  Toast.makeText(getApplicationContext(), "No data available for Supply", Toast.LENGTH_SHORT).show();
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
		if(pDialog_GetNewManPower_SUP.isShowing());
		pDialog_GetNewManPower_SUP.dismiss();
		
			}
}
private class send_MP_Supply extends AsyncTask<Void, Void, Void> {
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
	 pDialog_Send_MP_SUP=new ProgressDialog(ManpowerTabs.this);
	 
	 pDialog_Send_MP_SUP.setMessage("Please Wait.");
	 pDialog_Send_MP_SUP.setCancelable(false);
	 pDialog_Send_MP_SUP.show();
	}
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		 // Creating service handler class instance
		db=new DatabaseHandler(ManpowerTabs.this);    
		
		
		  System.out.println("selected id is"+selected_SiteID);
	
     		ArrayList<Manpower_Model> send_supply_list = db.find_SiteID_SynFlag_List_MP_SUP(selected_SiteID,"N");       
     	
             for (Manpower_Model cn : send_supply_list) {
            	
            	 int  contract_project_id_portal = cn.getContract_Project_Id_MP_SUP();
            	 int   project_id_portal = cn.getPrj_Id_MP_SUP();   
            	 int   site_id_portal = cn.getSite_Id_MP_SUP();  
            	 final int  contractor_id_portal = cn.getContractor_Id_MP_SUP();              
                String  contractor_name_portal1 =cn.getContractor_Name_MP_SUP();
                String  contractor_name_portal =contractor_name_portal1.replace(" " , "%20");
                final int  labour_type_id_portal = cn.getLabourType_Id_MP_SUP();
                String  labour_type_name_portal1 = cn.getLabourType_Name_MP_SUP();
                String  labour_type_name_portal =labour_type_name_portal1.replace(" " , "%20");
                String  transaction_date_portal1 =cn.getTransaction_Date_MP_SUP();
                String  transaction_date_portal =transaction_date_portal1.replace(" " , "%20");
                String  today_qty_portal =cn.getTodayEntered_QtySum_MP_SUP();
                String  total_till_date_qty_portal =cn.getTotalTillDate_Qty_MP_SUP();                
               
                StringBuffer	sb_Send_MP_SUP=new StringBuffer();
                sb_Send_MP_SUP.append(Utils.URL+"InsertMpSupplyContractSyncMaster"+"?"+"data="+contract_project_id_portal+","+project_id_portal+","+site_id_portal+","+contractor_id_portal+","+contractor_name_portal+","+labour_type_id_portal+","+labour_type_name_portal+","+transaction_date_portal+","+today_qty_portal+","+total_till_date_qty_portal+","+party_id+","+org_id);

                url_send_InsertMpSupplyContractSyncMaster_SUP=sb_Send_MP_SUP.toString();
		System.out.println("send to server "+url_send_InsertMpSupplyContractSyncMaster_SUP);
				 ServerConnection hh = new ServerConnection();
				 String jsonStr = hh.getmethods(url_send_InsertMpSupplyContractSyncMaster_SUP);
        
       
        System.out.println("json string>>>>>>>>"+jsonStr);
        
            
        
if(jsonStr!=null){
	try {
            // Extract JSON array from the response
            JSONArray jArray = new JSONArray(jsonStr);
            System.out.println("length of json string is "+jArray.length());
            if(jArray.length()!=0)
            {
            	
            		String syn_status_value=jArray.getString(0);    
            		int syn_status_int=Integer.parseInt(syn_status_value);
            		System.out.println("Syn ststus value"+syn_status_int);
           				if(syn_status_int==1){
           					runOnUiThread(new Runnable(){

           						@Override
            	          	public void run(){
            	           
            	        	  ArrayList<Manpower_Model> db_list= db.find_ContractorId_LabourTypeId_SynFlag_MP_SUPPLY_Table(contractor_id_portal,labour_type_id_portal, "N") ;
            	                for (Manpower_Model model : db_list) {        	
            	              	 	
            	             	   model.setSyn_Flag_MP_SUP("Y");            		            
            	             	  
            	             db.Update_MP_Supply_Row(model);          		        
            	              	    
            	                }       
            	            // display toast here 
            	        	  Toast.makeText(getApplicationContext(), "Supply data transferred", Toast.LENGTH_SHORT).show();
            	        	 
            	          }
            	       });
           				}else{
           					runOnUiThread(new Runnable(){

           						@Override
           						public void run(){
          	           
          	            // display toast here 
          	        	  Toast.makeText(getApplicationContext(), "Supply data not transferred", Toast.LENGTH_SHORT).show();
           						}
           					});
           				}
            	

            			
            }
           else {
        	   runOnUiThread(new Runnable(){

        	          @Override
        	          public void run(){
        	           
        	            // display toast here 
//    	        	  Toast.makeText(getApplicationContext(), "Email or Password are incorrect", Toast.LENGTH_SHORT).show();
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


//for loop ending
             }  
    	return null;
    	
	}
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if(pDialog_Send_MP_SUP.isShowing());
		pDialog_Send_MP_SUP.dismiss();
		
			}
}

private class GetNewLabourContractWorkDetails_MP_Contract extends AsyncTask<Void, Void, Void> {
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		pDialog_Get_MP_CONT=new ProgressDialog(ManpowerTabs.this);
	 
		pDialog_Get_MP_CONT.setMessage("Please Wait.");
		pDialog_Get_MP_CONT.setCancelable(false);
        pDialog_Get_MP_CONT.show();
	}
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		 // Creating service handler class instance
		String party_Id_String=String.valueOf(party_id);
		String site_Id_String=String.valueOf(selected_SiteID);
		String project_Id_String=String.valueOf(project_id);		
		String org_Id_String=String.valueOf(org_id);
		String module_Id_String=String.valueOf(module_Id);
		
		StringBuffer sb_New_MP_CONT=new StringBuffer();
		sb_New_MP_CONT.append(Utils.URL+"GetNewLabourContractWorkDetails"+"?"+"PartyId="+party_Id_String+"&SiteId="+site_Id_String+"&ProjectId="+project_Id_String+"&OrgId="+org_Id_String+"&MobModuleId="+module_Id_String);

		url_GetNewLabourContractWorkDetails_CONT=sb_New_MP_CONT.toString();
		System.out.println("send to server "+url_GetNewLabourContractWorkDetails_CONT);
		ServerConnection hh = new ServerConnection();
		String jsonStr = hh.getmethods(url_GetNewLabourContractWorkDetails_CONT);
       
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
     			JSONArray jArray1 = jArray.getJSONArray(i); 
        		
    			String lab_contract_work_master_id_MP_CONT_portal=jArray1.getString(0);   	
    	        String contract_project_id_MP_CONT_portal =jArray1.getString(1);    		
    			String contractor_id_MP_CONT_portal=jArray1.getString(2);	
    			String contractor_name_MP_CONT_portal=jArray1.getString(3);
    			String project_id_MP_CONT_portal=jArray1.getString(4);
    			String site_id_MP_CONT_portal=jArray1.getString(5);
    			String work_loc_id_MP_CONT_portal=jArray1.getString(6);
    			String work_loc_name_MP_CONT_portal=jArray1.getString(7);
    			String total_qty_MP_CONT_portal=jArray1.getString(8);
    			String qty_completed_MP_CONT_portal=jArray1.getString(9);
    			String units_MP_CONT_portal=jArray1.getString(10);
    			String rate_MP_CONT_portal=jArray1.getString(11);
    			String billable_amt_MP_CONT_portal=jArray1.getString(12);
    			String paid_amt_MP_CONT_portal=jArray1.getString(13);
    			String balc_amt_MP_CONT_portal=jArray1.getString(14);
    			String scope_of_work_MP_CONT_portal=jArray1.getString(15);
    			String start_date_MP_CONT_portal=jArray1.getString(16);	
//    			start_date calculation
    			String start_date_MP_CONT_timestamp =start_date_MP_CONT_portal.split("\\(")[1].split("\\+")[0];
		        Date start_Date = new Date(Long.parseLong(start_date_MP_CONT_timestamp));
		        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		        String start_date_MP_CONT_portal_formated = sdf.format(start_Date);
    			
    			String constant_String_portal=jArray1.getString(17);	
    			
    			int lab_contract_work_master_id_MP_CONT=Integer.parseInt(lab_contract_work_master_id_MP_CONT_portal);
    			int contract_project_id_MP_CONT_int=Integer.parseInt(contract_project_id_MP_CONT_portal);
    			int contractor_id_MP_CONT_int=Integer.parseInt(contractor_id_MP_CONT_portal);
    			int project_id_MP_CONT_int=Integer.parseInt(project_id_MP_CONT_portal);
    			int site_id_MP_CONT_int=Integer.parseInt(site_id_MP_CONT_portal);
    			int work_loc_id_MP_CONT_int=Integer.parseInt(work_loc_id_MP_CONT_portal);   			
   			   
		      
				 
    			 System.out.println("consta value"+constant_String_portal);
     				
     				 			if(constant_String_portal.equals("I")){
//				 						inserting in database     				 			
     				 				db.add_MP_Contract_Record(new Manpower_Model(lab_contract_work_master_id_MP_CONT,contract_project_id_MP_CONT_int,contractor_id_MP_CONT_int,contractor_name_MP_CONT_portal,project_id_MP_CONT_int,site_id_MP_CONT_int,work_loc_id_MP_CONT_int,work_loc_name_MP_CONT_portal,total_qty_MP_CONT_portal,units_MP_CONT_portal,start_date_MP_CONT_portal_formated,scope_of_work_MP_CONT_portal,qty_completed_MP_CONT_portal,rate_MP_CONT_portal,paid_amt_MP_CONT_portal,balc_amt_MP_CONT_portal,"Y",mCurrent_date,"Y"));
     				 				 System.out.println("inserting in table");
     				 				 }else if (constant_String_portal.equals("DI"))
     				 				 {
									              if((db.CountRecords_Matched_SiteId_LabContWorkMasterId_WorkLocId_ContractorId_MP_Contract_Table(site_id_MP_CONT_int, lab_contract_work_master_id_MP_CONT,work_loc_id_MP_CONT_int,contract_project_id_MP_CONT_int))==0){
									            	  db.add_MP_Contract_Record(new Manpower_Model(lab_contract_work_master_id_MP_CONT,contract_project_id_MP_CONT_int,contractor_id_MP_CONT_int,contractor_name_MP_CONT_portal,project_id_MP_CONT_int,site_id_MP_CONT_int,work_loc_id_MP_CONT_int,work_loc_name_MP_CONT_portal,total_qty_MP_CONT_portal,units_MP_CONT_portal,start_date_MP_CONT_portal_formated,scope_of_work_MP_CONT_portal,qty_completed_MP_CONT_portal,rate_MP_CONT_portal,paid_amt_MP_CONT_portal,balc_amt_MP_CONT_portal,"Y",mCurrent_date,"Y"));
									              }else{
								
									            	  Manpower_Model manpower_model= db.find_SiteId_LabContWorkMasterId_WorkLocId_ContractorId_MP_Contract_Table(site_id_MP_CONT_int, lab_contract_work_master_id_MP_CONT,work_loc_id_MP_CONT_int,contract_project_id_MP_CONT_int,"Y");
									            	  if(manpower_model!=null)
									            	  {    
									            		
									  	            	manpower_model.setLab_Contract_Work_MasterId_MP_CONT(lab_contract_work_master_id_MP_CONT);
									  	            	manpower_model.setContract_Project_Id_MP_CONT(contract_project_id_MP_CONT_int);
									  	            	manpower_model.setContractor_Id_MP_CONT(contractor_id_MP_CONT_int);
									  	            	manpower_model.setContractor_Name_MP_CONT(contractor_name_MP_CONT_portal);
									  	            	manpower_model.setProj_Id_MP_CONT(project_id_MP_CONT_int);	            	
									  	           		manpower_model.setSite_Id_MP_CONT(site_id_MP_CONT_int);
									  	           		manpower_model.setWork_Loc_Id_MP_CONT(work_loc_id_MP_CONT_int);
									  	           		manpower_model.setWork_Loc_Name_MP_CONT(work_loc_name_MP_CONT_portal);
									  	           		manpower_model.setTotal_Qty_MP_CONT(total_qty_MP_CONT_portal);
									  	           		manpower_model.setQty_Units_MP_CONT(units_MP_CONT_portal);
									  	           		manpower_model.setStartDate_MP_CONT(start_date_MP_CONT_portal_formated);
									  	           		manpower_model.setScope_Of_Work_MP_CONT(scope_of_work_MP_CONT_portal);	           		
									  	           		manpower_model.setQty_Completed_MP_CONT(qty_completed_MP_CONT_portal);
									  	           		manpower_model.setLab_Cont_Work_Rate_MP_CONT(rate_MP_CONT_portal);
									  	           		manpower_model.setPaidAmt_MP_CONT(paid_amt_MP_CONT_portal);	  
									  	           		manpower_model.setBalance_Amt_MP_CONT(balc_amt_MP_CONT_portal);	  
									  	           		manpower_model.setSyn_Flag_MP_CONT("Y");
									  	           		manpower_model.setCreated_Date_MP_CONT(mCurrent_date);           			           		
									  	           		manpower_model.setDisplay_Flag_MP_CONT("Y");	 
         	                    		 
         	                    		 
									            		  db.Update_MP_Contract_Row(manpower_model);                	                    		
         		                		  
									            	  	}
									            	  db.Update_MP_Contract_Row(manpower_model);
									              	}
									              
     				 				 }
     				
        		
        			 
//				 for loop ending
     		}
     		 		runOnUiThread(new Runnable(){

     		 			@Override
     		 			public void run(){
    	           
    	            // display toast here 
    	        	  Toast.makeText(getApplicationContext(), "Contract data collected", Toast.LENGTH_SHORT).show();
    	        	
     		 			}
     		 		});
     		
//  jArray.length() if loop ending      			
        }
       else {
    	   runOnUiThread(new Runnable(){

    	          @Override
    	          public void run(){
    	           
    	            // display toast here 
    	        	  Toast.makeText(getApplicationContext(), "No data available for Contract", Toast.LENGTH_SHORT).show();
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
		if(pDialog_Get_MP_CONT.isShowing());
		pDialog_Get_MP_CONT.dismiss();
		
			}
}

private class UpdateAppVersion extends AsyncTask<Void, Void, Void> {
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
	 pDialog_AppVersion=new ProgressDialog(ManpowerTabs.this);
	 
	 pDialog_AppVersion.setMessage("Please Wait.");
	 pDialog_AppVersion.setCancelable(false);
	 pDialog_AppVersion.show();
	}
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		 // Creating service handler class instance
		db=new DatabaseHandler(ManpowerTabs.this);    
		
		
		String org_id_string=String.valueOf(org_id);	
		String party_id_string=String.valueOf(party_id);			
         
         StringBuffer	appVersion_sb=new StringBuffer();
		
         appVersion_sb.append(Utils.URL+"UpdateAppVersion"+"?"+"PartyId="+party_id_string+"&OrgId="+org_id_string+"&version="+versionName);
       	        
         url_UpdateAppVersion=appVersion_sb.toString();
        System.out.print("send to server"+url_UpdateAppVersion);
		ServerConnection hh = new ServerConnection();
		String jsonStr = hh.getmethods(url_UpdateAppVersion);
		System.out.println("json string>>>>>>>>"+jsonStr);
if(jsonStr!=null){
	try {
            // Extract JSON array from the response
            JSONArray jArray = new JSONArray(jsonStr);
            System.out.println(jArray.length());
            if(jArray.length()!=0)
            {
            		
            				String response_Value=jArray.getString(0);  		                	        		
            				
            				int	mResponse_result=Integer.parseInt(response_Value);
            					if (mResponse_result==1) {
            						runOnUiThread(new Runnable(){
                           	          @Override
                           	          public void run(){
                           	           
                           	            // display toast here 
//                           	        	  Toast.makeText(getApplicationContext(), "version inserted"+versionName, Toast.LENGTH_SHORT).show();
                           	          }
                           	       });  
          		         		 
								} else {
									runOnUiThread(new Runnable(){
			                 	          @Override
			                 	          public void run(){
			                 	           
			                 	            // display toast here 
//			                 	        	  Toast.makeText(getApplicationContext(), "Error in version"+versionName, Toast.LENGTH_SHORT).show();
			                 	          }
			                 	       });  
					         		 

								}			 
            	
            		
            			
            }
           else {
        	   runOnUiThread(new Runnable(){

        	          @Override
        	          public void run(){
        	           
        	            // display toast here 
//        	        	  Toast.makeText(getApplicationContext(), "No data available ", Toast.LENGTH_SHORT).show();
        	        	 
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
		if(pDialog_AppVersion.isShowing());
		pDialog_AppVersion.dismiss();
		
			}
}

private class Send_SynMaster extends AsyncTask<Void, Void, Void> {
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
	 pDialog_SynMaster=new ProgressDialog(ManpowerTabs.this);
	 
	 pDialog_SynMaster.setMessage("Please Wait.");
	 pDialog_SynMaster.setCancelable(false);
	 pDialog_SynMaster.show();
	}
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		 // Creating service handler class instance
		db=new DatabaseHandler(ManpowerTabs.this);    
		
		
		String org_id_string=String.valueOf(org_id);	
		String party_id_string=String.valueOf(party_id);
		String selected_SiteID_string=String.valueOf(selected_SiteID);
		String module_Id_string=String.valueOf(module_Id);
         
         StringBuffer	synMaster_sb=new StringBuffer();
		
         synMaster_sb.append(Utils.URL+"InsertSyncMaster"+"?"+"PartyId="+party_id_string+"&SiteId="+selected_SiteID_string+"&OrgId="+org_id_string+"&MobModuleId="+module_Id_string);
       	        
        url_send_SynMaster=synMaster_sb.toString();
        System.out.print("send to server"+url_send_SynMaster);
		ServerConnection hh = new ServerConnection();
		String jsonStr = hh.getmethods(url_send_SynMaster);
        System.out.println("json string>>>>>>>>"+jsonStr);
if(jsonStr!=null){
	try {
            // Extract JSON array from the response
            JSONArray jArray = new JSONArray(jsonStr);
            System.out.println(jArray.length());
            if(jArray.length()!=0)
            {
            		
            				String response_Value=jArray.getString(0);  		                	        		
            				
            				int	mResponse_result=Integer.parseInt(response_Value);
            					if (mResponse_result==1) {
            						runOnUiThread(new Runnable(){
                           	          @Override
                           	          public void run(){
                           	           
                           	            // display toast here 
                           	        	  Toast.makeText(getApplicationContext(), "Synchornization Complete", Toast.LENGTH_SHORT).show();
                           	          }
                           	       });  
          		         		 
								} else {
									runOnUiThread(new Runnable(){
			                 	          @Override
			                 	          public void run(){
			                 	           
			                 	            // display toast here 
			                 	        	  Toast.makeText(getApplicationContext(), "Error in Synchornization", Toast.LENGTH_SHORT).show();
			                 	          }
			                 	       });  
					         		 

								}
     				 
            	
            		
            			
            }
           else {
        	   runOnUiThread(new Runnable(){

        	          @Override
        	          public void run(){
        	           
        	            // display toast here 
//        	        	  Toast.makeText(getApplicationContext(), "No data available ", Toast.LENGTH_SHORT).show();
        	        	 
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
		if(pDialog_SynMaster.isShowing());
		pDialog_SynMaster.dismiss();
		finish();
		startActivity(getIntent());
			}

}
}
