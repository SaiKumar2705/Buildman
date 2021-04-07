package com.buildman.buildman.materialtabs;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.Status_Status_Expandable_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.Status_Child_Model;
import com.buildman.buildman.model.Status_Model;

public class TasksList extends Activity {	
	
	
	 DatabaseHandler db;
	 private Status_Model status_model;
	 private ExpandableListView mExpandableList;
	 private int selected_SiteID,party_id_fmLoginDB,org_id_fmLoginDB;
	 ArrayList<Status_Model> arrayParents = new ArrayList<Status_Model>();
	 ArrayList<Status_Child_Model> arrayChildren = new ArrayList<Status_Child_Model>();
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.taskslist);
	        db = new DatabaseHandler(this); 
//		      creating methods to write code
		        getIds();
		        getIds_FmLoginTable();
		        getList_fromDB();		    
}
	
	

	@Override
	 public void onBackPressed() {
	 				// do something on back.
//	 			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
	 		 Intent i=new Intent(TasksList.this,Used_Material_Description.class);		
	 		startActivity(i);
	 		finish();
	 		return;
	 			}	
	 private void getList_fromDB() {
			// TODO Auto-generated method stub
		 db = new DatabaseHandler(this);
			// Reading all contacts
	      Log.d("Reading: ", "Reading all contacts.."); 
//	      get selected site_id from usersite class    

			SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);		 
			 selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);
			  
	      List<Status_Model> main_Tasks_List = db.find_SiteID_DisplayFlag_Distinct_MainTaskID_ProjectStatusList(selected_SiteID,"Y");       
	      System.out.println("list size with siteid"+main_Tasks_List.size());
	      LinearLayout relative = (LinearLayout) findViewById(R.id.listview_linearlayout_taskslist);
	      if(main_Tasks_List.size()==0){
	    	
	    	  relative.setBackgroundResource(0); 
	    		 Toast.makeText(getApplicationContext(), 
      					"No Tasks Available", Toast.LENGTH_SHORT).show();

	      }else {
	    	  relative.setBackgroundResource(R.drawable.box_bottom_border);
	      }      
	     
	      
	      //here we set the parents and the children
	         for (int i = 0; i < main_Tasks_List.size(); i++){
	        	 Status_Model model=main_Tasks_List.get(i);
	        	  int site_id=model.getSite_ID();	        	 
		          String mEST_Start_Date = model.getPRJ_WorK_EST_Start_Date();		                   
		          String mEST_End_Date = model.getPRJ_Work_EST_End_Date();
		          String mSum_Total_Qty = model.getPRJ_EST_Work_TOT_QTY();
		          String mStart_Date=model.getStart_Date();
		          String mLink_ToBill=model.getLink_ToBill();		         
		          String mSum_Entered_Qty=model.getSum_Entered_Qty();
		          String mUnits = model.getPRJ_EST_Work_TOT_QTY_Units();
		          int main_Task_id=model.getMain_Task_ID();
		          String mMainTask_Name = model.getMain_Task_Name();
		          
		          String mDate;
		         if(mStart_Date.equals("00-00-0000")){
		        	 mDate=mEST_Start_Date;
		         }else {
		        	 mDate=mStart_Date;
				}
		    	 arrayChildren = new ArrayList<Status_Child_Model>();
		    	 
		    	 ArrayList<Status_Model> sub_tasks_list = db.find_SiteID_DisplayFlag_MainTaskID_ProjectStatusList(site_id,"Y",main_Task_id);    
	             for (int j = 0; j < sub_tasks_list.size(); j++) {
	            	 Status_Model task_model=sub_tasks_list.get(j);
	            	 
	            	 int siteID=task_model.getSite_ID();
	            	 int work_master_id=task_model.getPRJ_EST_WorkMaster_ID();
	            	 String subTask_names=task_model.getPRJ_EST_WorkLocation_Name();
	            	 String est_start_date=task_model.getPRJ_WorK_EST_Start_Date();
	            	 String est_end_date=task_model.getPRJ_Work_EST_End_Date();
	            	 String work_total_qty=task_model.getPRJ_EST_Work_TOT_QTY();
	            	 String units=task_model.getPRJ_EST_Work_TOT_QTY_Units();
	            	 String work_completed_qty=task_model.getPRJ_EST_Work_COM_QTY();
	            	 float balance_work_qty_float=((Float.parseFloat(work_total_qty))-(Float.parseFloat(work_completed_qty)));     	 
	            	 String balance_work_qty_result = String.format("%.2f", balance_work_qty_float);
	            	 
	            	 Status_Child_Model child_model = new Status_Child_Model();
	            	 child_model.setSite_ID_Child(siteID);
	            	 child_model.setPRJ_EST_WorkMaster_ID_Child(work_master_id);
	            	 child_model.setPRJ_EST_WorkLocation_Name_Child(subTask_names);
	            	 child_model.setPRJ_Work_EST_Start_Date_Child(est_start_date);
	            	 child_model.setPRJ_Work_EST_End_Date_Child(est_end_date);
	            	 child_model.setPRJ_EST_Work_TOT_QTY_Child(work_total_qty);
	            	 child_model.setPRJ_EST_Work_COM_QTY_Child(work_completed_qty);	    
	            	 child_model.setPRJ_EST_Work_TOT_QTY_Units_Child(units);	    
	            	 child_model.setBalance_Work_QTY_Child(balance_work_qty_result);            	

	            	 arrayChildren.add(child_model);           	 
	            	 
	            	 
	             }
	             String mSum_Entered_Qty_Result;
	 /* here set sum_entered_qty value to current_qty_textview for particular MainTask of Selected_Site
	             where link_To_Bill="Y" ,display_flag="Y"       */  
	               
	                	 Status_Model status_model=db.find_SiteID_MainTask_ID_DisplayFlag_LinkToBill_ProjectStatus_Table
	   		        		  (selected_SiteID, main_Task_id, "Y", "YES");	   	                
	                	String mSum_Entered_Qty_Value=status_model.getSum_Entered_Qty();	
	                	
	                     if(mSum_Entered_Qty_Value!=null){
	                    	 mSum_Entered_Qty_Result=mSum_Entered_Qty_Value;
	                     }else{
	                    	 mSum_Entered_Qty_Result="0.00";
	                     }
	            
	            
	             Status_Model appadd = new Status_Model(mMainTask_Name,mDate,mSum_Entered_Qty_Result,mUnits,mEST_End_Date,mSum_Total_Qty,arrayChildren);
	             arrayParents.add(appadd);   
	         }    
	             //sets the adapter that provides data to the list.
	             mExpandableList.setAdapter(new Status_Status_Expandable_Adapter(TasksList.this,arrayParents));
	             
	             
	         mExpandableList.setOnChildClickListener(new OnChildClickListener() {
	    			
	    			@Override
	    			public boolean onChildClick(ExpandableListView parent, View v,
	    					int groupPosition, int childPosition, long id) {
	    				// TODO Auto-generated method stub
	    			 				
	    				
	    				 Status_Child_Model child_model =  arrayParents.get(groupPosition).getArrayChildren().get(childPosition);
	    				/* Toast.makeText(getApplicationContext(),arrayParents.get(groupPosition).getMain_Task_Name()
	                                     + " : "
	                                     + child_model.getPRJ_EST_WorkLocation_Name(), Toast.LENGTH_SHORT)
	                             .show();*/
	    				 
				    				 int mSubTaskID=child_model.getPRJ_EST_WorkMaster_ID_Child();
				    				 String mSubTaskName=child_model.getPRJ_EST_WorkLocation_Name();
				    				 String mEST_Start_Date=child_model.getPRJ_WorK_EST_Start_Date_Child();
				    				 String mEST_End_Date=child_model.getPRJ_Work_EST_End_Date_Child();
				    				 String mTotal_Qty=child_model.getPRJ_EST_Work_TOT_QTY_Child();
				    				 String mCompleted_Qty=child_model.getPRJ_EST_Work_COM_QTY_Child();
				    				 String mUnits=child_model.getPRJ_EST_Work_TOT_QTY_Units_Child();
				    				
				    				 Intent i=new Intent(TasksList.this,Used_Material_Description.class);	
				    				 i.putExtra("Send_mSubTaskID", mSubTaskID);
				    				 i.putExtra("Send_mSubTaskName", mSubTaskName);				    				
				    				 startActivity(i);
				    				 finish();
//				    				 Toast.makeText(getApplicationContext(), 
//		    	             					"clicked-"+mSubTaskName, Toast.LENGTH_SHORT).show();
	    	             		
	    	        	
	    	        	return false;
	    			}
	    		});
	        
	 }
	private void getIds_FmLoginTable() {
		// TODO Auto-generated method stub
	//  get party_id and org_id fm Login table which always have only 1 row
	     db=new DatabaseHandler(TasksList.this);    
			Login_Model login_model= db.get_FirstRow_Login(1);
			party_id_fmLoginDB=login_model.getParty_ID();
			org_id_fmLoginDB=login_model.getOrg_ID();
	}

	private void getIds() {
		// TODO Auto-generated method stub
		mExpandableList = (ExpandableListView)findViewById(R.id.expandablelist_taskslist);
	}
}
