package com.buildman.buildman.manpowertabs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Manpower_Model;
import com.buildman.buildman.model.Status_Model;
import com.buildman.buildman.utils.Utils;

public class Supply_Manpower_Description extends Activity {
	
	private TextView mContractor_title,mSummary,mSummary_Total_amt_title,mSummary_Total_amt_rs,mSummary_Total_paid_title,
	mSummary_Total_paid_rs,mSummary_TotalBalance_title,mSummary_TotalBalance_rs,mPresent_labout_Type_Txv,mRate_title_txv,
	mRate_rs_Txv,mTotal_Qty_title_txv,mTotal_Qty_rs_txv,mTotal_Amt_title_Txv,mTotal_Amt_rs_Txv,mCurrent_Qty_title_Txv,mCurrent_Amt_title_Txv,
	mCurrent_Amt_rs_Txv,mTotalTillDate_Qty_title_Txv,mTotalTillDate_Qty_rs_Txv,mTotalTillDate_Amt_title_txv,
	mTotalTillDate_Amt_rs_Txv,mPaid_title_txv,mPaid_rs_Txv,mBalance_title_txv,mBalance_rs_Txv,mTransaction_datetime_Tiltle_txv,
	mTransaction_datetime_txv;
	private ImageView mBack_Img,mTransaction_date_Img;
	private EditText mCurrent_Qty_rs_Edt;
	private Button mUpdate_btn,mConfirm_btn,mPlus_hide_show,transaction_set_btn,transaction_cancel_btn;
	private DatePicker transaction_datepicker; 
	private TimePicker transaction_timepicker;
	Manpower_Model manpower_model;
	DatabaseHandler db;
	private NumberFormat indian_RS_Format;
	boolean visible=false;
	int index_position,contractor_id,prj_id,site_id;
	String contractor_name,labour_type_name,rate,total_till_date_Qty,total_billed_amt,today_Qty,paid_Amt,balance_Amt,
	display_flag,mCurrent_Date_Time,current_Amt_Result,total_till_date_Amt_Result,balance_Amt_Result;
	ArrayList<Integer>contractor_Id_list =new ArrayList<Integer>();
	 ArrayList<String>contractor_name_list =new ArrayList<String>();
	 ArrayList<String>labour_type_name_list =new ArrayList<String>();
	 ArrayList<String>rate_list =new ArrayList<String>();
	 ArrayList<String>totalTillDate_Qty_list =new ArrayList<String>();
	 ArrayList<String>total_billed_amt_list =new ArrayList<String>();
	 ArrayList<String>today_Qty_list =new ArrayList<String>();
	 ArrayList<String>paid_Amt_list =new ArrayList<String>();
	 ArrayList<String>balance_Amt_list =new ArrayList<String>();
	 ArrayList<String>display_flag_list =new ArrayList<String>();
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.supply_manpower_description);
	        db = new DatabaseHandler(this); 
//		      creating methods to write code
		        getIds();
		        getData();
		        layout_Visibility();
		        current_Date_Time();
		        whole_project_summary_amt();
		        calender_Img_Click_TransactionDate();
		        focusEdittext();
		        updateBtnClick();
		        back_Img_Click();
		        confirm_Btn_Click();
		      LinearLayout rl1 = (LinearLayout) findViewById(R.id.hide_show_supply_mp_description);
                rl1.setVisibility(View.GONE);
	 }	 

	 @Override
		public void onBackPressed() {
				// do something on back.
//			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
		 Intent i=new Intent(Supply_Manpower_Description.this,ManpowerTabs.class);
			startActivity(i);
			finish();
		return;
			}		
	 private void confirm_Btn_Click() {
		// TODO Auto-generated method stub
		 mConfirm_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 float beforeUpdate_CurrentAmt_roundFigure = 0,afterUpdate_CurrentAmt_roundFigure = 0;
				 Date past_date,today_date; 
					if(mCurrent_Qty_rs_Edt.getText().length()==0) {
						 mCurrent_Qty_rs_Edt.setError("Please fill the field");
					 }else{	
						 
						 float beforeUpdate_CurrentAmt=((Float.parseFloat(mCurrent_Qty_rs_Edt.getText().toString()))*(Float.parseFloat(rate)));
						 beforeUpdate_CurrentAmt_roundFigure=Float.parseFloat(new DecimalFormat("##.##").format(beforeUpdate_CurrentAmt));
						 		System.out.println("current_Amt_Result"+current_Amt_Result);		 
						 float afterUpdate_CurrentAmt=Float.parseFloat(current_Amt_Result);					
						 afterUpdate_CurrentAmt_roundFigure=Float.parseFloat(new DecimalFormat("##.##").format(afterUpdate_CurrentAmt));
						 System.out.println("current_Amt_Result"+current_Amt_Result);		
					  }				 
				 
				 
				 if(mCurrent_Qty_rs_Edt.getText().length()==0) {
					       mCurrent_Qty_rs_Edt.setError("Please fill the field");
				  }else if (beforeUpdate_CurrentAmt_roundFigure!=afterUpdate_CurrentAmt_roundFigure) {
							Toast.makeText(getApplicationContext(), "Current amount not matched please update", Toast.LENGTH_SHORT).show();	
				  }else {
					  		db = new DatabaseHandler(Supply_Manpower_Description.this);  
					  		manpower_model= db.find_ContractorName_LabourTypeName_DisplayFlag_MP_SUP_Table(contractor_name,labour_type_name,display_flag); 
					  		if(manpower_model!=null){
					  			int contract_project_id=manpower_model.getContract_Project_Id_MP_SUP();
					  			int contractor_id=manpower_model.getContractor_Id_MP_SUP();
						  		prj_id=manpower_model.getPrj_Id_MP_SUP();
						  		site_id=manpower_model.getSite_Id_MP_SUP();
						  		int labourType_id=manpower_model.getLabourType_Id_MP_SUP();
						  		String created_date_fmDB=manpower_model.getCreated_Date_MP_SUP();
						  		String entered_Qty=mCurrent_Qty_rs_Edt.getText().toString();
						  		
//						  		calculation for today_entered_qty_sum
						  		String today_entered_qty_sum = null;
						  		 try{
					            	 

						            	 past_date = Utils.sdf.parse(created_date_fmDB);
						            	 today_date = Utils.sdf.parse(mCurrent_Date_Time);
						     
						            		if(past_date.equals(today_date)){
						            			System.out.println("past_date is equals today_date");
						            			String today_entered_qty_sum_fmDB=manpower_model.getTodayEntered_QtySum_MP_SUP();
						            			float today_entered_qty_sum_float=((Float.parseFloat(entered_Qty))+(Float.parseFloat(today_entered_qty_sum_fmDB)));
						            			today_entered_qty_sum=String.format("%.2f", today_entered_qty_sum_float); 
						            		
						            		}else {
						            			today_entered_qty_sum = entered_Qty;
						            			System.out.println("today_entered_qty_sum is"+today_entered_qty_sum);
											}	     
						            	
						     
						        	}catch(ParseException ex){
						        		ex.printStackTrace();
						        	}            
						  		
//						  		calculation for total_till_date_Qty
						  		 System.out.println("total till date qty in Db"+total_till_date_Qty);
						  		float total_till_date_Qty_Complete_float=((Float.parseFloat(mCurrent_Qty_rs_Edt.getText().toString()))+(Float.parseFloat(total_till_date_Qty)));
						  		String total_till_date_Qty_Complete=String.format("%.2f", total_till_date_Qty_Complete_float); 
						  		 mTotalTillDate_Qty_rs_Txv.setText(total_till_date_Qty_Complete);
						  		
//						  		calculation for total_till_date_Amount=total_Billed_amt		
//								 total_till_date_amt calculation					
								 float total_till_date_Qty_float=((Float.parseFloat(mCurrent_Qty_rs_Edt.getText().toString()))+(Float.parseFloat(total_till_date_Qty)));
								 float total_till_date_Amt_float=total_till_date_Qty_float*(Float.parseFloat(rate));
								 total_till_date_Amt_Result=String.format("%.2f", total_till_date_Amt_float); 
								 String total_Billed_amt_Complete=total_till_date_Amt_Result; 	
								 
//								 Calculating Balance_Amt
								 float balance_Amt_float=((Float.parseFloat(balance_Amt))+(Float.parseFloat(current_Amt_Result)));
								 balance_Amt_Result=String.format("%.2f", balance_Amt_float);
						  		
						  		
								
								
								String currrent_Qty_string=mCurrent_Qty_rs_Edt.getText().toString();
				           	 	float currrent_Qty_float=(Float.parseFloat(currrent_Qty_string));
				           	 	String currrent_Qty_result = String.format("%.2f", currrent_Qty_float);
				           	 			if(currrent_Qty_result.equals("0.00")){
				           		 
										}else 
										{
//											inserting in DB
											db.add_MP_Supply_Record(new Manpower_Model(contract_project_id,contractor_id,contractor_name,prj_id,site_id,labourType_id,
									                   labour_type_name,entered_Qty,today_entered_qty_sum,total_till_date_Qty_Complete,rate,
									                   total_Billed_amt_Complete,paid_Amt,balance_Amt_Result,"N",mCurrent_Date_Time,
									                   mTransaction_datetime_txv.getText().toString(),"Y"));
											manpower_model.setDisplay_Flag_MP_SUP("N");
											db.Update_MP_Supply_Row(manpower_model);  
											Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
											mTotal_Qty_rs_txv.setText(total_till_date_Qty_Complete);
											 String total_Billed_amt_Complete_inRS = indian_RS_Format.format(Float.parseFloat(total_Billed_amt_Complete));
											mTotal_Amt_rs_Txv.setText(total_Billed_amt_Complete_inRS);
	//	         	               			clear the fields after saving
											clearfields();	 
										}
						  			}	
					  		db.Update_MP_Supply_Row(manpower_model);					  		
					    }
			}

			
		});
		
	}
	 private void clearfields() {
			// TODO Auto-generated method stub
			mCurrent_Qty_rs_Edt.setText("0.00");
			mCurrent_Amt_rs_Txv.setText("0.00");
			
		}

	private void back_Img_Click() {
			// TODO Auto-generated method stub
			mBack_Img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Supply_Manpower_Description.this,ManpowerTabs.class);
					startActivity(i);	
					finish();
					}
			});
		}

	private void updateBtnClick() {
		// TODO Auto-generated method stub
mUpdate_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if(mCurrent_Qty_rs_Edt.getText().length()==0){
					 mCurrent_Qty_rs_Edt.setError("Please fill the field");
				 }else{
//					 current amt calculation
					 float current_amt_float=((Float.parseFloat(mCurrent_Qty_rs_Edt.getText().toString()))*(Float.parseFloat(rate)));			      
					 current_Amt_Result=String.format("%.2f", current_amt_float);  
					 String current_Amt_Result_inRS = indian_RS_Format.format(Float.parseFloat(current_Amt_Result));
					 mCurrent_Amt_rs_Txv.setText(current_Amt_Result_inRS);
					 
//					 total_till_date_Qty calculation					
					 float total_till_date_Qty_float=((Float.parseFloat(mCurrent_Qty_rs_Edt.getText().toString()))+(Float.parseFloat(total_till_date_Qty)));
					 String total_till_date_Qty_Result=String.format("%.2f", total_till_date_Qty_float);
					 mTotalTillDate_Qty_rs_Txv.setText(total_till_date_Qty_Result);
					 
//					 total_till_date_amt calculation					
					 float total_till_date_Amt_float=total_till_date_Qty_float*(Float.parseFloat(rate));
					 total_till_date_Amt_Result=String.format("%.2f", total_till_date_Amt_float); 
					 String total_till_date_Amt_Result_inRS = indian_RS_Format.format(Float.parseFloat(total_till_date_Amt_Result));
					 mTotalTillDate_Amt_rs_Txv.setText(total_till_date_Amt_Result_inRS);
					 
//					 Calculating Balance_Amt
					 float balance_Amt_float=((Float.parseFloat(balance_Amt))+(Float.parseFloat(current_Amt_Result)));
					 balance_Amt_Result=String.format("%.2f", balance_Amt_float);
					 String balance_Amt_Result_inRS = indian_RS_Format.format(Float.parseFloat(balance_Amt_Result));
					 mBalance_rs_Txv.setText(balance_Amt_Result_inRS);
					 
					 whole_project_summary_amt();
					 try  {
				            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
				            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
				        } catch (Exception e) {

				        }
				 }
			}		

			
		});
	}
	
	private void focusEdittext() {
		// TODO Auto-generated method stub
		mCurrent_Qty_rs_Edt.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
			if(hasFocus){
				mCurrent_Qty_rs_Edt.setText("");	
			}else{
				
			}
			}
		});
	}
	
	private void calender_Img_Click_TransactionDate() {
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
		final Dialog dialog = new Dialog(Supply_Manpower_Description.this);
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
				
                	db = new DatabaseHandler(Supply_Manpower_Description.this); 
                    Status_Model status_model=db.find_Last_TransactionDate_Project_Status_Table();
                  
                    String last_Transaction_DateTime=status_model.getTransaction_Date();
                    String selected_DateTime=day_format +"-"+month_format
	                        +"-"+transaction_datepicker.getYear()+" "+transaction_timepicker.getCurrentHour()+":"
		                    +transaction_timepicker.getCurrentMinute()+":"+"00";
                    
                    
                    
                    Date last_Transaction_Date,presentTime_date,selected_Date; 
           		 String mCurrent_date =Utils.sdf.format(new Date());
                    
           		 try{
	            	 

 	        		last_Transaction_Date = Utils.sdf.parse(last_Transaction_DateTime);
 	            	 presentTime_date = Utils.sdf.parse(mCurrent_date);
 	            	selected_Date = Utils.sdf.parse(selected_DateTime);
 	            	 
 	            	 System.out.println(Utils.sdf.format(selected_Date));
 	            	System.out.println(Utils.sdf.format(last_Transaction_Date));
 	            	System.out.println(Utils.sdf.format(presentTime_date));
 	     
 	            	
 	            	if(selected_Date.after(last_Transaction_Date)&& selected_Date.before(presentTime_date)){
 	            		
 	            		
 	            		System.out.println("selected_date is after today_date");
// 	            		Toast.makeText(getApplicationContext(), "selected_date is after transactiondate and before currenttime", Toast.LENGTH_SHORT).show();
 	            		mTransaction_datetime_txv.setText(selected_DateTime);
 	            	// Close the dialog
 	  	             
 						dialog.dismiss();
 	            	}else{
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

	private void whole_project_summary_amt() {
		// TODO Auto-generated method stub
		db = new DatabaseHandler(this);		
//      get data fm DB which matches contractor_id to calculate summary of contractor for both male and female
		ArrayList<Manpower_Model> contractor_Summary_List =db.find_ContractorID_DisplayFlag_List_MP_SUP(contractor_id,"Y"); 
		float  summary_totalTillDate_Amt_float = 0 ;	     
		float  summary_total_paid_amt_float = 0 ;
	        for(int i=0;i<contractor_Summary_List.size();i++){
	        	Manpower_Model model = contractor_Summary_List.get(i);	        	 
	        	summary_totalTillDate_Amt_float += (Float.parseFloat(model.getTotalTillDate_Qty_MP_SUP()))*(Float.parseFloat(model.getRate_MP_SUP()));
	        	
	        	summary_total_paid_amt_float+= Float.parseFloat(model.getTotalpaidAmt_MP_SUP());
	        	 System.out.println("whole total amt is"+summary_totalTillDate_Amt_float); 	        	 
	        }
//		      calculate current_Qty_Amt
	        float current_amt_float=((Float.parseFloat(mCurrent_Qty_rs_Edt.getText().toString()))*(Float.parseFloat(rate)));
//	        add labour_type_current_Qty_Amount to summary_total_till_date_amountt to get whole totalproject_summary_Amount
	        float whole_Summary_Total_Amt_float=summary_totalTillDate_Amt_float+current_amt_float;
	        String whole_Summary_Total_Amt_Result=String.format("%.2f", whole_Summary_Total_Amt_float); 	        
	       
		    String whole_Summary_Total_Amt_Result_inRS = indian_RS_Format.format(Float.parseFloat(whole_Summary_Total_Amt_Result));	        
		    mSummary_Total_amt_rs.setText(whole_Summary_Total_Amt_Result_inRS); 
		    
//		    calculation for overall summary_paid_amt
		    float whole_Summary_Total_Paid_Amt_float=summary_total_paid_amt_float;
		    String whole_Summary_Total_Paid_Amt_Result=String.format("%.2f", whole_Summary_Total_Paid_Amt_float); 
		    String whole_Summary_Total_Paid_Amt_Result_inRS = indian_RS_Format.format(Float.parseFloat(whole_Summary_Total_Paid_Amt_Result));	
		    mSummary_Total_paid_rs.setText(whole_Summary_Total_Paid_Amt_Result_inRS); 
		    
//		    calculation for remaining_balance_amt
		    float whole_Summary_Remaing_Balc_Amt_float=whole_Summary_Total_Amt_float-whole_Summary_Total_Paid_Amt_float;
		    String whole_Summary_Remaing_Balc_Amt_Result=String.format("%.2f", whole_Summary_Remaing_Balc_Amt_float); 	
		    String whole_Summary_Remaing_Balc_Amt_Result_inRS = indian_RS_Format.format(Float.parseFloat(whole_Summary_Remaing_Balc_Amt_Result));	
		    mSummary_TotalBalance_rs.setText(whole_Summary_Remaing_Balc_Amt_Result_inRS);
		    
	}
	private void layout_Visibility() {
		// TODO Auto-generated method stub
		
		mPlus_hide_show.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				if (visible == false){
	                visible = true;
	                		 LinearLayout rl1 = (LinearLayout) findViewById(R.id.hide_show_supply_mp_description);
	                 rl1.setVisibility(View.VISIBLE);
	                 mPlus_hide_show.setBackgroundResource(R.drawable.minus);	               
	            }
	            else {
	                visible = false;
	           	 LinearLayout rl1 = (LinearLayout) findViewById(R.id.hide_show_supply_mp_description);
	             rl1.setVisibility(View.GONE);
	             mPlus_hide_show.setBackgroundResource(R.drawable.plus);
	            
	            }
			}
		});
	}
	 private void current_Date_Time() {
			// TODO Auto-generated method stub
////	      date calculating
//	       Calendar c = Calendar.getInstance();
//	         int mYear = c.get(Calendar.YEAR);
//	         int mMonth = c.get(Calendar.MONTH);
//	         int mDay = c.get(Calendar.DAY_OF_MONTH);
//	        
//	         
////		        time calculating
//	         
//	         int mHour = c.get(Calendar.HOUR_OF_DAY);
//	        int  mMinute = c.get(Calendar.MINUTE);
//	        int  mSecond = c.get(Calendar.SECOND);
//	        mTransaction_datetime_txv.setText((String.valueOf(mDay)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mYear))+" "+(String.valueOf(mHour)+":"+String.valueOf(mMinute)+":"+String.valueOf(mSecond)));
	         mCurrent_Date_Time=Utils.sdf.format(new Date());
	         mTransaction_datetime_txv.setText(mCurrent_Date_Time);
		}

	private void getData() {
		// TODO Auto-generated method stub
		index_position=getIntent().getIntExtra("item_position_id", 0);	
		
		contractor_Id_list=getIntent().getIntegerArrayListExtra("contractor_id_list_send"); 		
	    contractor_id=contractor_Id_list.get(index_position);
		
		contractor_name_list=getIntent().getStringArrayListExtra("contractor_name_list_send"); 		
	    contractor_name=contractor_name_list.get(index_position);
	    
	    labour_type_name_list=getIntent().getStringArrayListExtra("labour_type_name_list_send"); 		
	    labour_type_name=labour_type_name_list.get(index_position);
	    
	    rate_list=getIntent().getStringArrayListExtra("rate_list_send"); 		
	    rate=rate_list.get(index_position);
	    
	    totalTillDate_Qty_list=getIntent().getStringArrayListExtra("totalTillDate_Qty_list_send"); 		
	    total_till_date_Qty=totalTillDate_Qty_list.get(index_position);
	    System.out.println("before confirm"+total_till_date_Qty);
	    total_billed_amt_list=getIntent().getStringArrayListExtra("total_billed_amt_list_send"); 		
	    total_billed_amt=total_billed_amt_list.get(index_position);
	    
	    today_Qty_list=getIntent().getStringArrayListExtra("today_Qty_list_send"); 		
	    today_Qty=today_Qty_list.get(index_position);
	    
	    paid_Amt_list=getIntent().getStringArrayListExtra("paid_Amt_list_send"); 		
	    paid_Amt=paid_Amt_list.get(index_position);
	    
	    balance_Amt_list=getIntent().getStringArrayListExtra("balance_Amt_list_send"); 		
	    balance_Amt=balance_Amt_list.get(index_position);
	    
	    display_flag_list=getIntent().getStringArrayListExtra("display_flag_list_send"); 		
	    display_flag=display_flag_list.get(index_position);
	    
//	    converting value(25000) to indian rupess(25,000.00)
	    indian_RS_Format= NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
	   
//	    setting to fields 
	   
	    mContractor_title.setText(contractor_name);
	    mPresent_labout_Type_Txv.setText(labour_type_name);
	    
	    String rate_inRS = indian_RS_Format.format(Float.parseFloat(rate));	
	    mRate_rs_Txv.setText(rate_inRS);
	    mTotal_Qty_rs_txv.setText(total_till_date_Qty);
	    
	    String total_billed_amt_inRS = indian_RS_Format.format(Float.parseFloat(total_billed_amt));	
	    mTotal_Amt_rs_Txv.setText(total_billed_amt_inRS);
	    mCurrent_Qty_rs_Edt.setText("0.00");
	    current_Amt_Result="0.00";	   
	    
	    String current_Amt_Result_inRS = indian_RS_Format.format(Float.parseFloat(current_Amt_Result));	
	    mCurrent_Amt_rs_Txv.setText(current_Amt_Result_inRS);
	    mTotalTillDate_Qty_rs_Txv.setText(total_till_date_Qty);	    
	    
	    mTotalTillDate_Amt_rs_Txv.setText(total_billed_amt_inRS);
	    
	    String paid_Amt_inRS = indian_RS_Format.format(Float.parseFloat(paid_Amt));	
	    mPaid_rs_Txv.setText(paid_Amt_inRS);
	    
	    String balance_Amt_inRS = indian_RS_Format.format(Float.parseFloat(balance_Amt));	
	    mBalance_rs_Txv.setText(balance_Amt_inRS);
	}

	private void getIds() {
		// TODO Auto-generated method stub
		mContractor_title=(TextView)findViewById(R.id.contractor_title_supply_mp_description);
		mPlus_hide_show=(Button)findViewById(R.id.layout_hide_show_plus_supply_mp_description);
		mSummary=(TextView)findViewById(R.id.summary_supply_mp_description);
		mSummary_Total_amt_title=(TextView)findViewById(R.id.totalamt_title_summary_supply_mp_description);
		mSummary_Total_amt_rs=(TextView)findViewById(R.id.totalamt_rs_summary_supply_mp_description);
		mSummary_Total_paid_title=(TextView)findViewById(R.id.totalpaid_tite_qty_summary_supply_mp_description);
		mSummary_Total_paid_rs=(TextView)findViewById(R.id.totalpaid_rs_summary_supply_mp_description);
		mSummary_TotalBalance_title=(TextView)findViewById(R.id.totalbalance_tite_qty_summary_supply_mp_description);
		mSummary_TotalBalance_rs=(TextView)findViewById(R.id.totalbalance_rs_summary_supply_mp_description);		
		mPresent_labout_Type_Txv=(TextView)findViewById(R.id.present_labourtype_supply_mp_description);		
		mUpdate_btn=(Button)findViewById(R.id.update_supply_mp_description);		
		mRate_title_txv=(TextView)findViewById(R.id.rate_title_supply_mp_description);
		mRate_rs_Txv=(TextView)findViewById(R.id.rate_rs_supply_mp_description);
		mTotal_Qty_title_txv=(TextView)findViewById(R.id.total_qty_title_supply_mp_description);
		mTotal_Qty_rs_txv=(TextView)findViewById(R.id.total_qty_rs_supply_mp_description);
		mTotal_Amt_title_Txv=(TextView)findViewById(R.id.total_amt_title_supply_mp_description);
		mTotal_Amt_rs_Txv=(TextView)findViewById(R.id.total_amt_rs_supply_mp_description);
		mCurrent_Qty_title_Txv=(TextView)findViewById(R.id.current_qty_title_supply_mp_description);
		mCurrent_Qty_rs_Edt=(EditText)findViewById(R.id.current_qty_rs_supply_mp_description);
		mCurrent_Amt_title_Txv=(TextView)findViewById(R.id.current_amt_title_supply_mp_description);
		mCurrent_Amt_rs_Txv=(TextView)findViewById(R.id.current_amt_rs_supply_mp_description);
		mTotalTillDate_Qty_title_Txv=(TextView)findViewById(R.id.total_till_date_qty_title_supply_mp_description);
		mTotalTillDate_Qty_rs_Txv=(TextView)findViewById(R.id.total_till_date_qty_rs_supply_mp_description);
		mTotalTillDate_Amt_title_txv=(TextView)findViewById(R.id.totaltilldate_title_supply_mp_description);
		mTotalTillDate_Amt_rs_Txv=(TextView)findViewById(R.id.totaltilldate_rs_supply_mp_description);
		mPaid_title_txv=(TextView)findViewById(R.id.paid_tite_supply_mp_description);
		mPaid_rs_Txv=(TextView)findViewById(R.id.paid_rs_supply_mp_description);
		mBalance_title_txv=(TextView)findViewById(R.id.balance_title_supply_mp_description);
		mBalance_rs_Txv=(TextView)findViewById(R.id.balance_rs_supply_mp_description);
		mTransaction_datetime_Tiltle_txv=(TextView)findViewById(R.id.transaction_datetime_title_txv_supply_mp_description);
		mTransaction_datetime_txv=(TextView)findViewById(R.id.transaction_datetime_txv_supply_mp_description);		
		mTransaction_date_Img=(ImageView)findViewById(R.id.transaction_datetime_calender_img_supply_mp_description);
		mBack_Img=(ImageView)findViewById(R.id.back_supply_mp_description);
		mConfirm_btn=(Button)findViewById(R.id.confirm_supply_mp_description);
	}      
	}