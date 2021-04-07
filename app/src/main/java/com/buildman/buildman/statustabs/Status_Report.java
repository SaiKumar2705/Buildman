package com.buildman.buildman.statustabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.StatusReport_Expandable_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.StatusReport_Child_Model;
import com.buildman.buildman.model.StatusReport_Parent_Model;
import com.buildman.buildman.utils.Utils;
import com.buildman.buildman.web.ServerConnection;




public class Status_Report extends Activity {
	DatabaseHandler db;
	private ProgressDialog pDialog;
	private ImageView mBack_Img;
	private EditText mSerach_Edt;
	private int org_id,party_id,site_id;
	private static String url_send_Parms_GetProjectStatusReport;
    private ExpandableListView mExpandableList;
    private StatusReport_Expandable_Adapter adapter;
    ArrayList<StatusReport_Parent_Model> arrayParents = new ArrayList<StatusReport_Parent_Model>();
    ArrayList<StatusReport_Child_Model> arrayChildren = new ArrayList<StatusReport_Child_Model>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_report);
        db=new DatabaseHandler(Status_Report.this);
        getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>Buildman</font>"));	    
        getActionBar().setSubtitle(Html.fromHtml("<font color='#6f6f6f'>Status Report</font>"));
        getIds();
        focusEdtText();
        search_Functionality();	 
        isInternetOn();
        back_Img_Click();       
 
    }    
	@Override
	public void onBackPressed() {
			// do something on back.
//		Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
    	Intent i=new Intent(Status_Report.this,StatusTabs.class);
		startActivity(i);
		finish();
	return;
		}	
    private void getIds() {
		// TODO Auto-generated method stub
    	mSerach_Edt=(EditText)findViewById(R.id.search_edt_statusreport);
    	mExpandableList = (ExpandableListView)findViewById(R.id.expandablelist_statusreport);
    	mBack_Img=(ImageView)findViewById(R.id.back_img_statusreport);
	}
    private void back_Img_Click() {
		// TODO Auto-generated method stub
    	mBack_Img.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Status_Report.this,StatusTabs.class);
				startActivity(i);
				finish();
			}
		});
	}
    private void focusEdtText() {
		// TODO Auto-generated method stub
    	mSerach_Edt.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
				  if (hasFocus) {					  
						  
					  mSerach_Edt.setText("");
					   } 
					 
					   else {
						  
						 
					   }
			}
		});
    }
    private void search_Functionality() {
		// TODO Auto-generated method stub
    	//adding list to edittext for searchfunctionality 
        mSerach_Edt.addTextChangedListener(new TextWatcher()
        
        {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
            {
            
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            	
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            	if(mSerach_Edt.getText().length()==0){
            		
            	}else if (mSerach_Edt.getText().toString()!=null) {
            		String text = mSerach_Edt.getText().toString().toLowerCase(Locale.getDefault());
	            	System.out.println("search edittext box value-"+text);
	            	adapter.filter(text);	
				}
            	
            }
        });
	}
    private void getList_fromDB() {
		// TODO Auto-generated method stub
    	 
        
         List<StatusReport_Parent_Model> sites_list = db.getDistinct_StatusReport_List();       
         System.out.println("list size with siteid"+sites_list.size());
         LinearLayout relative = (LinearLayout) findViewById(R.id.expandableList_linearlayout_statusreport);
         if(sites_list.size()==0){
       	
       	  relative.setBackgroundResource(0); 
         }else {
       	  relative.setBackgroundResource(R.drawable.box_bottom_gray_border);
         }
         
         
         //here we set the parents and the children
         for (int i = 0; i < sites_list.size(); i++){
        	 StatusReport_Parent_Model model=sites_list.get(i);
	    	 String site_names = model.getTitle_SiteName();
	    	 int site_id = model.getTitle_SiteId();	    	 
	    	float  sum_totalBillable_Amt_float = 0 ;
	    	 arrayChildren = new ArrayList<StatusReport_Child_Model>();
	    	 
	    	 ArrayList<StatusReport_Parent_Model> tasks_list = db.find_SiteID_StatusReportList(site_id);     
             for (int j = 0; j < tasks_list.size(); j++) {
            	 StatusReport_Parent_Model task_model=tasks_list.get(j);
            	 System.out.println(tasks_list.size()+"task_name"+task_model.getTask_Name());
            	 String task_names=task_model.getTask_Name();
            	 String total_qty=task_model.getTotal_Qty();
            	 String completed_qty=task_model.getCompleted_Qty();
            	 String percentage_workcompleted_qty=task_model.getPercentage_WorkCompleted_Qty();
            	 String balance_qty=task_model.getBalance_Qty();
            	 String units=task_model.getUnits();    
            	 String billable_amt=task_model.getBillable_Amt();
            	 System.out.println("billable_amt fm db"+billable_amt);
            	 
            	 StatusReport_Child_Model child_model = new StatusReport_Child_Model();
            	 child_model.setTaskName_Child(task_names);
            	 child_model.setTotal_Qty_Child(total_qty);
            	 child_model.setCompleted_Qty_Child(completed_qty);
            	 child_model.setPercentage_WorkCompleted_Qty(percentage_workcompleted_qty);
            	 child_model.setBalance_Qty_Child(balance_qty);
            	 child_model.setUnits_Child(units);
            	 child_model.setBillable_Amt_Child(billable_amt);

            	 arrayChildren.add(child_model);            	 
            	 
            	 sum_totalBillable_Amt_float += (Float.parseFloat(billable_amt));
             }
           
             String whole_Sum_totalBillable_Amt_Result=String.format("%.2f", sum_totalBillable_Amt_float); 
             StatusReport_Parent_Model appadd = new StatusReport_Parent_Model(site_names,whole_Sum_totalBillable_Amt_Result,arrayChildren);
             arrayParents.add(appadd);   
             Collections.sort(arrayParents);

     		System.out.println("**** After sorting sum_billable_amt descending ***");
     		for(StatusReport_Parent_Model employee: arrayParents){
     			System.out.println(employee);
     		}
         }        
         
         adapter = new
        		 StatusReport_Expandable_Adapter(Status_Report.this,arrayParents);
        		
     		   
     			
         //sets the adapter that provides data to the list.
         mExpandableList.setAdapter(adapter);
         
         
        /* mExpandableList.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				 StatusReport_Child_Model detailInfo =  arrayParents.get(groupPosition).getArrayChildren().get(childPosition);
				 Toast.makeText(getApplicationContext(),arrayParents.get(groupPosition).getTitle_SiteName()
                                 + " : "
                                 + detailInfo.getTaskName_Child(), Toast.LENGTH_SHORT)
                         .show();
				return false;
			}
		});*/
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
                
                // if connected with internet then setup the app
              
            	new Get_StatusReport_Data().execute();    	
            	
                return true;
                 
            } else if ( 
              connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
              connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            	getList_fromDB();
           	 Toast.makeText(getApplicationContext(), "Please connect to internet for latest data", Toast.LENGTH_SHORT).show();
                return false;
            }
          return false;	
	}
    
    

	private class Get_StatusReport_Data extends AsyncTask<Void, Void, Void> {
		
   	 @Override
   		protected void onPreExecute(){
   			super.onPreExecute();
   		 pDialog=new ProgressDialog(Status_Report.this);
   		 
   		 pDialog.setMessage("Please Wait.");
   		 pDialog.setCancelable(false);
  	     pDialog.show();
   		}
   		protected Void doInBackground(Void... params) {
   			// TODO Auto-generated method stub
   			 // Creating service handler class instance
   			db=new DatabaseHandler(Status_Report.this);    
   			
   			Login_Model login_model=db.get_FirstRow_Login(1);
   	    	org_id=login_model.getOrg_ID();
   	    	party_id=login_model.getParty_ID();
   	    	site_id=0;
   			
   	    	
   			StringBuffer sb_StatusReport=new StringBuffer();
   			String org_Id_String=String.valueOf(org_id);
   			String party_Id_String=String.valueOf(party_id);
   			String site_id_String=String.valueOf(site_id);
   			
   			sb_StatusReport.append(Utils.URL+"GetProjectStatusReport"+"?"+"PartyId="+party_Id_String+"&SiteId="+site_id_String+"&OrgId="+org_Id_String);

   			url_send_Parms_GetProjectStatusReport=sb_StatusReport.toString();
   			System.out.println("send to server "+url_send_Parms_GetProjectStatusReport);
			ServerConnection hh = new ServerConnection();
			String jsonStr = hh.getmethods(url_send_Parms_GetProjectStatusReport);
               
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
                			JSONObject jArray1 = jArray.getJSONObject(i); 
	                		
            				String site_id=jArray1.getString("SiteId"); 
            				String site_name=jArray1.getString("SiteName"); 
            				String task_id=jArray1.getString("TaskId"); 
            				String task_name=jArray1.getString("TaskName"); 
            				String total_qty_String=jArray1.getString("TotalQty"); 
            				String completed_qty=jArray1.getString("CompletionQty");
            				String units=jArray1.getString("Units");
            				String billable_amt=jArray1.getString("BillableAmount");
//            				decimals for total_qty
            				float total_qty_float=Float.parseFloat(total_qty_String);
            				String total_qty = String.format("%.2f", total_qty_float);	
            				System.out.println("total_qty decimal:"+total_qty);
//            				calculating balance_qty=total_qty-completed_qty
            				float balance_qty_float=((Float.parseFloat(total_qty))-(Float.parseFloat(completed_qty)));
            				String balance_qty_result = String.format("%.2f", balance_qty_float);			
//                		calculating percentage of work_completed            				
            				 float total = Float.parseFloat(total_qty);
            				 float score = Float.parseFloat(completed_qty);
            				
            				 float  percentage = (score * 100/ total);
            			  String  percentage_completion=String.format("%.2f", percentage);
            				
            				
                				 int mSite_id_result=Integer.parseInt(site_id);
                				 int mtask_id_result=Integer.parseInt(task_id);
                				 			if((db.countRows_MatchedOf_SiteID_TaskID_StatusReportTable(mSite_id_result,mtask_id_result))==0){
//        				 						inserting in database
                				 				db.add_StatusReport_Record(new StatusReport_Parent_Model(mSite_id_result,site_name,mtask_id_result,task_name,total_qty,completed_qty,percentage_completion,balance_qty_result,units,billable_amt));
                				 				
                				 			}else{
                				 				StatusReport_Parent_Model model= db.find_SiteID_TaskID_StatusReportTable(mSite_id_result,mtask_id_result);
                				 							if(model!=null)
                				 							{    
                    	                    		 
                    	                    		 model.setTitle_SiteName(site_name) ; 
                    	                    		 model.setTask_Name(task_name);    
                    	                    		 model.setTotal_Qty(total_qty); 
                    	                    		 model.setCompleted_Qty(completed_qty);
                    	                    		 model.setPercentage_WorkCompleted_Qty(percentage_completion);
                    	                    		 model.setBalance_Qty(balance_qty_result);
                    	                    		 model.setUnits(units);
                    	                    		 model.setBillable_Amt(billable_amt);
                    	                    		 db.Update_StatusReport_Row(model);                	                    		
                    		                		  
                				 							}
                				 					db.Update_StatusReport_Row(model);
                       		
                				 			}
                				
                   		
                   			 
//         				 for loop ending
                		}
                		 		runOnUiThread(new Runnable(){

                		 			@Override
                		 			public void run(){
               	           
               	            // display toast here 
               	        	  Toast.makeText(getApplicationContext(), "Status Report data collected", Toast.LENGTH_SHORT).show();
               	        	 
                		 			}
                		 		});
                		
//                if lloop ending			
                }
               else {
            	   runOnUiThread(new Runnable(){

            	          @Override
            	          public void run(){
            	           
            	            // display toast here 
           	        	  Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
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
   			getList_fromDB();
   				}


    }
	 
}