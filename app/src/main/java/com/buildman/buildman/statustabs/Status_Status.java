package com.buildman.buildman.statustabs;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.buildman.buildman.activity.Home;
import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.Status_Status_Adapter;
import com.buildman.buildman.adapter.Status_Status_Expandable_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.Status_Child_Model;
import com.buildman.buildman.model.Status_Model;
import com.buildman.buildman.model.UserSites_Model;

public class Status_Status extends Activity {

	 DatabaseHandler db;
	 private Status_Model status_model;
//	 private ExpandableListView mExpandableList;
	 private ListView mExpandableList;
	 private int selected_SiteID,party_id_fmLoginDB,org_id_fmLoginDB;
	 ArrayList<Status_Model> arrayParents = new ArrayList<Status_Model>();
	 List<Status_Child_Model> arrayChildren = new ArrayList<Status_Child_Model>();
    Status_Model task_model;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.status_status);
		 db = new DatabaseHandler(this);
		        getIds();
		        getIds_FmLoginTable();
		        getList_fromDB();
	 }
	 @Override
	 public void onBackPressed() {
		 Intent i=new Intent(Status_Status.this,Home.class);
		 startActivity(i);
		 finish();
		 return;
	 }
	 private void getList_fromDB() {
		 // TODO Auto-generated method stub
		 db = new DatabaseHandler(this);
		 Log.d("Reading: ", "Reading all contacts..");
		 SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);
		 selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);
		 List<Status_Model> main_Tasks_List = db.find_SiteID_DisplayFlag_Distinct_MainTaskID_ProjectStatusList(selected_SiteID,"Y");
		 System.out.println("list size with siteid"+main_Tasks_List.size());
		 LinearLayout relative = (LinearLayout) findViewById(R.id.listview_linearlayout_status);
		 if(main_Tasks_List.size()==0)
	    	  relative.setBackgroundResource(0); 
	     else{
	    	  relative.setBackgroundResource(R.drawable.box_bottom_border);
			 System.out.println("list with siteid"+main_Tasks_List.toString());
	     }
		 arrayChildren = new ArrayList<Status_Child_Model>();
		 List<Status_Model> sub_tasks_list = db.find_SiteID_DisplayFlag_MainTaskID_ProjectStatusList(selected_SiteID,"Y");
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
		 mExpandableList.setAdapter(new Status_Status_Adapter(Status_Status.this,arrayChildren));
		 mExpandableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 @Override
			 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 UserSites_Model model=db.find_PartyID_SiteID_UserSitesTable(party_id_fmLoginDB, selected_SiteID);
				 if(model!=null)
				 {
					 String userType_inDB= model.getUserType();
					 if(userType_inDB.equals("PRIMRY"))
					 {
						 Status_Child_Model child_model =  arrayChildren.get(position);
						 int mSubTaskID=child_model.getPRJ_EST_WorkMaster_ID_Child();
						 String mSubTaskName=child_model.getPRJ_EST_WorkLocation_Name();
						 String mEST_Start_Date=child_model.getPRJ_WorK_EST_Start_Date_Child();
						 String mEST_End_Date=child_model.getPRJ_Work_EST_End_Date_Child();
						 String mTotal_Qty=child_model.getPRJ_EST_Work_TOT_QTY_Child();
						 String mCompleted_Qty=child_model.getPRJ_EST_Work_COM_QTY_Child();
						 String mUnits=child_model.getPRJ_EST_Work_TOT_QTY_Units_Child();
						 Intent i=new Intent(Status_Status.this,Status_Status_Description.class);
						 i.putExtra("Send_mSubTaskID", mSubTaskID);
						 i.putExtra("Send_mSubTaskName", mSubTaskName);
						 i.putExtra("Send_mEST_Start_Date", mEST_Start_Date);
						 i.putExtra("Send_mEST_End_Date", mEST_End_Date);
						 i.putExtra("Send_mTotal_Qty", mTotal_Qty);
						 i.putExtra("Send_mCompleted_Qty", mCompleted_Qty);
						 i.putExtra("Send_mUnits", mUnits);
						 startActivity(i);
					 }else
						 Toast.makeText(getApplicationContext(),"User type is restricted", Toast.LENGTH_SHORT).show();
				 }

			 }
		 });
//		 mExpandableList.setOnChildClickListener(new OnChildClickListener() {
//			 @Override
//			 public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//				 // TODO Auto-generated method stub
//				 UserSites_Model model=db.find_PartyID_SiteID_UserSitesTable(party_id_fmLoginDB, selected_SiteID);
//				 if(model!=null)
//				 {
//					 String userType_inDB= model.getUserType();
//					 if(userType_inDB.equals("PRIMRY"))
//					 {
//						 Status_Child_Model child_model =  arrayParents.get(groupPosition).getArrayChildren().get(childPosition);
//						 int mSubTaskID=child_model.getPRJ_EST_WorkMaster_ID_Child();
//						 String mSubTaskName=child_model.getPRJ_EST_WorkLocation_Name();
//						 String mEST_Start_Date=child_model.getPRJ_WorK_EST_Start_Date_Child();
//						 String mEST_End_Date=child_model.getPRJ_Work_EST_End_Date_Child();
//						 String mTotal_Qty=child_model.getPRJ_EST_Work_TOT_QTY_Child();
//						 String mCompleted_Qty=child_model.getPRJ_EST_Work_COM_QTY_Child();
//						 String mUnits=child_model.getPRJ_EST_Work_TOT_QTY_Units_Child();
//						 Intent i=new Intent(Status_Status.this,Status_Status_Description.class);
//						 i.putExtra("Send_mSubTaskID", mSubTaskID);
//						 i.putExtra("Send_mSubTaskName", mSubTaskName);
//						 i.putExtra("Send_mEST_Start_Date", mEST_Start_Date);
//						 i.putExtra("Send_mEST_End_Date", mEST_End_Date);
//						 i.putExtra("Send_mTotal_Qty", mTotal_Qty);
//						 i.putExtra("Send_mCompleted_Qty", mCompleted_Qty);
//						 i.putExtra("Send_mUnits", mUnits);
//						 startActivity(i);
//					 }else
//						 Toast.makeText(getApplicationContext(),"User type is restricted", Toast.LENGTH_SHORT).show();
//				 }
//				 return false;
//			 }
//		 });
	 }
	 private void getIds_FmLoginTable() {
		 // TODO Auto-generated method stub
		//  get party_id and org_id fm Login table which always have only 1 row
		db=new DatabaseHandler(Status_Status.this);
		Login_Model login_model= db.get_FirstRow_Login(1);
		party_id_fmLoginDB=login_model.getParty_ID();
		org_id_fmLoginDB=login_model.getOrg_ID();
	 }
	private void getIds() {
		// TODO Auto-generated method stub
		mExpandableList = (ListView)findViewById(R.id.expandablelist_status_status);
	}
}