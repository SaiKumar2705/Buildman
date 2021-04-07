package com.buildman.buildman.manpowertabs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.activity.Home;
import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.Supply_Manpower_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.Manpower_Model;
import com.buildman.buildman.model.UserSites_Model;
import com.buildman.buildman.utils.Utils;

public class Supply_Manpower extends Activity {
	private TextView mContracter_headerTxv,mTotal_tilldate_headerTxv,mToday_headerTxv,mQty_headerTxv,mTtoday_headerTxv;
	DatabaseHandler db;
	private int selected_SiteID,party_id_fmLoginDB,org_id_fmLoginDB;
	private ListView mListview;
	private String mCurrent_Date_Time;	
	 ArrayList<Manpower_Model>supply_list = new ArrayList<Manpower_Model>();
	 ArrayList<Integer>contractor_Id_list =new ArrayList<Integer>();
	 ArrayList<String>contractor_name_list =new ArrayList<String>();
	 ArrayList<String>labour_type_name_list =new ArrayList<String>();
	 ArrayList<String>rate_list =new ArrayList<String>();
	 ArrayList<String>totalTillDate_Qty_list =new ArrayList<String>();
	 ArrayList<String>today_Qty_list =new ArrayList<String>();
	 ArrayList<String>total_billed_amt_list =new ArrayList<String>();
	 ArrayList<String>paid_Amt_list =new ArrayList<String>();
	 ArrayList<String>balance_Amt_list =new ArrayList<String>();
	 ArrayList<String>display_flag_list =new ArrayList<String>();	 
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.supply_manpower);
	        db = new DatabaseHandler(this); 
//		      creating methods to write code
		        getIds();
		        getIds_FmLoginTable();
		        if(db.getRow_Count_MP_SUPPLY()==0){
//		        	create_DB();
		        }
		        
		        getList_fromDB();
		       	

}
	 @Override
	 public void onBackPressed() {
	 				// do something on back.
//	 			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
	 		 Intent i=new Intent(Supply_Manpower.this,Home.class);		
	 		startActivity(i);
	 		finish();
	 		return;
	 			}		
		private void getList_fromDB() {
			
			// TODO Auto-generated method stub
			db = new DatabaseHandler(this);		
	        
			SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);		 
		    selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);
			
	        List<Manpower_Model> list = db.find_SiteID_DisplayFlag_OrderBY_ContractorID_MP_SUP_List(selected_SiteID,"Y");      
	      System.out.println("Reading size of list "+ list.size()+",siteId="+selected_SiteID); 
	      LinearLayout relative = (LinearLayout) findViewById(R.id.listview_linearlayout_supply_manpower);
	        if(list.size()==0){
	      	
	      	  relative.setBackgroundResource(0); 
	        }else {
	      	  relative.setBackgroundResource(R.drawable.box_bottom_border);
	        }
	        Date past_date,today_date; 
	         mCurrent_Date_Time= Utils.sdf.format(new Date());
	        for (Manpower_Model cn : list) {	
	        	
	            int contractor_id=cn.getContractor_Id_MP_SUP();
	            String contractor_name = cn.getContractor_Name_MP_SUP();	           
	            String labour_type_name = cn.getLabourType_Name_MP_SUP();
	            String rate = cn.getRate_MP_SUP();
	            String total_till_date_qty = cn.getTotalTillDate_Qty_MP_SUP();
	            String total_billed_amt=cn.getTotalBilledAmt_MP_SUP();
	            String paid_amt=cn.getTotalpaidAmt_MP_SUP();
	            String balance_amt=cn.getBalance_Amt_MP_SUP();
	            String create_date=cn.getCreated_Date_MP_SUP();	 
	            
	            String display_flag=cn.getDisplay_Flag_MP_SUP();
	            String today_entered_qty_sum = null;
	            try{
	            	 

	            	 past_date = Utils.sdf.parse(create_date);
	            	 today_date = Utils.sdf.parse(mCurrent_Date_Time);
	     
	            	System.out.println("date fm db"+Utils.sdf.format(past_date));
	            	System.out.println(Utils.sdf.format(today_date));
	            	
	     
	            		if(past_date.before(today_date)){
	            			System.out.println("past_date is before today_date");
	            			today_entered_qty_sum="0";
	            		}else {
	            			today_entered_qty_sum = cn.getTodayEntered_QtySum_MP_SUP();
	            			System.out.println("entered qty is"+cn.getEntered_Qty_MP_SUP());
						}	     
	            	
	     
	        	}catch(ParseException ex){
	        		ex.printStackTrace();
	        	} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}            
	            
	            Manpower_Model appadd = new Manpower_Model(contractor_name,labour_type_name, total_till_date_qty,today_entered_qty_sum);
	            supply_list.add(appadd); 
	            contractor_Id_list.add(contractor_id);
	            contractor_name_list.add(contractor_name);
	            labour_type_name_list.add(labour_type_name);
	            rate_list.add(rate);
	            totalTillDate_Qty_list.add(total_till_date_qty);
	            total_billed_amt_list.add(total_billed_amt);
	            today_Qty_list.add(today_entered_qty_sum);
	            paid_Amt_list.add(paid_amt);
	            balance_Amt_list.add(balance_amt);
	            display_flag_list.add(display_flag);
	          	       
	        }
	      Supply_Manpower_Adapter adapter = new
	    		  Supply_Manpower_Adapter(Supply_Manpower.this, R.layout.supply_manpower_row,supply_list);
		   
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
					           
					            Intent i=new Intent(Supply_Manpower.this,Supply_Manpower_Description.class);	
					            i.putIntegerArrayListExtra("contractor_id_list_send", contractor_Id_list);
					            i.putStringArrayListExtra("contractor_name_list_send", contractor_name_list);
					            i.putStringArrayListExtra("labour_type_name_list_send", labour_type_name_list);
					            i.putStringArrayListExtra("rate_list_send", rate_list);
					            i.putStringArrayListExtra("totalTillDate_Qty_list_send", totalTillDate_Qty_list);
					            i.putStringArrayListExtra("total_billed_amt_list_send", total_billed_amt_list);	
					            i.putStringArrayListExtra("today_Qty_list_send", today_Qty_list);	
					            i.putStringArrayListExtra("paid_Amt_list_send", paid_Amt_list);	
					            i.putStringArrayListExtra("balance_Amt_list_send", balance_Amt_list);	
					            i.putStringArrayListExtra("display_flag_list_send", display_flag_list);
       	            
								 int item_id=(int) parent.getItemIdAtPosition(position);
								 i.putExtra("item_position_id", item_id);
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
		

			 String mCurrent_date =Utils.sdf.format(new Date());
//
//			db.add_MP_Supply_Record(new Manpower_Model(1,2,"Mahesh",1,1,1,"Male","0.00","0.00","2","300","600","100","500","Y","19-10-1952","19-10-1952","Y")) ;
//			db.add_MP_Supply_Record(new Manpower_Model(1,2,"Mahesh",1,1,2,"Female","0.00","0.00","1","200","200","100","100","Y","19-10-1952","19-10-1952","Y")) ;
			
		}
	 private void getIds_FmLoginTable() {
			// TODO Auto-generated method stub
		//  get party_id and org_id fm Login table which always have only 1 row
	     db=new DatabaseHandler(Supply_Manpower.this);    
			Login_Model login_model= db.get_FirstRow_Login(1);
			party_id_fmLoginDB=login_model.getParty_ID();
			org_id_fmLoginDB=login_model.getOrg_ID();
		}
	private void getIds() {
		// TODO Auto-generated method stub
		mListview=(ListView)findViewById(R.id.listview_supply_manpower);
	}
}

