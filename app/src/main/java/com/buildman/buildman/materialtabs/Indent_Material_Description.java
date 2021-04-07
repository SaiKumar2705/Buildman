package com.buildman.buildman.materialtabs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.ViewStockHistoryDialog_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Received_Material_Model;
import com.buildman.buildman.utils.Utils;

public class Indent_Material_Description extends Activity {
	private TextView mMatl_Header,mPresent_matl_Txv,mRate_Title_txv,mRate_txv,mCurrentStock_Title_txv,mCurrentStock_txv,mCurrentStock_dimen_txv,
	mReqdStock_Title_txv,mReqdStock_dimen_txv,mReqdByDateTitle_txv,mReqdByDate_txv,mHistory_Txv_ViewStockHistory_Dialog,
	mReceived_Txv_ViewStockHistory_Dialog,mUsed_Txv_ViewStockHistory_Dialog,mIndent_Txv_ViewStockHistory_Dialog,
	mSum_Recvd_Txv_ViewStockHistory_Dialog,mSum_Used_Txv_ViewStockHistory_Dialog,mSum_Indent_Txv_ViewStockHistory_Dialog;
	private ListView mListview_ViewStockHistory_Dialog;	
	private EditText mReqdStock_Edt;
	private Button mView_StockHistoryBtn,mClose_btn_ViewStockHistory_Dialog,mconfirmBtn;
	private ImageView mBackImg,mReqdByDate_Calendar_img;
	private String material,currentStock,units,recevdStock,reqdStock,rate,display_flag,mIndent_Code;
	private float rate_float;
	private int selected_SiteID,site_id,project_id,party_id,org_id, mIndent_Number;
	
	private Received_Material_Model received_model;
	DatabaseHandler db;
	private int index_position,material_id;
	
	private ArrayList<Integer> matl_id_list=new ArrayList<Integer>();
	private ArrayList<String> matl_name_list=new ArrayList<String>();
	private ArrayList<String> current_stock_list=new ArrayList<String>();
	private ArrayList<String> units_list=new ArrayList<String>();
	private ArrayList<String> reqd_stock_list=new ArrayList<String>();
	private ArrayList<String> rate_list=new ArrayList<String>();
	private ArrayList<String> display_flag_list=new ArrayList<String>();
	private ArrayList<Received_Material_Model> indent_Greater_List_SynFLAG=new ArrayList<Received_Material_Model>();
	private ArrayList<Received_Material_Model> indent_Greater_List_DisplayFlag=new ArrayList<Received_Material_Model>();
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.indent_material_description);
	    
//		      creating methods to write code
	        
		        getIds();
		        todayDateCalculation();
		        selectReqdDate_Picker();
		        getData();
		        focusEdtText();
		        view_StockHistory();
		        backBtnClick();		      
		        confirmBtnClick();	  
		      
	}
	 @Override
		public void onBackPressed() {
				// do something on back.
//			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
		 Intent i=new Intent(Indent_Material_Description.this,MaterialTabs.class);
         i.putExtra("from_material", 2);
         startActivity(i);
         finish();
		return;
			}		

	private void selectReqdDate_Picker() {
		// TODO Auto-generated method stub
		mReqdByDate_Calendar_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Calendar c = Calendar.getInstance();
	           int mYear = c.get(Calendar.YEAR);
	           int mMonth = c.get(Calendar.MONTH);
	           int mDay = c.get(Calendar.DAY_OF_MONTH);
	 
	            // Launch Date Picker Dialog
	            DatePickerDialog dpd = new DatePickerDialog(Indent_Material_Description.this,
	                    new DatePickerDialog.OnDateSetListener() {
	 
	                        @Override
	                        public void onDateSet(DatePicker view, int year,
	                                int monthOfYear, int dayOfMonth) {
	                            // Display Selected date in textbox
	                        	mReqdByDate_txv.setText(dayOfMonth + "-"
	                                    + (monthOfYear + 1) + "-" + year);
	 
	                        }
	                    }, mYear, mMonth, mDay);
	            dpd.show();
			}
		});
	}

	private void todayDateCalculation() {
		// TODO Auto-generated method stub
//        date calculating
         Calendar c = Calendar.getInstance();
           int mYear = c.get(Calendar.YEAR);
           int mMonth = c.get(Calendar.MONTH);
           int mDay = c.get(Calendar.DAY_OF_MONTH);
           mReqdByDate_txv.setText(String.valueOf(mDay)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mYear));
	}

	private void focusEdtText() {
		// TODO Auto-generated method stub
		mReqdStock_Edt.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
				  if (hasFocus) {					  
						  
					  mReqdStock_Edt.setText("");
					   } 
					 
					   else {
						  
						 
					   }
			}
		});
	}

	private void getIds() {
		// TODO Auto-generated method stub
		mMatl_Header=(TextView)findViewById(R.id.material_title_indent_material_description);
		mPresent_matl_Txv=(TextView)findViewById(R.id.matl_name_header_indent_material_description);
		mView_StockHistoryBtn=(Button)findViewById(R.id.view_stockhistory_btn_indent_material_description);
		mRate_Title_txv=(TextView)findViewById(R.id.rate_title_txv_indent_material_description);
		mRate_txv=(TextView)findViewById(R.id.rate_txv_indent_material_description);
		mCurrentStock_Title_txv=(TextView)findViewById(R.id.currentstock_title_txv_indent_material_description);
		mCurrentStock_txv=(TextView)findViewById(R.id.currentstock_txv_indent_material_description);
		mCurrentStock_dimen_txv=(TextView)findViewById(R.id.currentstock_dimens_txv_indent_material_description);
		mReqdStock_Title_txv=(TextView)findViewById(R.id.requiredstock_title_txv_indent_material_description);
		mReqdStock_Edt=(EditText)findViewById(R.id.requiredstock_edt_indent_material_description);
		mReqdStock_dimen_txv=(TextView)findViewById(R.id.requiredstock_dimens_txv_indent_material_description);			
		mReqdByDateTitle_txv=(TextView)findViewById(R.id.required_bydate_title_txv_indent_material_description);
		mReqdByDate_txv=(TextView)findViewById(R.id.required_bydate_txv_indent_material_description);
		mReqdByDate_Calendar_img=(ImageView)findViewById(R.id.required_bydate_calender_img_indent_material_description);
		
		
		mBackImg=(ImageView)findViewById(R.id.back_img_indent_material_description);
		mconfirmBtn=(Button)findViewById(R.id.confirm_btn_indent_material_description);
		
	}

	private void getData() {
		// TODO Auto-generated method stub
		SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);		 
		 selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);
		 
		index_position=getIntent().getIntExtra("matl_name_id", 0);
		
		matl_id_list=getIntent().getIntegerArrayListExtra("matl_id_list_send"); 		
		  material_id=matl_id_list.get(index_position);
		  
		matl_name_list=getIntent().getStringArrayListExtra("matl_name_list_send"); 		
	    material=matl_name_list.get(index_position);
	    
	    current_stock_list=getIntent().getStringArrayListExtra("currentStock_list_send"); 		
	    currentStock=current_stock_list.get(index_position);
	    
	    units_list=getIntent().getStringArrayListExtra("units_list_send"); 		
	    units=units_list.get(index_position);
	    
	    reqd_stock_list=getIntent().getStringArrayListExtra("reqdStock_list_send"); 		
	    reqdStock=reqd_stock_list.get(index_position);
	    
	    rate_list=getIntent().getStringArrayListExtra("rate_list_send"); 		
	    rate=rate_list.get(index_position);	
	    
	    display_flag_list=getIntent().getStringArrayListExtra("display_flag_list_send"); 		
	    display_flag=display_flag_list.get(index_position);
		
	    mPresent_matl_Txv.setText(material);
	    if(currentStock==null){
		
		mCurrentStock_txv.setText("0.00");
	    }
	    else{
	    	
	    	
	    	mCurrentStock_txv.setText(currentStock);
	    }
		mCurrentStock_dimen_txv.setText(units);
		mReqdStock_dimen_txv.setText(units);
		
		mReqdStock_Edt.setText("0.00");
//		setting rate to rate text field
		mRate_txv.setText(rate);
		rate_float=Float.parseFloat(mRate_txv.getText().toString());
		

		
	}
	

	private void confirmBtnClick() {
		// TODO Auto-generated method stub
		mconfirmBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if(mReqdStock_Edt.getText().length()==0){
	         			
		           		mReqdStock_Edt.setError("Please fill the field");
	            }else{
			  	db = new DatabaseHandler(Indent_Material_Description.this);  
			  	 received_model= db.find_SiteID_MaterialID_DisplayFlag_Material_Stk_Trans_Table(selected_SiteID,material_id,display_flag); 
	             
	               if(received_model!=null) {  
	            	   
//	           		calculations          		  
         		   float reqd_stock=Float.parseFloat(mReqdStock_Edt.getText().toString());
         		   String  reqd_stock_result = String.format("%.2f", reqd_stock);
         			site_id=received_model.getSite_ID_MST();
   			  	 project_id=received_model.getProject_ID_MST();
   			     party_id=received_model.getParty_ID_MST();
   			  	 org_id=received_model.getOrg_ID_MST();
   			  	 String current_stock=received_model.getCurrent_Stock_MST();

   			  String mTodayDate= Utils.sdf.format(new Date());
                          if(reqd_stock_result.equals("0.00")){
                        	  
                          }else if (reqd_stock_result.equals("0")) {
							
                          }else {
                        	  
			                        	  indent_Code_Generation();
			                        	 if(indent_Greater_List_SynFLAG.size()>0){                      			
                      			
                        	  					
                        	  					Received_Material_Model model=indent_Greater_List_SynFLAG.get(0);			
                        	  					mIndent_Number =Integer.parseInt(model.getDC_Invoice_MST());
                        	  					System.out.println("previousindent_numberSynFLag--"+model.getDC_Invoice_MST()); 
                        	  					
                        	  					ArrayList<Received_Material_Model> list=db.find_SiteID_MaterialID_SynStatus_Material_Stk_TransList(selected_SiteID,material_id,"N");
                        	  					System.out.println("find_SiteID_MaterialID_SynFlag_ListSize--"+list.size()); 
                        	  					
                        	  					ArrayList<Received_Material_Model> indent_Sorted_List=new ArrayList<Received_Material_Model>();
                        	  					for (Received_Material_Model cn : list) {
                        	  						 String indent_Qty=cn.getIndent_MST();
                        	  						 
                        	  						  double  indent_Qty_double=Double.parseDouble(indent_Qty);                    
                        	  						if(indent_Qty_double>0.00){                        	  						
                        	  							indent_Sorted_List.add(cn);
                        	  						}
                        	  					 }	
                        	  					
                        	  					if(indent_Sorted_List.size()==0){
                        	  						 db.add_Material_Stk_Trans_Record(new Received_Material_Model(material_id,mPresent_matl_Txv.getText().toString(),units,"0.00","0.00","0.00",reqd_stock_result,current_stock,mTodayDate,project_id,site_id,party_id,org_id,"N","Y",mReqdByDate_txv.getText().toString(),String.valueOf(mIndent_Number),"0",mIndent_Code,mTodayDate,"0","0","0",0));
                        	  						 received_model.setDisplay_Flag_MST("N");
                        	  						db.Update_Material_Stk_TransRow(received_model);
                        	  						 
                        	  					}else {
                        	  							for (int i=0;i<indent_Sorted_List.size();i++) {
                        	  								Received_Material_Model recd_model=indent_Sorted_List.get(i);              	  								

//    	                            	  					get Indent_Qty of Previous_rowdata and Indent_Reqd_Qty_Editext(Summing) 
    	                            	  					float mReqd_Indent=Float.parseFloat(mReqdStock_Edt.getText().toString())+Float.parseFloat(recd_model.getIndent_MST());
    	    	                      	         		    String  mReqd_Indent_result = String.format("%.2f", mReqd_Indent);
    	    	                      	         		 System.out.println("summingg--"+mReqdStock_Edt.getText().toString()+","+recd_model.getIndent_MST()); 
                        	  								/*updating row*/
    	    	                      	         		recd_model.setIndent_MST(mReqd_Indent_result);
    	    	                      	         		recd_model.setTransaction_Date_MST(mTodayDate);
    	    	                      	         		recd_model.setMaterial_Req_By_Date_MST(mReqdByDate_txv.getText().toString());
    	    	                      	         		recd_model.setDC_Invoice_MST(String.valueOf(mIndent_Number));
    	    	                      	         		recd_model.setDoc_Type_MST(mIndent_Code);
                        	  								db.Update_Material_Stk_TransRow(recd_model);
		                        	  					    }
												}
                        	  				
	    	                        	  				
                        	  		
                        	  					
                        	  			 }else{
				                      			    if(indent_Greater_List_DisplayFlag.size()>0){
				                      			    		Collections.sort(indent_Greater_List_DisplayFlag);
				                      			    		System.out.println("indent_Greater_List_DisplayFlag--"+indent_Greater_List_DisplayFlag.size());//				                      			    	
				                      			    		Received_Material_Model model=indent_Greater_List_DisplayFlag.get(0);				                      			    		
				                      			    		mIndent_Number =(Integer.parseInt(model.getDC_Invoice_MST()))+1;
				                      			    		System.out.println("previousindent_number_DisplayFlag--"+model.getDC_Invoice_MST());
				                      			     }else{
				                      			    	mIndent_Number =1;			                      			    		
				                      			     }
				                      			    db.add_Material_Stk_Trans_Record(new Received_Material_Model(material_id,mPresent_matl_Txv.getText().toString(),units,"0.00","0.00","0.00",reqd_stock_result,current_stock,mTodayDate,project_id,site_id,party_id,org_id,"N","Y",mReqdByDate_txv.getText().toString(),String.valueOf(mIndent_Number),"0",mIndent_Code,mTodayDate,"0","0","0",0));
				                      			    received_model.setDisplay_Flag_MST("N");
				                      			    db.Update_Material_Stk_TransRow(received_model);
		                           	                
                        	  				}
			                        	 	
                        	                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        	                clearfields();
                        	 
                	                
                          }
             
	                  }  
//	               db.Update_Material_Stk_TransRow(received_model);	
	  

	               }
			}
			
			
		});		
	}
	private void indent_Code_Generation() {
		// TODO Auto-generated method stub
		/*Saving in DB as IndentCode in  DOC_TYPE COLUMN and Indent_Number in DC_INVOICE COLUMN*/
		ArrayList<Received_Material_Model> db_list_SynFlag=db.find_SiteID_SynStatus_Material_Stk_TransList(selected_SiteID,"N");
		ArrayList<Received_Material_Model> db_list_DisplayFlag=db.find_SiteID_Material_Stk_TransList(selected_SiteID,"Y");
		
			mIndent_Code=selected_SiteID+"IND";
		
		
		
		for (Received_Material_Model cn : db_list_SynFlag) {
			 String indent_Qty=cn.getIndent_MST();
			 
			  double  indent_Qty_double=Double.parseDouble(indent_Qty);                    
			if(indent_Qty_double>0.00){
				 System.out.println("indent_valueee,added to listSyn"+cn.getIndent_MST());
				indent_Greater_List_SynFLAG.add(cn);
			}
		 }	
		
	
		for (Received_Material_Model cn : db_list_DisplayFlag) {
			 String indent_Qty=cn.getIndent_MST();
			
			  double  indent_Qty_double=Double.parseDouble(indent_Qty);                    
			if(indent_Qty_double>0.00){
				  System.out.println("indent_valueee,added to listDisplay"+cn.getIndent_MST());
				indent_Greater_List_DisplayFlag.add(cn);
			}
		 }	
		
		
		
		
	}

	private void view_StockHistory() {
		// TODO Auto-generated method stub
		 
		mView_StockHistoryBtn.setOnClickListener(new OnClickListener() {
			

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 ArrayList<Received_Material_Model> stock_history_list = new ArrayList<Received_Material_Model>();
				// Create custom dialog object
				final Dialog dialog = new Dialog(v.getContext());
				// hide to default title for Dialog
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

				dialog.setContentView(R.layout.view_stock_history_dialog);
				db = new DatabaseHandler(Indent_Material_Description.this);  
		mHistory_Txv_ViewStockHistory_Dialog= (TextView) dialog.findViewById(R.id.history_viewstockhistory_dialog);
		mReceived_Txv_ViewStockHistory_Dialog = (TextView) dialog.findViewById(R.id.matl_received_viewstockhistory_dialog);
		mUsed_Txv_ViewStockHistory_Dialog = (TextView) dialog.findViewById(R.id.matl_used_viewstockhistory_dialog);
		mIndent_Txv_ViewStockHistory_Dialog = (TextView) dialog.findViewById(R.id.matl_indent_viewstockhistory_dialog);
		mSum_Recvd_Txv_ViewStockHistory_Dialog = (TextView) dialog.findViewById(R.id.sum_received_viewstockhistory_dialog);
		mSum_Used_Txv_ViewStockHistory_Dialog = (TextView) dialog.findViewById(R.id.sum_used_viewstockhistory_dialog);
		mSum_Indent_Txv_ViewStockHistory_Dialog = (TextView) dialog.findViewById(R.id.sum_indent_viewstockhistory_dialog);
		mListview_ViewStockHistory_Dialog = (ListView) dialog.findViewById(R.id.listview_viewstockhistory_dialog);
		mClose_btn_ViewStockHistory_Dialog = (Button) dialog.findViewById(R.id.close_btn_viewstockhistory_dialog);
		
		 List<Received_Material_Model> list = db.find_SiteID_MaterialID_Material_Stk_Trans(selected_SiteID, material_id);   
		 RelativeLayout stock_RelativeLayout = (RelativeLayout)dialog. findViewById(R.id.relative_layout_stock);
		 RelativeLayout sum_RelativeLayout = (RelativeLayout)dialog.findViewById(R.id.relative_layout_sum);
		 if(list.size()==0){			
			 stock_RelativeLayout.setVisibility(View.GONE);
			 sum_RelativeLayout.setVisibility(View.GONE);
			 Toast.makeText(getApplicationContext(), "No records exists,  please go to portal",Toast.LENGTH_SHORT).show();
		 }else{
			 	float received_total_sum_float = 0,used_total_sum_float = 0,indent_total_sum_float = 0;
			 		stock_RelativeLayout.setVisibility(View.VISIBLE);
			 		sum_RelativeLayout.setVisibility(View.VISIBLE);
			 		for (Received_Material_Model cn : list) { 
			 			String received_amt=cn.getReceived_Stock_MST();
			 			String used=cn.getUsed_MST();
			 			String indent=cn.getIndent_MST();
//			 	        convert string to float
			 			received_total_sum_float += Float.valueOf(received_amt);
			 			used_total_sum_float += Float.valueOf(used);
			 			indent_total_sum_float += Float.valueOf(indent);
			 			
			 			stock_history_list.add(cn);	 	          
			 		}
			       			        
			 	    String mRecvd_Amt_Rupees = String.format("%.2f", received_total_sum_float);
			 	    String mUsed_Amt_Rupees = String.format("%.2f",used_total_sum_float);
			 	    String mIndent_Amt_Rupees = String.format("%.2f",indent_total_sum_float);
			 	    mSum_Recvd_Txv_ViewStockHistory_Dialog.setText(mRecvd_Amt_Rupees);
			 	    mSum_Used_Txv_ViewStockHistory_Dialog.setText(mUsed_Amt_Rupees);
			 	    mSum_Indent_Txv_ViewStockHistory_Dialog.setText(mIndent_Amt_Rupees);
			ViewStockHistoryDialog_Adapter adapter = new
			ViewStockHistoryDialog_Adapter(Indent_Material_Description.this, R.layout.view_stock_history_dialog_row, stock_history_list);
		   
			mListview_ViewStockHistory_Dialog.setAdapter(adapter); 
		 }
				
		mClose_btn_ViewStockHistory_Dialog.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {			
						
						dialog.dismiss();
					}
				});

		
				// Display the dialog
				dialog.show();
			}
		});
	}
	private void clearfields() {
		// TODO Auto-generated method stub
		mReqdStock_Edt.setText("0.00");
	}
	private void backBtnClick() {
		// TODO Auto-generated method stub
		mBackImg.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent i=new Intent(Indent_Material_Description.this,MaterialTabs.class);
	              i.putExtra("from_material", 2);
	              startActivity(i);
	              finish();
			}
		});
		
		
	}
	
	}