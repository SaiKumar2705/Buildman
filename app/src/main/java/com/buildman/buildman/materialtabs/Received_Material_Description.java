package com.buildman.buildman.materialtabs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.Received_Material_Adapter;
import com.buildman.buildman.adapter.ViewStockHistoryDialog_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.expensestabs.Income_Expense;
import com.buildman.buildman.expensestabs.Income_Expense_Description;
import com.buildman.buildman.model.Expense_Income_Model;
import com.buildman.buildman.model.Expense_Indent_Model;
import com.buildman.buildman.model.Received_Material_Model;
import com.buildman.buildman.utils.Utils;

public class Received_Material_Description extends Activity {
	private TextView mMatl_Header,mCurrentStock_Title_txv,mCurrentStock_txv,mCurrentStock_dimen_txv,
	mRecvdStock_Title_txv,mRecvdStock_dimen_txv,mUpdtdStock_Title_txv,mUpdtdStock_txv,mUpdtdStock_dimen_txv,
	mTransaction_datetime_Tiltle_txv,mTransaction_datetime_txv,mPresent_matl_Txv,
	mHistory_Txv_ViewStockHistory_Dialog,mReceived_Txv_ViewStockHistory_Dialog,mUsed_Txv_ViewStockHistory_Dialog,
	mIndent_Txv_ViewStockHistory_Dialog,mSum_Recvd_Txv_ViewStockHistory_Dialog,mSum_Used_Txv_ViewStockHistory_Dialog,
	mSum_Indent_Txv_ViewStockHistory_Dialog	;
	private ListView mListview_ViewStockHistory_Dialog;
	private CheckBox mAdjustemnets_CBox;
	private Spinner mSpinnerDropDown_AdjustIn;
	private EditText mRecvdStock_Edt,mDC_Inv_Edt,mRemarks_Edt_Dialog;
	private Button mView_StockHistoryBtn,mconfirmBtn,mCancel_Btn_Dialog,mConfirm_Btn_Dialog,mUpdateBtn,transaction_set_btn,
	transaction_cancel_btn,mClose_btn_ViewStockHistory_Dialog;
	private Switch mDc_Invoice_Switch;
	private ImageView mBackImg,mPrev_matl_Img,mNext_matl_Img,mTransaction_date_Img,mTransaction_time_Img;
	int material_id;
	private DatePicker transaction_datepicker; 
	private  TimePicker transaction_timepicker;
	private String material,currentStock,dc_invoice,units,recevdStock,rate,stockValue_result, updatedStock_result,display_flag;
	float rate_float;
	private  Received_Material_Model received_model;
	int selected_SiteID,site_id,project_id,party_id,org_id;
    private String doc_type,last_Transaction_Time,adjustments_Type;
    DatabaseHandler db;
    private  int index_position;
	   ArrayList<Integer> matl_id_list=new ArrayList<Integer>();
	   ArrayList<String> matl_name_list=new ArrayList<String>();
	   ArrayList<String> current_stock_list=new ArrayList<String>();
	   ArrayList<String> dc_invoice_list=new ArrayList<String>();
	   ArrayList<String> units_list=new ArrayList<String>();
	   ArrayList<String> recvd_stock_list=new ArrayList<String>();
	   ArrayList<String> rate_list=new ArrayList<String>();
	   ArrayList<String> display_flag_list=new ArrayList<String>();
	   
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.received_material_description);
	        android.app.ActionBar bar=getActionBar();

//		      creating methods to write code
		        getIds();
		        current_Date_Time();	       
		        calender_Img_Click();		       
		        getData();
		        focusEdtText();
		        checkBox_Selection();
		        dc_Invoice_selection();
		        backBtnClick();
		        view_StockHistory();
		        updateBtnClick();
		        confirmBtnClick();	
		        dropDownSpinner_AdjustIn();
		      
	}	
	
	@Override
		public void onBackPressed() {
				// do something on back.
//			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
		 Intent i=new Intent(Received_Material_Description.this,MaterialTabs.class);
			startActivity(i);
			finish();
		return;
			}		

	private void calender_Img_Click() {
		// TODO Auto-generated method stub
		mTransaction_date_Img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				transaction_DateTimePicker();
			}
		});
		
	}

	private void transaction_DateTimePicker() {
		// TODO Auto-generated method stub
		// Create custom dialog object
		final Dialog dialog = new Dialog(Received_Material_Description.this);
		// hide to default title for Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setContentView(R.layout.datetime_picker_dialog);
		
		 transaction_datepicker = (DatePicker) dialog.findViewById(R.id.date_picker_dialog_received_material); 
		 transaction_timepicker = (TimePicker)dialog.findViewById(R.id.time_picker_dialog_received_material);
	
    transaction_set_btn = (Button) dialog.findViewById(R.id.set_btn_dialog_received_material);
    transaction_cancel_btn  = (Button) dialog.findViewById(R.id.cancel_btn_dialog_received_material);
    
   
    transaction_cancel_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {			
				
				dialog.dismiss();
			}
		});
		
    transaction_set_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
//
            	
                	int day=transaction_datepicker.getDayOfMonth();
                	String day_format=""+day;
                	if(day < 10){

                		day_format  = "0" + day ;
                    }
                	
                	int month=(transaction_datepicker.getMonth() + 1);
                	String month_format=""+month;
                	if(month < 10){

                		 month_format="0"+month;
                    }
				
                	db = new DatabaseHandler(Received_Material_Description.this); 
                    Received_Material_Model received_model=db.find_Last_TransactionDate_Material_Stk_Trans_Table();
                  
                    String last_Transaction_DateTime=received_model.getTransaction_Date_MST();
                    String selected_DateTime=day_format +"-"+month_format
	                        +"-"+transaction_datepicker.getYear()+" "+transaction_timepicker.getCurrentHour()+":"
		                    +transaction_timepicker.getCurrentMinute()+":"+"00";
                    
                    
                    
                    Date last_Transaction_Date,presentTime_date,selected_Date; 
           		 String mCurrent_dateTime = Utils.sdf.format(new Date());
                    
           		 try{
	            	 

 	        		last_Transaction_Date = Utils.sdf.parse(last_Transaction_DateTime);
 	            	 presentTime_date = Utils.sdf.parse(mCurrent_dateTime);
 	            	selected_Date = Utils.sdf.parse(selected_DateTime);
 	            	 
 	            	 System.out.println(Utils.sdf.format(selected_Date));
 	            	System.out.println(Utils.sdf.format(last_Transaction_Date));
 	            	System.out.println(Utils.sdf.format(presentTime_date));
 	     
 	            	
 	            	if(selected_Date.after(last_Transaction_Date)&& selected_Date.before(presentTime_date)){ 	            		
 	            		
 	            		System.out.println("selected_date is after today_date");
// 	            		Toast.makeText(getApplicationContext(), "selected_date is after transactiondate and before currenttime", Toast.LENGTH_SHORT).show();
 	            		mTransaction_datetime_txv.setText(selected_DateTime);
 	            	// Close the dialog
// 	            		transaction_datepicker.setActivated(false);
 						dialog.dismiss();
 	            	}else{
// 	            		Toast.makeText(getApplicationContext(), "selected_date is before transactiondate and after currenttime", Toast.LENGTH_SHORT).show();
 	            		Toast.makeText(getApplicationContext(), "Please change date and time", Toast.LENGTH_SHORT).show();
 	            	} 	     
 	     
 	        	}catch(ParseException ex){
 	        		ex.printStackTrace();
 	        	}
              
				
			}
		});

		// Display the dialog
		dialog.show();

	}

	private void current_Date_Time() {
		// TODO Auto-generated method stub
//      date calculating
       Calendar c = Calendar.getInstance();
         int mYear = c.get(Calendar.YEAR);
         int mMonth = c.get(Calendar.MONTH);
         int mDay = c.get(Calendar.DAY_OF_MONTH);       
         
//	        time calculating
         
         int mHour = c.get(Calendar.HOUR_OF_DAY);
        int  mMinute = c.get(Calendar.MINUTE);
        int  mSecond = c.get(Calendar.SECOND);
        mTransaction_datetime_txv.setText((String.valueOf(mDay)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mYear))
        		+" "+(String.valueOf(mHour)+":"+String.valueOf(mMinute)+":"+String.valueOf(mSecond)));
	}	
	
	private void dc_Invoice_selection() {
		// TODO Auto-generated method stub
		mDc_Invoice_Switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {

				} else {

				}
			}
		});
		
	}	
	 private void checkBox_Selection() {
			// TODO Auto-generated method stub
			mAdjustemnets_CBox.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					RelativeLayout dc_invoice_RelativeLayout = (RelativeLayout) findViewById(R.id.dc_invoice_relativelayout);
					RelativeLayout receivedStock_RelativeLayout = (RelativeLayout) findViewById(R.id.revdStock_relativelayout);
					RelativeLayout updateStock_RelativeLayout = (RelativeLayout) findViewById(R.id.updateStock_relativelayout);
					RelativeLayout stock_RelativeLayout = (RelativeLayout) findViewById(R.id.stock_relativelayout);
					RelativeLayout transaction_RelativeLayout = (RelativeLayout) findViewById(R.id.transactionDate_relativelayout);
					
					if(((CheckBox)v).isChecked()){
						mDC_Inv_Edt.setText("0");
						 mSpinnerDropDown_AdjustIn.setVisibility(View.VISIBLE);
						 dc_invoice_RelativeLayout.setVisibility(View.GONE);
						 receivedStock_RelativeLayout.setBackgroundResource(R.color.gray);
						 updateStock_RelativeLayout.setBackgroundResource(R.color.gray);
						 stock_RelativeLayout.setBackgroundResource(R.color.white);
						 transaction_RelativeLayout.setBackgroundResource(R.color.gray);
					}else {
						 mSpinnerDropDown_AdjustIn.setVisibility(View.GONE);
						 dc_invoice_RelativeLayout.setVisibility(View.VISIBLE);
						 receivedStock_RelativeLayout.setBackgroundResource(R.color.white);
						 updateStock_RelativeLayout.setBackgroundResource(R.color.white);
						 stock_RelativeLayout.setBackgroundResource(R.color.gray);
						 transaction_RelativeLayout.setBackgroundResource(R.color.white);
					}
				}
			});
		}

	private void focusEdtText() {
		// TODO Auto-generated method stub
		mRecvdStock_Edt.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
				  if (hasFocus) {					  
						  
					  mRecvdStock_Edt.setText("");
					   } 
					 
					   else {
						  
						 
					   }
			}
		});

mDC_Inv_Edt.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
				  if (hasFocus) {					  
						  
					  mDC_Inv_Edt.setText("");
					   } 
					 
					   else {
						  
						 
					   }
			}
		});
	}

	private void getIds() {
		// TODO Auto-generated method stub
		mPresent_matl_Txv=(TextView)findViewById(R.id.matl_name_header_received_material_description);	
		mView_StockHistoryBtn=(Button)findViewById(R.id.view_stockhistory_btn_received_material_description);
		mAdjustemnets_CBox=(CheckBox)findViewById(R.id.adjustments_cb_received_material_description);
		mSpinnerDropDown_AdjustIn = (Spinner)findViewById(R.id.spinner_adjustIn_received_material_description);
		mCurrentStock_Title_txv=(TextView)findViewById(R.id.currentstock_title_txv_received_material_description);
		mCurrentStock_txv=(TextView)findViewById(R.id.currentstock_txv_received_material_description);
		mCurrentStock_dimen_txv=(TextView)findViewById(R.id.currentstock_dimen_txv_received_material_description);
		mDc_Invoice_Switch = (Switch) findViewById(R.id.dc_invoice_switch);			
		mDC_Inv_Edt=(EditText)findViewById(R.id.dc_inv_edt_received_material_description);		
		mRecvdStock_Title_txv=(TextView)findViewById(R.id.receivedstock_title_txv_received_material_description);
		mRecvdStock_Edt=(EditText)findViewById(R.id.receivedstock_edt_received_material_description);
		mRecvdStock_dimen_txv=(TextView)findViewById(R.id.receivedstock_dimen_txv_received_material_description);
		mUpdateBtn=(Button)findViewById(R.id.update_stock_btn_received_material_description);
		mUpdtdStock_Title_txv=(TextView)findViewById(R.id.updatedstock_title_txv_received_material_description);
		mUpdtdStock_txv=(TextView)findViewById(R.id.updatedstock_txv_received_material_description);
		mUpdtdStock_dimen_txv=(TextView)findViewById(R.id.updatedstock_dimen_txv_received_material_description);

		mTransaction_datetime_Tiltle_txv=(TextView)findViewById(R.id.transaction_datetime_title_txv_received_material_description);
		mTransaction_datetime_txv=(TextView)findViewById(R.id.transaction_datetime_txv_received_material_description);
		
		mTransaction_date_Img=(ImageView)findViewById(R.id.transaction_datetime_calender_img_received_material_description);
	
		
		mBackImg=(ImageView)findViewById(R.id.back_img_received_material_description);
		mconfirmBtn=(Button)findViewById(R.id.confirm_btn_received_material_description);
		
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
	    

	    
	    rate_list=getIntent().getStringArrayListExtra("rate_list_send"); 		
	    rate=rate_list.get(index_position);
	    
	    display_flag_list=getIntent().getStringArrayListExtra("display_flag_list_send"); 		
	    display_flag=display_flag_list.get(index_position);	
		
		

//	    setting material to material field
	    mPresent_matl_Txv.setText(material);
	    

	    if(currentStock==null){
		
		mCurrentStock_txv.setText("0.00");
	    }
	    else{
	    	
	    	
	    	mCurrentStock_txv.setText(currentStock);
	    }
//	    setting dc_invoice number to edittext field
	    mDC_Inv_Edt.setText("0");	    
		mCurrentStock_dimen_txv.setText(units);
		mRecvdStock_dimen_txv.setText(units);
		
		mRecvdStock_Edt.setText("0.00");		
//		calculations
		String revd_stock=mRecvdStock_Edt.getText().toString();
		
			float updatedStock=((Float.parseFloat(mCurrentStock_txv.getText().toString()))+(Float.parseFloat(revd_stock)));	
		
		
		updatedStock_result = String.format("%.2f", updatedStock);
		
		mUpdtdStock_txv.setText(updatedStock_result);

		
		System.out.println("iiiiiiiiiii"+stockValue_result);
		
		mUpdtdStock_dimen_txv.setText(units);

	}
	private void updateBtnClick() {
		// TODO Auto-generated method stub
mUpdateBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				calculations for Updated stocks
				 if(mDC_Inv_Edt.getText().length()==0) {
					  mDC_Inv_Edt.setError("Please fill the field");
				}else if (mRecvdStock_Edt.getText().length()==0) {
					  mRecvdStock_Edt.setError("Please fill the field");
						
				}else{
							if(mAdjustemnets_CBox.isChecked()){
										if(mSpinnerDropDown_AdjustIn.getSelectedItem().equals("Select Remarks")){
//										Toast.makeText(getApplicationContext(), "Please select remarks in dropdown", Toast.LENGTH_SHORT).show();	
										TextView errorText = (TextView)mSpinnerDropDown_AdjustIn.getSelectedView();
										errorText.setError("Select remarks");
//										errorText.setTextColor(Color.RED);//just to highlight that this is an error
//										errorText.setText("Select remarks");
										  
										}else{
											String revd_stock=mRecvdStock_Edt.getText().toString();						  	
									  		float updatedStock=((Float.parseFloat(mCurrentStock_txv.getText().toString()))+(Float.parseFloat(revd_stock)));
									  		updatedStock_result = String.format("%.2f", updatedStock);
									  		mUpdtdStock_txv.setText(updatedStock_result);
									  		System.out.println("iiiiiiiiiii"+stockValue_result);
										
									  		mUpdtdStock_dimen_txv.setText(units);
										}
							 }else{
								 	String revd_stock=mRecvdStock_Edt.getText().toString();						  	
							  		float updatedStock=((Float.parseFloat(mCurrentStock_txv.getText().toString()))+(Float.parseFloat(revd_stock)));
							  		updatedStock_result = String.format("%.2f", updatedStock);
							  		mUpdtdStock_txv.setText(updatedStock_result);
							  		System.out.println("iiiiiiiiiii"+stockValue_result);
								
							  		mUpdtdStock_dimen_txv.setText(units);
							 }
					  		
						  try  {
					      InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
					      imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
						  } catch (Exception e) {
						  }
					}				  
				
			}
		});
	}

	private void confirmBtnClick() {
		// TODO Auto-generated method stub
		
		mconfirmBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				float sum_roundFigure = 0,stock_roundFigure = 0;
				 if (mRecvdStock_Edt.getText().length()==0) {
					  mRecvdStock_Edt.setError("Please fill the field");						
				 }else{
					 
						 float updatedStock=((Float.parseFloat(mCurrentStock_txv.getText().toString()))+(Float.parseFloat(mRecvdStock_Edt.getText().toString())));
					 sum_roundFigure=Float.parseFloat(new DecimalFormat("##.##").format(updatedStock));
				
				float  stock_qty=Float.parseFloat(mUpdtdStock_txv.getText().toString());
				 stock_roundFigure=Float.parseFloat(new DecimalFormat("##.##").format(stock_qty));
				System.out.println("sum is"+sum_roundFigure);
				System.out.println("stock_qty is"+stock_roundFigure);
				 }
				
				 if(mDC_Inv_Edt.getText().length()==0) {
					  mDC_Inv_Edt.setError("Please fill the field");				
				 }else if (mRecvdStock_Edt.getText().length()==0) {
					  mRecvdStock_Edt.setError("Please fill the field");				 
				 }else if (stock_roundFigure!=sum_roundFigure) {
					Toast.makeText(getApplicationContext(), "Stock Qty not matched please update stock", Toast.LENGTH_SHORT).show();	
				 }else
				 {
			  	db = new DatabaseHandler(Received_Material_Description.this);  
			  	 received_model= db.find_SiteID_MaterialID_DisplayFlag_Material_Stk_Trans_Table(selected_SiteID,material_id,display_flag); 
			  	 
			  	site_id=received_model.getSite_ID_MST();
			  	 project_id=received_model.getProject_ID_MST();
			   party_id=received_model.getParty_ID_MST();
			  	 org_id=received_model.getOrg_ID_MST();
			  	 System.out.println("check my site id "+received_model.getSite_ID_MST());
			  	String revd_stock_result = null;
	               			if(received_model!=null) 
	               			{   	                 
	            	 //for dc and invoice insertion

	               				if(mDc_Invoice_Switch.isChecked())
	               				{
	            	    	
	               					doc_type="Invoice";
	               				}
	               				else {
	            		
	               					doc_type="DC";
	               				}		                
//	               				calucation for adjustments(adjust_in)
	               				if(mAdjustemnets_CBox.isChecked()){
	               					adjustments_Type=mAdjustemnets_CBox.getText().toString();
	               				}else{
	               					adjustments_Type="Normal";
	               				}
	               				
//	         			calculations for  Received_stock   what you entered 
	               				String revd_stock=mRecvdStock_Edt.getText().toString();
	               				float revd_stock_float=(Float.parseFloat(revd_stock));
	               				revd_stock_result = String.format("%.2f", revd_stock_float);
	           	     

	               				String mTodayDate=Utils.sdf.format(new Date());

	               				if(revd_stock_result.equals("0.00")){
	               								
	               				}else if (revd_stock_result.equals("0")) {              					
	               				
								}else{
											if(mAdjustemnets_CBox.isChecked()){
													if(mSpinnerDropDown_AdjustIn.getSelectedItem().equals("Select Remarks")){
//				               					  Toast.makeText(getApplicationContext(), "Please select item in dropdown", Toast.LENGTH_SHORT).show();	
				               					TextView errorText = (TextView)mSpinnerDropDown_AdjustIn.getSelectedView();
												errorText.setError("Select remarks");
													}else{
//				               				 new transaction row added with display_flag "Y" and syn_flag to "N"
					               				db.add_Material_Stk_Trans_Record(new Received_Material_Model(material_id,mPresent_matl_Txv.getText().toString(),
					               				units,"0.00",revd_stock_result,"0.00","0.00",updatedStock_result,mTransaction_datetime_txv.getText().toString(),
					               				project_id,site_id,party_id,org_id,"N","Y","1753-01-01",mDC_Inv_Edt.getText().toString(),"0",doc_type,
					               				mTodayDate,"0",adjustments_Type,String.valueOf(mSpinnerDropDown_AdjustIn.getSelectedItem()),0));
//					              			 prevous transtion row  display_flag update to "N"
					               				received_model.setDisplay_Flag_MST("N");
					               				db.Update_Material_Stk_TransRow(received_model);  
					               				Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
//					         	               clear the fields after saving
					         	               clearfields();	 
														}
											}else
											{
	//						         		 new transaction row added with display_flag "Y" and syn_flag to "N"
				               				db.add_Material_Stk_Trans_Record(new Received_Material_Model(material_id,mPresent_matl_Txv.getText().toString(),
				               				units,"0.00",revd_stock_result,"0.00","0.00",updatedStock_result,mTransaction_datetime_txv.getText().toString(),
				               				project_id,site_id,party_id,org_id,"N","Y","1753-01-01",mDC_Inv_Edt.getText().toString(),"0",doc_type,
				               				mTodayDate,"0",adjustments_Type,"NA",0));
	//			              				 prevous transtion row  display_flag update to "N"
				               				received_model.setDisplay_Flag_MST("N");
				               				db.Update_Material_Stk_TransRow(received_model);  
				               				Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
	//			         	               clear the fields after saving
				         	               clearfields();	 
											}
									}	                    
	               			}  	             
	               db.Update_Material_Stk_TransRow(received_model); 	          	
             

				  }
			}

		});
		
	}
	private void dropDownSpinner_AdjustIn() {
		// TODO Auto-generated method stub
		
		  List<String> spinner_List = new ArrayList<String>();
		spinner_List.add("Select Remarks");
		spinner_List.add("Wrong Entry");
		spinner_List.add("Stock Audit");
		spinner_List.add("Stock Transfer-In");
       
        
	
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, spinner_List);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerDropDown_AdjustIn.setAdapter(dataAdapter);
		
		// Set the ClickListener for Spinner
		mSpinnerDropDown_AdjustIn.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() { 

		              public void onItemSelected(AdapterView<?> adapterView, 
		             View view, int i, long l) { 
		             // TODO Auto-generated method stub
	             
		               }
		                // If no option selected
		    public void onNothingSelected(AdapterView<?> arg0) {
		     // TODO Auto-generated method stub			          
		    } 

		        });
	}

	
	
	private void clearfields() {
		// TODO Auto-generated method stub
		 mDC_Inv_Edt.setText("0");
//		 mLot_Number_Edt.setText("0");
         mRecvdStock_Edt.setText("0.00");	
         mCurrentStock_txv.setText(mUpdtdStock_txv.getText().toString());
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
				db = new DatabaseHandler(Received_Material_Description.this);  
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
			ViewStockHistoryDialog_Adapter(Received_Material_Description.this, R.layout.view_stock_history_dialog_row, stock_history_list);
		   
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
	private void backBtnClick() {
		// TODO Auto-generated method stub
		mBackImg.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Received_Material_Description.this,MaterialTabs.class);
				startActivity(i);
				finish();
			}
		});
		
		
	}
}