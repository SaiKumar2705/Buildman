package com.buildman.buildman.manpowertabs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.buildman.buildman.activity.Home;
import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.Contract_Manpower_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.Manpower_Model;
import com.buildman.buildman.model.UserSites_Model;
import com.buildman.buildman.utils.Utils;

public class Contract_Manpower extends Activity {
	private ListView mListview;
	DatabaseHandler db;
	private int selected_SiteID,party_id_fmLoginDB,org_id_fmLoginDB;
	 ArrayList<Manpower_Model>contract_list = new ArrayList<Manpower_Model>();
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.contract_manpower);	
	        db=new DatabaseHandler(this);
	        getIds();
	        getIds_FmLoginTable();
	        if(db.getRow_Count_MP_CONTRACT()==0){
	        	create_DB();
	        }
	        getList_fromDB();

}
	 @Override
	 public void onBackPressed() {
	 				// do something on back.
//	 			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
	 		 Intent i=new Intent(Contract_Manpower.this,Home.class);		
	 		startActivity(i);
	 		finish();
	 		return;
	 			}		
	 private void getList_fromDB() {
			
			// TODO Auto-generated method stub
			db = new DatabaseHandler(this);		
	        
			SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);		 
		    selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);
			
	        List<Manpower_Model> list = db.find_SiteID_DisplayFlag_OrderBY_Work_LocID_MP_CONTRACT_List(selected_SiteID,"Y");      
	      System.out.println("Reading size of list "+ list.size()); 
	      LinearLayout relative = (LinearLayout) findViewById(R.id.listview_linearlayout_contract_manpower);
	        if(list.size()==0){
	      	
	      	  relative.setBackgroundResource(0); 
	        }else {
	      	  relative.setBackgroundResource(R.drawable.box_bottom_border);
	        }
	        
	        for (Manpower_Model cn : list) {
	        	 int mContractor_Id=cn.getContractor_Id_MP_CONT();
	             String mContractor_Name = cn.getContractor_Name_MP_CONT();
	         	 String mWork_loc_name = cn.getWork_Loc_Name_MP_CONT();
	         	 System.out.println("work name"+mWork_loc_name);
	         	 String mTotal_Qty = cn.getTotal_Qty_MP_CONT();
	         	 String mQty_Completed = cn.getQty_Completed_MP_CONT();
	         	 String mUnits=cn.getQty_Units_MP_CONT();
	         	 String mStart_Date=cn.getStartDate_MP_CONT();
	        	 String mScope_of_Work=cn.getScope_Of_Work_MP_CONT();
	        	 String mRate=cn.getLab_Cont_Work_Rate_MP_CONT();
	        	 String mPaid_Amt=cn.getPaidAmt_MP_CONT();
	  Manpower_Model appadd = new Manpower_Model(mContractor_Id,mContractor_Name,mWork_loc_name,mTotal_Qty,mQty_Completed,mUnits,
			                      mStart_Date,mScope_of_Work,mRate, mPaid_Amt);
		         contract_list.add(appadd);  
	         }
	        Contract_Manpower_Adapter adapter = new
	        		Contract_Manpower_Adapter(Contract_Manpower.this, R.layout.contract_manpower_row, contract_list);
	   	   
	   		mListview.setAdapter(adapter);
	   		
	   		mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	   	        @Override
	   	        public void onItemClick(AdapterView<?> parent, View view,
	   	                                int position, long id) {
	   	        	
	   	        	UserSites_Model model=db.find_PartyID_SiteID_UserSitesTable(party_id_fmLoginDB, selected_SiteID);
		        	if(model!=null)
		        	{    
		             		String userType_inDB= model.getUserType();
//		             		Toast.makeText(getApplicationContext(), 
//	             					"User-"+userType_inDB, Toast.LENGTH_SHORT).show();
		             		if(userType_inDB.equals("PRIMRY")){
				   	        	Manpower_Model manpower_model = (Manpower_Model) parent.getItemAtPosition(position);
				   	           
				   	            Intent i=new Intent(Contract_Manpower.this,Contract_Manpower_Description.class);	   	           	            
				   				  int item_id=(int) parent.getItemIdAtPosition(position);
				   				  i.putExtra("task_name_id", item_id);
				   				  i.putExtra("contractor_id_send", manpower_model.getContractor_Id_MP_CONT());
				   				  i.putExtra("contractor_name_send", manpower_model.getContractor_Name_MP_CONT());
				   				  i.putExtra("work_loc_name_send", manpower_model.getWork_Loc_Name_MP_CONT());
				   				  i.putExtra("start_date_send", manpower_model.getStartDate_MP_CONT());
				   				  i.putExtra("scopeOfWork_send", manpower_model.getScope_Of_Work_MP_CONT());
				   				  i.putExtra("rate_send", manpower_model.getLab_Cont_Work_Rate_MP_CONT());
				   				  i.putExtra("total_Qty_send", manpower_model.getTotal_Qty_MP_CONT());
				   				  i.putExtra("completed_Qty_send", manpower_model.getQty_Completed_MP_CONT());
				   				  i.putExtra("paid_amt_send", manpower_model.getPaidAmt_MP_CONT());
				   				  startActivity(i);
				   				  finish();
	   	            
		             		}else{
		             			Toast.makeText(getApplicationContext(), 
		             					"User type is restricted", Toast.LENGTH_SHORT).show();
		             		}
		        	}
	   	            
	   	        }
	   	    }); 
	        
		}
	 private void create_DB() {
			// TODO Auto-generated method stub
		 
			
			 db = new DatabaseHandler(this); 
		

			 String mCurrent_date = Utils.sdf.format(new Date());

//			db.add_MP_Contract_Record(new Manpower_Model(1,"Mahesh",1,1,1,"Basement Floor","5","M2","23-11-2015 12:03:11","Complete in a month","2","200","100","300","Y",mCurrent_date,"Y")) ;
//			db.add_MP_Contract_Record(new Manpower_Model(1,"Mahesh",1,1,2,"Retaining Wall","5","M2","23-11-2015 12:03:11","Complete in a month","2","200","100","300","Y",mCurrent_date,"Y")) ;
			
		}
	 private void getIds_FmLoginTable() {
			// TODO Auto-generated method stub
		//  get party_id and org_id fm Login table which always have only 1 row
	     db=new DatabaseHandler(Contract_Manpower.this);    
			Login_Model login_model= db.get_FirstRow_Login(1);
			party_id_fmLoginDB=login_model.getParty_ID();
			org_id_fmLoginDB=login_model.getOrg_ID();
		}
	private void getIds() {
		// TODO Auto-generated method stub
		mListview=(ListView)findViewById(R.id.listview_contract_manpower);
	}
}