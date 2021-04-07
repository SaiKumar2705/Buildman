package com.buildman.buildman.materialtabs;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.activity.Login;
import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.UserSites_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Manpower_Model;
import com.buildman.buildman.model.Received_Material_Model;
import com.buildman.buildman.model.Status_Model;
import com.buildman.buildman.model.UserSites_Model;
import com.buildman.buildman.utils.Utils;
import com.buildman.buildman.web.ServerConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetMy_Materials extends Activity  {
	private TextView mBuilder_txv,mBuilderTech_txv;	
	private Button mGetMy_Materials_Btn;
	private ListView mListview;
	DatabaseHandler db;
	ProgressDialog pDialog,pDialog_MyProjectTasks,pDialog_MP_Supply,pDialog_MP_Contract;	
	private String mDate_Time_Now;
	private int org_id_int,party_id_int_fmDB;
	private String org_id_string,party_id_string,multiple_Site_Ids_string,multiple_Project_Ids_string;
	private static String url;
	private static String url_send_GetMyProjectTasks;
	private static String GetIssuesAndImagesOverLoaded;
	private static String GetSendImagesOverLoaded;
	private static String url_GetManPowerDetails_Supply;
	private static String url_GetLabourContractWorkDetails_Contract;
	ArrayList<UserSites_Model> sites_list = new ArrayList<UserSites_Model>();
	JSONObject jsonObjectgetImages;
	long val=0,imdval;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.getmy_materials);
			db = new DatabaseHandler(this);
	        
	       android.app.ActionBar bar=getActionBar();	    
	        getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>Buildman</font>"));
//		      creating methods to write code
		        getIds();
		        
		        getMy_Materials_Btn_Click();
		        getUser_Sites_Fm_DB();
        	
		}
	@Override
	public void onBackPressed() {
				// do something on back.
//			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();		
			
		return;
			}
	private void getUser_Sites_Fm_DB() {
			// TODO Auto-generated method stub

			// Reading all contacts
	        Log.d("Reading: ", "Reading all contacts.."); 
	        List<UserSites_Model> list = db.getAll_UserSitesList();       
	         
	        for (UserSites_Model cn : list) {
	        	
	         
	            String site_names = cn.getSite_Name();
	            System.out.println("site names"+site_names);
	            int site_Ids = cn.getSite_ID();	          
	            int  org_id=cn.getOrg_ID();
	            UserSites_Model appadd = new UserSites_Model(site_names,site_Ids);
	            sites_list.add(appadd);
	           
	        }
			 UserSites_Adapter adapter = new
					 UserSites_Adapter(GetMy_Materials.this, R.layout.user_sites_row, sites_list);
		   
			mListview.setAdapter(adapter) ;
			
			mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		        @Override
		        public void onItemClick(AdapterView<?> parent, View view,  int position, long id) {
		        			     
		        }
		    }); 
		}
	private void getIds() {
			// TODO Auto-generated method stub
//			getting textview Ids fm Xml

		mListview=(ListView)findViewById(R.id.listview_getmy_materials);
			mGetMy_Materials_Btn=(Button)findViewById((R.id.getmy_materials_btn_getmy_materials));
		}

	private void getMy_Materials_Btn_Click() {
				// TODO Auto-generated method stub
			 mGetMy_Materials_Btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						 dateTime_Now();
						 getParameters_IDfmDB();
						 isInternetOn();  
					 						
					}

				});
			}
	private void dateTime_Now() {
				// TODO Auto-generated method stub
				mDate_Time_Now=Utils.sdf.format(new Date());
			}
	private void getParameters_IDfmDB() {
				// TODO Auto-generated method stub
				db=new DatabaseHandler(GetMy_Materials.this);
				
				  
				 org_id_int=getIntent().getIntExtra("org_id_getMySites_to_getMyMaterial", 0);
				
//			      get data fm DB which matches  org_id 
					ArrayList<UserSites_Model> get_list =db.getAll_Site_IDs_List_byOrgID_UserSitesTable(org_id_int);
					 System.out.println("list size  is"+get_list.size()); 

				        StringBuffer site_id_StringBuffer=new StringBuffer();
				        StringBuffer project_id_StringBuffer=new StringBuffer();
				        
				       
				        for(int i=0;i<get_list.size();i++){
				        	UserSites_Model model = get_list.get(i);
				        	
				        	party_id_int_fmDB= model.getParty_ID();				        	
				        	 int site_id=model.getSite_ID();	
				        	 int project_id=model.getProject_ID();
				        	 	if(i==get_list.size()-1){
				        	 		site_id_StringBuffer.append(String.valueOf(site_id));
				        	 		project_id_StringBuffer.append(String.valueOf(project_id));
				        	 	}else {
				        	 
				        		 site_id_StringBuffer.append(String.valueOf(site_id)+",");
				        		 project_id_StringBuffer.append(String.valueOf(project_id)+",");
				        	 	}

				        	      
				        	
				        }
				        System.out.println("mutiple site_ids"+site_id_StringBuffer.toString()); 
					
					
				    org_id_string=String.valueOf(org_id_int);	
					party_id_string=String.valueOf(party_id_int_fmDB);
					multiple_Site_Ids_string =site_id_StringBuffer.toString();
					multiple_Project_Ids_string=project_id_StringBuffer.toString();
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
		                
		                // if connected with internet then setup the app
		                 
		            	    new GetMyProjectTasks().execute(); 
		            	    new GetManPowerDetails_Supply().execute(); 
		            	    new GetLabourContractWorkDetails_Contract().execute();
							new GetMyMaterials().execute(); 
							new GetIssuesAndImagesOverLoaded().execute();
							new GetSendImagesOverloaded().execute();

		                return true;
		                 
		            } else if ( 
		              connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
		              connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
		               
		                Toast.makeText(this, "Please connect to internet for Setup App", Toast.LENGTH_SHORT).show();
		                return false;
		            }
		          return false;
		        }
	private class GetMyMaterials extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog=new ProgressDialog(GetMy_Materials.this);
			pDialog.setMessage("Please Wait.");
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					 // Creating service handler class instance
			db=new DatabaseHandler(GetMy_Materials.this);
					
			StringBuffer sb=new StringBuffer();
//			        sb.append(url1+"?"+"OrgId="+org_id_string+"&"+"SiteId="+multiple_Site_Ids_string);
			sb.append(Utils.URL+"GetMaterialDetails"+"?"+"OrgId="+org_id_string+"&"+"PartyId="+party_id_string+"&"+"SiteIds="+multiple_Site_Ids_string);
			       
			System.out.print(Utils.URL+"GetMaterialDetails"+"?"+"OrgId="+org_id_string+"&"+"PartyId="+party_id_string+"&"+"SiteIds="+multiple_Site_Ids_string);
			        
			url=sb.toString();


			ServerConnection hh = new ServerConnection();
			String jsonStr = hh.getmethods(url);
			System.out.println("json string>>>>>>>>"+jsonStr);
		  	if(jsonStr!=null&&!jsonStr.equals("")){
		    	try {
		                // Extract JSON array from the response
		                JSONArray jArray = new JSONArray(jsonStr);
		                System.out.println(jArray.length());
		                if(jArray.length()!=0)
		                {
		                		for(int i=0; i<jArray.length(); i++)
		                		{                				
		                		JSONArray jArray1 = jArray.getJSONArray(i); 
		                		
		                				String material_id=jArray1.getString(0);  		                	        		
		                				String material_name =jArray1.getString(1);	
		                				String material_quantity =jArray1.getString(2);	
		                				String units=jArray1.getString(3);	
		                				String site_id=jArray1.getString(4);		                				
		                				 
		                				 System.out.println("My material_id and name is:"+material_id+material_name);
		                				 System.out.println("My site id is:"+site_id);   	                		
		                		                  	              		
		                				   	  
		                           	
		                					int	mMaterial_Id_result=Integer.parseInt(material_id);
		                					int	mSite_Id_result=Integer.parseInt(site_id);
		        		         			
		                					 String mTodayDate=Utils.sdf.format(new Date());
		                					 
		                					 UserSites_Model usersites_model= db.find_Org_ID_SiteID_UserSitesTable(org_id_int, mSite_Id_result); 
		                		             int project_id_int=usersites_model.getProject_ID();
//		                		             int site_id_int=usersites_model.getSite_ID();
		                		             int party_id_int=usersites_model.getParty_ID();
//		    		     					inserting in database
		    db.add_Material_Record(new Received_Material_Model(mMaterial_Id_result,material_name,units,material_quantity));
		    db.add_Material_Stk_Trans_Record(new Received_Material_Model(mMaterial_Id_result,material_name,units,"0.00",material_quantity,"0.00","0.00",material_quantity,mTodayDate,project_id_int,mSite_Id_result,party_id_int,org_id_int,"Y","Y","00-00-0000","0","0","0",mTodayDate,"0","0","0",0));
		         					  		 
		                		}
		                		  runOnUiThread(new Runnable(){

		                  	          @Override
		                  	          public void run(){
		                  	           
		                  	            // display toast here 
		                  	        	  Toast.makeText(getApplicationContext(), "Materials Setup Successful", Toast.LENGTH_SHORT).show();
		                  	        	  Intent i_mat=new Intent(GetMy_Materials.this,Login.class);
		                  	        	  startActivity(i_mat);
		                  	        	finish();
		                  	          }
		                  	       });                
		                		         				
		                }
		               else {
		            	   runOnUiThread(new Runnable(){

		            	          @Override
		            	          public void run(){
		            	           
		            	            // display toast here 
		            	        	  Toast.makeText(getApplicationContext(), "No Data Available for Materials ", Toast.LENGTH_SHORT).show();
		            	        	  Intent i_mat=new Intent(GetMy_Materials.this,Login.class);
	                  	        	  startActivity(i_mat);
	                  	        	finish();
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
					
						}
	}
	private class GetMyProjectTasks extends AsyncTask<Void, Void, Void> {
				
				@Override
				protected void onPreExecute(){
					super.onPreExecute();
					pDialog_MyProjectTasks=new ProgressDialog(GetMy_Materials.this);
				 
					pDialog_MyProjectTasks.setMessage("Please Wait.");
					pDialog_MyProjectTasks.setCancelable(false);
//				 pDialog.show();
				}
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					 // Creating service handler class instance
					
						
						StringBuffer sb_GetMyProjectTasks=new StringBuffer();					

						sb_GetMyProjectTasks.append(Utils.URL+"GetMyProjectTasksOverloaded"+"?"+"&PartyId="+party_id_string+"&PrjId="+multiple_Project_Ids_string+"&SiteId="+multiple_Site_Ids_string+"&OrgId="+org_id_string);
				       
				        System.out.print(sb_GetMyProjectTasks+"?"+"&PartyId="+party_id_string+"&PrjId="+multiple_Project_Ids_string+"&SiteId="+multiple_Site_Ids_string+"&OrgId="+org_id_string);
				        
				        url_send_GetMyProjectTasks=sb_GetMyProjectTasks.toString();
					
									
					System.out.println("send to server "+url_send_GetMyProjectTasks);
//			      ServiceHandler sh = new ServiceHandler();
//			        String jsonStr=sh.makeServiceCall(url_send_GetMyProjectTasks, ServiceHandler.GET);
					ServerConnection hh = new ServerConnection();
					String jsonStr = hh.getmethods(url_send_GetMyProjectTasks);
			        
			       
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
			     			JSONObject jObject = jArray.getJSONObject(i); 
			        		
			    			String project_id_portal=jObject.getString("ProjectId");   	
			    	        String site_id_portal =jObject.getString("PrjSiteId");    		
			    			String prj_EST_WorkMaster_Id_portal=jObject.getString("PrjEstWorkMasterId");	
			    			String prj_EST_Work_Location_Name_portal=jObject.getString("PrjEstWorkLocationName");	
			    			String remarks_portal=jObject.getString("Remarks");	
			    			String prj_Work_EST_Start_Date_portal=jObject.getString("PrjWorkEstStartDate");	
			    			String prj_Work_EST_End_Date_portal=jObject.getString("PrjWorkEstEndDate");		
			    			
			    				/*String prj_Work_EST_Start_Date_timestamp =prj_Work_EST_Start_Date_portal.split("\\(")[1].split("\\+")[0];
			    		        Date createdOn_start = new Date(Long.parseLong(prj_Work_EST_Start_Date_timestamp));
			    		        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			    		        String prj_Work_EST_Start_Date_portal_formated = sdf.format(createdOn_start);
			    		        System.out.println("startdate of jsonnnnnnnnn"+prj_Work_EST_Start_Date_portal_formated);*/
			    				/*String prj_Work_EST_End_Date_timestamp =prj_Work_EST_End_Date_portal.split("\\(")[1].split("\\+")[0];
			    		        Date createdOn_end = new Date(Long.parseLong(prj_Work_EST_End_Date_timestamp));			    		     
			    		        String prj_Work_EST_End_Date_portal_formated = sdf.format(createdOn_end);
			    		        System.out.println("end date of jsonnnnnnnnn"+prj_Work_EST_End_Date_portal_formated);*/
//			    			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//			    	        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
			    	        Date date_Start_Date = null;
			    	        Date date_End_Date = null;
			    			try {
			    				date_Start_Date = Utils.sdf.parse(prj_Work_EST_Start_Date_portal);
			    				date_End_Date = Utils.sdf.parse(prj_Work_EST_End_Date_portal);
			    			} catch (ParseException e) {
			    				// TODO Auto-generated catch block
			    				e.printStackTrace();
			    			}
			    			String prj_Work_EST_Start_Date_portal_formated=Utils.sdf.format(date_Start_Date);
			    			String prj_Work_EST_End_Date_portal_formated=Utils.sdf.format(date_End_Date);
			    		        
			    			String percentage_Completion_=jObject.getString("PercentageCompletion");
			    			String percentage_Completion_portal;
			    			if(percentage_Completion_!=null){
			    				percentage_Completion_portal=percentage_Completion_;
			    			}else{
			    				percentage_Completion_portal="0.00";
			    			}
			    			String prj_EST_Work_COM_Qty_portal=jObject.getString("PrjEstWorkCompQty");
			    			String prj_EST_Work_TOT_Qty_portal=jObject.getString("PrjEstWorkTotQty");
			    			String prj_EST_Work_TOT_Qty_Units_portal=jObject.getString("MatrlUnitName");		
			    			String constant_String_portal=jObject.getString("Flag");	
			    			String workTaskType_Name=jObject.getString("WorkTaskTypeName");	
			    			String workTaskType_ID=jObject.getString("WorkTaskTypeId");	
			    			String mainTask_ID=jObject.getString("WorkMainTaskId");	
			    			String mainTask_Name=jObject.getString("WorkMainTaskName");	
			    			String link_toBill=jObject.getString("LinkToBill");	
//			    			calculations for sum_entered_Qty
			    			String mSum_Entered_Qty;
			    			if(link_toBill.equals("YES")){
			    				mSum_Entered_Qty=prj_EST_Work_COM_Qty_portal;
			    			}else{
			    				mSum_Entered_Qty="0.00";
			    			}
			    			int project_id_int=Integer.parseInt(project_id_portal);
			    			int site_id_int=Integer.parseInt(site_id_portal);
			    			int prj_EST_WorkMaster_id_int=Integer.parseInt(prj_EST_WorkMaster_Id_portal);
			    			int workTaskType_ID_int=Integer.parseInt(workTaskType_ID);
			    			int mainTask_ID_int=Integer.parseInt(mainTask_ID);
			    			
			    			 System.out.println("consta value"+constant_String_portal);
			     				
			     				 			if(constant_String_portal.equals("I")){
//							 						inserting in database
			     				 			db.add_Project_Status_Record(new Status_Model(project_id_int,site_id_int,prj_EST_WorkMaster_id_int,prj_EST_Work_Location_Name_portal,percentage_Completion_portal,prj_EST_Work_TOT_Qty_portal,prj_EST_Work_TOT_Qty_Units_portal,prj_EST_Work_COM_Qty_portal,prj_Work_EST_Start_Date_portal_formated,prj_Work_EST_End_Date_portal_formated,"00-00-0000","00-00-0000","Y",mDate_Time_Now,mDate_Time_Now,"Y",remarks_portal,workTaskType_ID_int,mainTask_ID_int,mainTask_Name,link_toBill,mSum_Entered_Qty));
			     				 				
			     				 			}
			     				 			else if (constant_String_portal.equals("DI")) {

			     				 				if((db.CountRecords_Matched_SiteId_WorkMasterId_ProjectStatus_Table(site_id_int, prj_EST_WorkMaster_id_int))==0) {
													db.add_Project_Status_Record(new Status_Model(project_id_int, site_id_int, prj_EST_WorkMaster_id_int, prj_EST_Work_Location_Name_portal, percentage_Completion_portal, prj_EST_Work_TOT_Qty_portal, prj_EST_Work_TOT_Qty_Units_portal, prj_EST_Work_COM_Qty_portal, prj_Work_EST_Start_Date_portal_formated, prj_Work_EST_End_Date_portal_formated, "00-00-0000", "00-00-0000", "Y", mDate_Time_Now, mDate_Time_Now, "Y", remarks_portal, workTaskType_ID_int, mainTask_ID_int, mainTask_Name, link_toBill, mSum_Entered_Qty));
												}
			     				 				else{
			     				 					Status_Model model= db.find_WorkMasterId_SiteID_DisplayFlag_ProjectStatus(prj_EST_WorkMaster_id_int,site_id_int,"Y");
			     				 					if(model!=null) {
			     				 						model.setProject_ID(project_id_int) ;
			     				 						model.setSite_ID(site_id_int) ;
			     				 						model.setPRJ_EST_WorkMaster_ID(prj_EST_WorkMaster_id_int);
			     				 						model.setPRJ_EST_WorkLocation_Name(prj_EST_Work_Location_Name_portal);
							         	                model.setPercentage_Completion(percentage_Completion_portal);
							         	                model.setPRJ_EST_Work_TOT_QTY(prj_EST_Work_TOT_Qty_portal);
							         	                model.setPRJ_EST_Work_TOT_QTY_Units(prj_EST_Work_TOT_Qty_Units_portal);
							         	                model.setPRJ_EST_Work_COM_QTY(prj_EST_Work_COM_Qty_portal);
							         	                model.setPRJ_Work_EST_Start_Date(prj_Work_EST_Start_Date_portal_formated);
							         	                model.setPRJ_Work_EST_End_Date(prj_Work_EST_End_Date_portal_formated);
							         	                model.setStart_Date("00-00-0000");
							         	                model.setEnd_Date("00-00-0000");
							         	                model.setSyn_Status("Y");
							         	                model.setCreated_Date(mDate_Time_Now);
							         	                model.setTransaction_Date(mDate_Time_Now);
							         	                model.setDisplay_Flag("Y");
							         	                model.setRemarks(remarks_portal);
							         	                model.setWork_TaskType_ID(workTaskType_ID_int);
							         	                model.setMain_Task_ID(mainTask_ID_int);
							         	                model.setMain_Task_Name(mainTask_Name);
							         	                model.setLink_ToBill(link_toBill);
							         	                model.setSum_Entered_Qty(mSum_Entered_Qty);
							         	                db.Update_Project_Status_Row(model);
			     				 					}

			     				 				}
			            		
			     				 			}  //elseif end
			     				
			        					        			 
  	 			     		} //for loop ending
			     		 		runOnUiThread(new Runnable(){

			     		 			@Override
			     		 			public void run(){
			    	           
			    	            // display toast here 
			    	        	  Toast.makeText(getApplicationContext(), "Project Tasks collected", Toast.LENGTH_SHORT).show();
			    	        	
			     		 			}
			     		 		});
			     		
			//  jArray.length() if loop ending      			
			        }
			       else {
			    	   runOnUiThread(new Runnable(){

			    	          @Override
			    	          public void run(){
			    	           
			    	            // display toast here 
			    	        	  Toast.makeText(getApplicationContext(), "No data available for Project Tasks", Toast.LENGTH_SHORT).show();
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
					if(pDialog_MyProjectTasks.isShowing());
					pDialog_MyProjectTasks.dismiss();
					
						}


			}
	private class GetManPowerDetails_Supply extends AsyncTask<Void, Void, Void> {
				
				@Override
				protected void onPreExecute(){
					super.onPreExecute();
					pDialog_MP_Supply=new ProgressDialog(GetMy_Materials.this);
				 
					pDialog_MP_Supply.setMessage("Please Wait.");
					pDialog_MP_Supply.setCancelable(false);
//				 pDialog.show();
				}
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					 // Creating service handler class instance
					db=new DatabaseHandler(GetMy_Materials.this);				  
					
						
						StringBuffer sb_Get_MP_Supply=new StringBuffer();					

						sb_Get_MP_Supply.append(Utils.URL+"GetManPowerDetails"+"?"+"&PartyId="+party_id_string+"&SiteId="+multiple_Site_Ids_string+"&ProjectId="+multiple_Project_Ids_string+"&OrgId="+org_id_string);
				       
				        System.out.print(sb_Get_MP_Supply+"?"+"&PartyId="+party_id_string+"&SiteId="+multiple_Site_Ids_string+"&PrjId="+multiple_Project_Ids_string+"&OrgId="+org_id_string);
				        
				        url_GetManPowerDetails_Supply=sb_Get_MP_Supply.toString();
					
									
					System.out.println("send to server "+url_GetManPowerDetails_Supply);
					ServerConnection hh = new ServerConnection();
					String jsonStr = hh.getmethods(url_GetManPowerDetails_Supply);
//			         ServiceHandler sh = new ServiceHandler();
//			        String jsonStr=sh.makeServiceCall(url_GetManPowerDetails_Supply, ServiceHandler.GET);
			        
			       
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
			    			
//			    			Total_billed_amt calculation
							 float total_Billed_Amt_float=((Float.parseFloat(total_till_date_qty_MP_SUP_portal))*(Float.parseFloat(rate_MP_SUP_portal)));			      
							String total_Billed_Amt_Result=String.format("%.2f", total_Billed_Amt_float);  
							
//			    			balc_amt calculation=total_billed_amt-total_paid_amt 
							 float balc_Amt_float=(total_Billed_Amt_float)-(Float.parseFloat(total_paid_amt_MP_SUP_portal));			      
							String balance_Amt_Result=String.format("%.2f", balc_Amt_float);  
							 
			    			 System.out.println("consta value"+constant_String_portal);
			     				
			     				 			if(constant_String_portal.equals("I")){
//							 						inserting in database     				 			
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
			     				
			        		
			        			 
//							 for loop ending
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
					if(pDialog_MP_Supply.isShowing());
					pDialog_MP_Supply.dismiss();
					
						}
			}
	private class GetLabourContractWorkDetails_Contract extends AsyncTask<Void, Void, Void> {
				
				@Override
				protected void onPreExecute(){
					super.onPreExecute();
					pDialog_MP_Contract=new ProgressDialog(GetMy_Materials.this);
				 
					pDialog_MP_Contract.setMessage("Please Wait.");
					pDialog_MP_Contract.setCancelable(false);
//				 pDialog.show();
				}
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					 // Creating service handler class instance
					db=new DatabaseHandler(GetMy_Materials.this);				  
					
						
						StringBuffer sb_Get_MP_Contract=new StringBuffer();					

						sb_Get_MP_Contract.append(Utils.URL+"GetLabourContractWorkDetails"+"?"+"&PartyId="+party_id_string+"&SiteId="+multiple_Site_Ids_string+"&ProjectId="+multiple_Project_Ids_string+"&OrgId="+org_id_string);
				       				        
				        url_GetLabourContractWorkDetails_Contract=sb_Get_MP_Contract.toString();				
									
					System.out.println("send to server "+url_GetLabourContractWorkDetails_Contract);
					ServerConnection hh = new ServerConnection();
					String jsonStr = hh.getmethods(url_GetLabourContractWorkDetails_Contract);
//			         ServiceHandler sh = new ServiceHandler();
//			        String jsonStr=sh.makeServiceCall(url_GetLabourContractWorkDetails_Contract, ServiceHandler.GET);
			        
			       
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
//			    			start_date calculation
			    			String start_date_MP_CONT_timestamp =start_date_MP_CONT_portal.split("\\(")[1].split("\\+")[0];
					        Date start_Date = new Date(Long.parseLong(start_date_MP_CONT_timestamp));
//					        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					        String start_date_MP_CONT_portal_formated = Utils.sdf.format(start_Date);
			    			
			    			String constant_String_portal=jArray1.getString(17);	
			    			
			    			int lab_contract_work_master_id_MP_CONT=Integer.parseInt(lab_contract_work_master_id_MP_CONT_portal);
			    			int contract_project_id_MP_CONT_int=Integer.parseInt(contract_project_id_MP_CONT_portal);
			    			int contractor_id_MP_CONT_int=Integer.parseInt(contractor_id_MP_CONT_portal);
			    			int project_id_MP_CONT_int=Integer.parseInt(project_id_MP_CONT_portal);
			    			int site_id_MP_CONT_int=Integer.parseInt(site_id_MP_CONT_portal);
			    			int work_loc_id_MP_CONT_int=Integer.parseInt(work_loc_id_MP_CONT_portal);   			
			   			   
					      
							 
			    			 System.out.println("consta value"+constant_String_portal);
			     				
			     				 			if(constant_String_portal.equals("I")){
//							 						inserting in database     				 			
			     				 				db.add_MP_Contract_Record(new Manpower_Model(lab_contract_work_master_id_MP_CONT,contract_project_id_MP_CONT_int,contractor_id_MP_CONT_int,contractor_name_MP_CONT_portal,project_id_MP_CONT_int,site_id_MP_CONT_int,work_loc_id_MP_CONT_int,work_loc_name_MP_CONT_portal,total_qty_MP_CONT_portal,units_MP_CONT_portal,start_date_MP_CONT_portal_formated,scope_of_work_MP_CONT_portal,qty_completed_MP_CONT_portal,rate_MP_CONT_portal,paid_amt_MP_CONT_portal,balc_amt_MP_CONT_portal,"Y",mDate_Time_Now,"Y"));
			     				 				 System.out.println("inserting in table");
			     				 				 }else if (constant_String_portal.equals("DI"))
			     				 				 {
												              if((db.CountRecords_Matched_SiteId_LabContWorkMasterId_WorkLocId_ContractorId_MP_Contract_Table(site_id_MP_CONT_int, lab_contract_work_master_id_MP_CONT,work_loc_id_MP_CONT_int,contract_project_id_MP_CONT_int))==0){
												            	  db.add_MP_Contract_Record(new Manpower_Model(lab_contract_work_master_id_MP_CONT,contract_project_id_MP_CONT_int,contractor_id_MP_CONT_int,contractor_name_MP_CONT_portal,project_id_MP_CONT_int,site_id_MP_CONT_int,work_loc_id_MP_CONT_int,work_loc_name_MP_CONT_portal,total_qty_MP_CONT_portal,units_MP_CONT_portal,start_date_MP_CONT_portal_formated,scope_of_work_MP_CONT_portal,qty_completed_MP_CONT_portal,rate_MP_CONT_portal,paid_amt_MP_CONT_portal,balc_amt_MP_CONT_portal,"Y",mDate_Time_Now,"Y"));
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
												  	           		manpower_model.setCreated_Date_MP_CONT(mDate_Time_Now);           			           		
												  	           		manpower_model.setDisplay_Flag_MP_CONT("Y");	 
			         	                    		 
			         	                    		 
												            		  db.Update_MP_Contract_Row(manpower_model);                	                    		
			         		                		  
												            	  	}
												            	  db.Update_MP_Contract_Row(manpower_model);
												              	}
												              
			     				 				 }
			     				
			        		
			        			 
//							 for loop ending
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
					if(pDialog_MP_Contract.isShowing());
					pDialog_MP_Contract.dismiss();
					
						}


			}
	private class GetIssuesAndImagesOverLoaded extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog_MyProjectTasks=new ProgressDialog(GetMy_Materials.this);

			pDialog_MyProjectTasks.setMessage("Please Wait.");
			pDialog_MyProjectTasks.setCancelable(false);
//				 pDialog.show();
		}
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetIssuesAndImagesOverLoaded=Utils.URL+"GetIssuesAndImagesOverLoaded"+"?"+"&OrgId="+org_id_string+"&PartyId="+party_id_string;
			System.out.println("send to server "+GetIssuesAndImagesOverLoaded);
			ServerConnection hh = new ServerConnection();
			String jsonStr = hh.getmethods(GetIssuesAndImagesOverLoaded);
			System.out.println("json string>>>>>>>>"+jsonStr);
			if(jsonStr!=null&&jsonStr!="ERROR"){
				try {
					JSONArray jArray = new JSONArray(jsonStr);
					System.out.println(jArray.length());
					if(jArray.length()!=0)
					{
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jsonObject = jArray.getJSONObject(i);
                            int issuesandimades = Integer.parseInt(jsonObject.getString("IssueId"));
                            String issuesandimadesval = db.find_Portal_Issues_Id(issuesandimades);
                            String imageUrl=jsonObject.getString("Image1").trim();
                            if (issuesandimadesval != null) {
                                Boolean b = false;
                                if(imageUrl!=null&&!imageUrl.equalsIgnoreCase("NA")){
									b = file_download(jsonObject.getString("Image1"), jsonObject.getString("ImageName"));
									if (b) {
										String GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Buildman/receive/" + jsonObject.getString("ImageName");
//								String imgpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GridViewDemo/"+jsonObject.getString("ImageName");
										db.add_Project_Issues_Images_Record(new Status_Model(issuesandimadesval, jsonObject.getString("ProjectId"), jsonObject.getString("SiteId"), jsonObject.getString("TaskId"), GridViewDemo_ImagePath, "Y", jsonObject.getString("SyncDate"), "Y"));
									}
								}
                            }
                            else {
                                Long vAnInt = db.add_Project_issues_Record(new Status_Model(Integer.parseInt(jsonObject.getString("ProjectId").trim()), Integer.parseInt(jsonObject.getString("SiteId").trim()), Integer.parseInt(jsonObject.getString("TaskId")), jsonObject.getString("TaskName"), jsonObject.getString("IssueTitle"), jsonObject.getString("IssueDescription"), "Y", jsonObject.getString("SyncDate"), issuesandimades, "Y"));
                                Boolean b = false;
								if(imageUrl!=null&&!imageUrl.equalsIgnoreCase("NA")){
									b = file_download(jsonObject.getString("Image1"), jsonObject.getString("ImageName"));
									if (b) {
										String GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Buildman/receive/" + jsonObject.getString("ImageName");
//								String imgpath = RetriveCapturedImagePath();
										db.add_Project_Issues_Images_Record(new Status_Model(String.valueOf(vAnInt), jsonObject.getString("ProjectId").trim(), jsonObject.getString("SiteId").trim(), jsonObject.getString("TaskId"), GridViewDemo_ImagePath, "Y", jsonObject.getString("SyncDate"), "Y"));
									}
								}
                            }
                        }
//						for(int i=0; i<jArray.length(); i++){
//							JSONObject jObject = jArray.getJSONObject(i);
//							val=db.add_Project_issues_Record(new Status_Model(Integer.parseInt(jObject.getString("ProjectId").trim()),Integer.parseInt(jObject.getString("SiteId").trim()),Integer.parseInt(jObject.getString("TaskId").trim()),jObject.getString("TaskName"),jObject.getString("IssueTitle"),jObject.getString("IssueDescription"),"Y",jObject.getString("SyncDate"),"Y"));
//							if(val>0)
//							{
//								if(!jObject.getString("Image1").equalsIgnoreCase("NA")){
//									Boolean b = file_download(jObject.getString("Image1"), jObject.getString("ImageName"));
//									if (b) {
//										String ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Buildman/receive/" + jObject.getString("ImageName");
//										imdval=db.add_Project_Issues_Images_Record(new Status_Model(jObject.getString("IssueId").trim(),jObject.getString("ProjectId"),jObject.getString("SiteId"),jObject.getString("TaskId"), ImagePath, "Y", jObject.getString("SyncDate"),"Y"));
//									}
//								}
//							}
//						}
					}
				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			return null;
		}
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(pDialog_MyProjectTasks.isShowing());
			pDialog_MyProjectTasks.dismiss();

		}
	}
	private class GetSendImagesOverloaded extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog_MyProjectTasks=new ProgressDialog(GetMy_Materials.this);
			pDialog_MyProjectTasks.setMessage("Please Wait.");
			pDialog_MyProjectTasks.setCancelable(false);
//				 pDialog.show();
		}
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetSendImagesOverLoaded=Utils.URL+"GetSendImagesOverLoaded"+"?"+"&OrgId="+org_id_string+"&PartyId="+party_id_string;
			System.out.println("send to server "+GetSendImagesOverLoaded);
			ServerConnection hh = new ServerConnection();
			String jsonStr = hh.getmethods(GetSendImagesOverLoaded);
			System.out.println("json string>>>>>>>>"+jsonStr);
			if(jsonStr!=null&&jsonStr!="ERROR"){
				try {
					// Extract JSON array from the response
					JSONArray jArray = new JSONArray(jsonStr);
					System.out.println(jArray.length());
					if(jArray.length()!=0)
					{
						for(int i=0; i<jArray.length(); i++){
							JSONObject jObject = jArray.getJSONObject(i);
							if(Integer.parseInt(jObject.getString("ResponseId").trim())>0){
								Boolean b = false;
								b = file_download(jObject.getString("Image1"), jObject.getString("ImageName"));
								if (b) {
									String GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Buildman/receive/" + jObject.getString("ImageName");
									db.add_Project_Images_Record(new Status_Model(Integer.parseInt(jObject.getString("ProjectId").trim()), Integer.parseInt(jObject.getString("SiteId").trim()),Integer.parseInt(jObject.getString("WorkLocId").trim()),"",GridViewDemo_ImagePath,"Y",jObject.getString("CreatedDate")));
								}
							}
						}
					}

				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			return null;

		}
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(pDialog_MyProjectTasks.isShowing());
			pDialog_MyProjectTasks.dismiss();
		}
	}

	public boolean file_download(String uRl, String name) {
		boolean b = false;
		File direct = new File(Environment.getExternalStorageDirectory() + "/Buildman/receive");
		if (!direct.exists()) {
			direct.mkdirs();
		}

		DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
		Uri downloadUri = Uri.parse(uRl);
		DownloadManager.Request request = new DownloadManager.Request(downloadUri);
		request.setAllowedNetworkTypes(
				DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
				.setAllowedOverRoaming(false).setTitle("Demo")
				.setDescription("Something useful. No, really.")
				.setDestinationInExternalPublicDir("/Buildman/receive", name);
		mgr.enqueue(request);
		return true;
	}
}
