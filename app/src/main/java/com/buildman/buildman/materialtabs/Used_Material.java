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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.activity.Home;
import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.Used_Material_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Login_Model;
import com.buildman.buildman.model.Received_Material_Model;
import com.buildman.buildman.model.UserSites_Model;

public class Used_Material extends Activity {
	private TextView mMatl_header_txv,mUnits_headertxv,mCur_Stk_header_txv,mRecd_Qty_header_txv;
	private ListView mListview;
	DatabaseHandler db;
	private int selected_SiteID,party_id_fmLoginDB,org_id_fmLoginDB;
	 Received_Material_Model received_model;
	 ArrayList<Received_Material_Model> buildman_list = new ArrayList<Received_Material_Model>();
	 ArrayList<Integer>material_id_list =new ArrayList<Integer>();
	 ArrayList<String>matl_name_list =new ArrayList<String>();
	 ArrayList<String>currentStock_list =new ArrayList<String>();
	 ArrayList<String>units_list =new ArrayList<String>();
	 ArrayList<String>usedStock_list =new ArrayList<String>();
	 ArrayList<String>rate_list =new ArrayList<String>();
	 ArrayList<String>display_flag_list =new ArrayList<String>();
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.used_material);
	        received_model=new Received_Material_Model();
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
	 		 Intent i=new Intent(Used_Material.this,Home.class);		
	 		startActivity(i);
	 		finish();
	 		return;
	 			}		

	private void getList_fromDB() {
		// TODO Auto-generated method stub
		db = new DatabaseHandler(this);
		// Reading all contacts
        Log.d("Reading: ", "Reading all contacts.."); 
        SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);		 
		 selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);
		
		   List<Received_Material_Model> list = db.find_SiteID_Material_Stk_TransList(selected_SiteID,"Y");       
		   LinearLayout relative = (LinearLayout) findViewById(R.id.listview_linearlayout_used_matl);
	        if(list.size()==0){
	      	
	      	  relative.setBackgroundResource(0); 
	        }else {
	      	  relative.setBackgroundResource(R.drawable.box_bottom_border);
	        }
         
        for (Received_Material_Model cn : list) {
        	
        	 int material_id = cn.getMaterial_ID_MST();
            String material = cn.getMaterial_Name_MST();
            System.out.println("materials"+material);
            String units = cn.getUnits_MST();
            String currentStock = cn.getCurrent_Stock_MST();
            String receivedStock = cn.getReceived_Stock_MST();
            String usedStock=cn.getUsed_MST();
            String rate = cn.getRate_MST();
            String display_flag=cn.getDisplay_Flag_MST();
//            Received_Material_Model appadd = new Received_Material_Model(material,units,currentStock,receivedStock);
            buildman_list.add(cn); 
            
            material_id_list.add(material_id);	 
            matl_name_list.add(material);	 
            currentStock_list.add(currentStock);
            units_list.add(units);

            usedStock_list.add(usedStock);
            rate_list.add(rate);
            display_flag_list.add(display_flag);
       
        }
		 Used_Material_Adapter adapter = new
				 Used_Material_Adapter(Used_Material.this, R.layout.used_material_row, buildman_list);
	   
		mListview.setAdapter(adapter);
		
		mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                                int position, long id) {
	        	
	        	UserSites_Model model=db.find_PartyID_SiteID_UserSitesTable(party_id_fmLoginDB, selected_SiteID);
	        	if(model!=null)
	        	{    
	             		String userType_inDB= model.getUserType();
//	             		Toast.makeText(getApplicationContext(), 
//             					"User-"+userType_inDB, Toast.LENGTH_SHORT).show();
	             		if(userType_inDB.equals("PRIMRY")){
	        	Received_Material_Model received_model = (Received_Material_Model) parent.getItemAtPosition(position);
	           
	        	int material_id=received_model.getMaterial_ID_MST();
	        	 String matl_name = received_model.getMaterial_Name_MST();	        
	           
	             String currentStock = received_model.getCurrent_Stock_MST();
	             String units = received_model.getUnits_MST();
	             String receivedStock = received_model.getReceived_Stock_MST();
	             String usedStock=received_model.getUsed_MST();
	             String rate = received_model.getRate_MST();
	             String display_flag=received_model.getDisplay_Flag_MST();
	        	
	        	
				            Intent i=new Intent(Used_Material.this,Used_Material_Description.class);
			
				           /* i.putIntegerArrayListExtra("matl_id_list_send", material_id_list);
				            i.putStringArrayListExtra("matl_name_list_send", matl_name_list);
				            i.putStringArrayListExtra("currentStock_list_send", currentStock_list);
				            i.putStringArrayListExtra("units_list_send", units_list);
				            i.putStringArrayListExtra("usedStock_list_send", usedStock_list);
				            i.putStringArrayListExtra("rate_list_send", rate_list);
				            i.putStringArrayListExtra("display_flag_list_send", display_flag_list);
							int item_id=(int) parent.getItemIdAtPosition(position);
							i.putExtra("matl_name_id", item_id);*/
//				            Toast.makeText(getApplicationContext(), "matlid-"+material_id+matl_name, Toast.LENGTH_SHORT).show();
							  //passing site_id to material tabs class, Received_Material class
				            SharedPreferences material_id_UsedMatl_Pref = getSharedPreferences("material_id_UsedMatl_MyPrefs", Context.MODE_PRIVATE);		
				            	SharedPreferences.Editor material_id_UsedMatl_edit = material_id_UsedMatl_Pref.edit();	
				            	material_id_UsedMatl_edit.putInt("material_id_UsedMatl_KEY_PREF",material_id);	
				            	material_id_UsedMatl_edit.commit();
//		                  passing site_name to material tabs
								SharedPreferences matl_name_Pref=getSharedPreferences("matl_name_UsedMatl_MyPrefs", Context.MODE_PRIVATE);
								SharedPreferences.Editor matl_name_edit=matl_name_Pref.edit();
								matl_name_edit.putString("matl_name_UsedMatl_KEY_PREF", matl_name);
								matl_name_edit.commit();
								
//								  passing site_name to material tabs
									SharedPreferences currentStock_Pref=getSharedPreferences("currentStock_UsedMatl_MyPrefs", Context.MODE_PRIVATE);
									SharedPreferences.Editor currentStock_edit=currentStock_Pref.edit();
									currentStock_edit.putString("currentStock_UsedMatl_KEY_PREF", currentStock);
									currentStock_edit.commit();
									
//									  passing site_name to material tabs
										SharedPreferences units_Pref=getSharedPreferences("units_UsedMatl_MyPrefs", Context.MODE_PRIVATE);
										SharedPreferences.Editor units_edit=units_Pref.edit();
										units_edit.putString("units_UsedMatl_KEY_PREF", units);
										units_edit.commit();
								
										
//										  passing receivedStock to material tabs
											/*SharedPreferences receivedStock_Pref=getSharedPreferences("receivedStock_UsedMatl_MyPrefs", Context.MODE_PRIVATE);
											SharedPreferences.Editor receivedStock_edit=receivedStock_Pref.edit();
											receivedStock_edit.putString("receivedStock_UsedMatl_KEY_PREF", receivedStock);
											receivedStock_edit.commit();*/
											
											
//											  passing receivedStock to material tabs
												SharedPreferences usedStock_Pref=getSharedPreferences("usedStock_UsedMatl_MyPrefs", Context.MODE_PRIVATE);
												SharedPreferences.Editor usedStock_edit=usedStock_Pref.edit();
												usedStock_edit.putString("usedStock_UsedMatl_KEY_PREF", usedStock);
												usedStock_edit.commit();
												
//												  passing receivedStock to material tabs
													SharedPreferences rate_Pref=getSharedPreferences("rate_UsedMatl_MyPrefs", Context.MODE_PRIVATE);
													SharedPreferences.Editor rate_edit=rate_Pref.edit();
													rate_edit.putString("rate_UsedMatl_KEY_PREF", rate);
													rate_edit.commit();
																				
													
//													  passing receivedStock to material tabs
														SharedPreferences display_flag_Pref=getSharedPreferences("display_flag_UsedMatl_MyPrefs", Context.MODE_PRIVATE);
														SharedPreferences.Editor display_flag_edit=display_flag_Pref.edit();
														display_flag_edit.putString("display_flag_UsedMatl_KEY_PREF", display_flag);
														display_flag_edit.commit();
																										
							
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
	 private void getIds_FmLoginTable() {
			// TODO Auto-generated method stub
		//  get party_id and org_id fm Login table which always have only 1 row
	     db=new DatabaseHandler(Used_Material.this);    
			Login_Model login_model= db.get_FirstRow_Login(1);
			party_id_fmLoginDB=login_model.getParty_ID();
			org_id_fmLoginDB=login_model.getOrg_ID();
		}
	private void getIds() {
		// TODO Auto-generated method stub

		mListview=(ListView)findViewById(R.id.listview_used_material);
		
	}

	


	
}
