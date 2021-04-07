package com.buildman.buildman.statustabs;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Status_Model;
import com.buildman.buildman.utils.Utils;

public class Status_Status_Description extends Activity {
	TextView mPresent_TaskName_Txv,mEST_Title_txv,mEST_StartDate_txv,
	mEST_EndDate_txv,mStartDate_Title_txv,mEndDate_Title_txv,mActual_Title_txv,mActual_StartDate_txv,mActual_EndDate_txv,mTotalQty_Title_txv,mTotalQty_txv,
	mTotalQty_Units_txv,mCompletedQty_Title_txv,mCompletedQty_txv,mCompletedQty_Units_txv,mCurrentQty_Title_txv,
	mCurrentQty_Units_txv,mTotalCompletedQty_Title_txv,mTotalCompletedQty_txv,mTotalCompletedQty_Units_txv,
	mTransaction_datetime_Tiltle_txv,mTransaction_datetime_txv;
	EditText mCurrentQty_Edt;
	ImageView mTransaction_date_Img,mBackImg;
	 DatePicker transaction_datepicker; 
	 TimePicker transaction_timepicker;
	 Button mUpdate_Btn,mConfirmBtn,transaction_set_btn,transaction_cancel_btn;
	   DatabaseHandler db;
	   Status_Model status_model;
	   int selected_SiteID,work_master_id,site_id,project_id;	  
	   String task_name,est_startDate,est_endDate,total_qty,units,completed_qty,totalCompleted_Qty_result, percentage_completion;
	  
		  
		
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.status_status_description);
	        android.app.ActionBar bar=getActionBar();
	      
//		      creating methods to write code
		        getIds();
		        getData();		    
		        current_Date_Time();	       
		        calender_Img_Click();       		       
		        focusEdtText();
		        actual_StartDate_Picker();
		        actual_EndDate_Picker();
		        backBtnClick();
		        updateBtnClick();
		        confirmBtnClick();	  
		      
	}
	 @Override
		public void onBackPressed() {
				// do something on back.
//			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
		 Intent i=new Intent(Status_Status_Description.this,StatusTabs.class);
			startActivity(i);
			finish();
		return;
			}	
	 	 
		private void actual_StartDate_Picker() {
		// TODO Auto-generated method stub
mActual_StartDate_txv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// On button click show datepicker dialog 
					start_Date_Selection();
				}			
				
			});	
	}
	 	private void start_Date_Selection() {
			// TODO Auto-generated method stub
	 		final Calendar c = Calendar.getInstance();
	 		int mYear = c.get(Calendar.YEAR);
	 		int mMonth = c.get(Calendar.MONTH);
	 		int mDay = c.get(Calendar.DAY_OF_MONTH);
	 		 
	 		DatePickerDialog dpd = new DatePickerDialog(this,
	 		        new DatePickerDialog.OnDateSetListener() {
	 		 
	 		            @Override
	 		            public void onDateSet(DatePicker view, int year,
	 		                    int monthOfYear, int dayOfMonth) {
	 		            	mActual_StartDate_txv.setText(dayOfMonth + "-"
	 		                        + (monthOfYear + 1) + "-" + year);
	 		 
	 		            }
	 		        }, mYear, mMonth, mDay);
	 		dpd.show();
		}

		private void actual_EndDate_Picker() {
		// TODO Auto-generated method stub
			mActual_EndDate_txv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// On button click show datepicker dialog 
	              
					end_Date_Selection();
					
				}				
				
			});	
	}
	 	private void end_Date_Selection() {
			// TODO Auto-generated method stub
	 		final Calendar c = Calendar.getInstance();
	 		int mYear = c.get(Calendar.YEAR);
	 		int mMonth = c.get(Calendar.MONTH);
	 		int mDay = c.get(Calendar.DAY_OF_MONTH);
	 		 
	 		DatePickerDialog dpd = new DatePickerDialog(this,
	 		        new DatePickerDialog.OnDateSetListener() {
	 		 
	 		            @Override
	 		            public void onDateSet(DatePicker view, int year,
	 		                    int monthOfYear, int dayOfMonth) {

	 		            	
	 	                	int day_selected=dayOfMonth;
	 	                	String day_selected_format=""+day_selected;
	 	                	if(day_selected < 10){

	 	                		day_selected_format  = "0" + day_selected ;
	 	                    }
	 	                	
	 	                	int month_selected=(monthOfYear + 1);
	 	                	String month_selected_format=""+month_selected;
	 	                	if(month_selected < 10){

	 	                		month_selected_format="0"+month_selected;
	 	                    }
	 		            	
	 		            	mActual_EndDate_txv.setText(day_selected_format + "-"
	 		                        + month_selected_format + "-" + year);
	 		            	
	 		            	String endDate=mActual_EndDate_txv.getText().toString();
							if(endDate.equals("")){
								mActual_EndDate_txv.setBackgroundResource(R.drawable.calender);	
							}else{
								mActual_EndDate_txv.setBackgroundResource(R.color.trance);

							}
	 		            }
	 		        }, mYear, mMonth, mDay);
	 		dpd.show();
		}
	
	
	private void confirmBtnClick() {
			// TODO Auto-generated method stub
			mConfirmBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					float sum_roundFigure = 0,total_completed_qty_roundFigure = 0;
					
					if(mCurrentQty_Edt.getText().length()==0) {
						mCurrentQty_Edt.setError("Please fill the field");
					 }else{
					
					System.out.println("complete qty "+mCompletedQty_txv.getText().toString());
					System.out.println("current qty"+mCurrentQty_Edt.getText().toString());
					float updatedStock=((Float.parseFloat(mCompletedQty_txv.getText().toString()))+(Float.parseFloat(mCurrentQty_Edt.getText().toString())));					
					 sum_roundFigure=Float.parseFloat(new DecimalFormat("##.##").format(updatedStock));
					
					float  total_completed_qty=Float.parseFloat(mTotalCompletedQty_txv.getText().toString());
					 total_completed_qty_roundFigure=Float.parseFloat(new DecimalFormat("##.##").format(total_completed_qty));
					System.out.println("sum is"+sum_roundFigure);
					System.out.println("total_completed_qty_roundFigure is"+total_completed_qty_roundFigure);
					  }
										
					
					if(mCurrentQty_Edt.getText().length()==0) {
						mCurrentQty_Edt.setError("Please fill the field");
					  }else if (total_completed_qty_roundFigure!=sum_roundFigure) {
							Toast.makeText(getApplicationContext(), "Total complete Qty not matched please update status", Toast.LENGTH_SHORT).show();	
					  }else {
				  	db = new DatabaseHandler(Status_Status_Description.this);  
				  	 status_model= db.find_SiteID_WorkMaster_ID_DisplayFlag_ProjectStatus_Table(selected_SiteID,work_master_id,"Y"); 
				  	 
				  	 project_id=status_model.getProject_ID();
				  	 site_id=status_model.getSite_ID();	
				  	String remarks=status_model.getRemarks();
				  	int work_Task_Type_Id=status_model.getWork_TaskType_ID();
				  	int main_Task_Id=status_model.getMain_Task_ID();
				  	String main_Task_Name=status_model.getMain_Task_Name();
				  	String link_To_Bill=status_model.getLink_ToBill();
				  	String sum_Entered_Qty_before=status_model.getSum_Entered_Qty();
				  	float sum_Entered_Qty_float=((Float.parseFloat(mCurrentQty_Edt.getText().toString()))+(Float.parseFloat(sum_Entered_Qty_before)));
					String sum_Entered_Qty_result=String.format("%.2f", sum_Entered_Qty_float);
					
				  	percentage_Completion();
				 
				  	
				  
		               if(status_model!=null) {    	    
		            	 
							 String current_qty=mCurrentQty_Edt.getText().toString();
							float completed_qty_float=((Float.parseFloat(mCompletedQty_txv.getText().toString()))+(Float.parseFloat(current_qty)));
							String completed_qty_result=String.format("%.2f", completed_qty_float);
							
		           	   String est_start_date_result=mEST_StartDate_txv.getText().toString();
		           	   String est_end_date_result=mEST_EndDate_txv.getText().toString();
		           	   String actual_start_date_result;
		           	   
		           	   		Status_Model my_model=db.find_SiteId_WorkMaster_Id_ProjectStatus_Table(site_id, work_master_id);
		           	   			if(db.CountRecords_Matched_SiteId_WorkMasterId_ProjectStatus_Table(site_id, work_master_id)==0){
		           	   			actual_start_date_result=mActual_StartDate_txv.getText().toString();
		           	   			}else{	
		           	   			String start_date_fmDB=my_model.getStart_Date();
		           	   						if(start_date_fmDB.equals("00-00-0000")){
		           	   						actual_start_date_result= Utils.sdf.format(new Date());
		           	   						}else {
		           	   						actual_start_date_result=start_date_fmDB;
		           	   						}  
		           	   			
		           	   			}
		           	   
		           	   
		           	   String actual_end_date_selected=mActual_EndDate_txv.getText().toString();	
		           	   System.out.println("Actual end date is"+actual_end_date_selected);
		           	   String actual_end_date_result;
		           	   			if(actual_end_date_selected.equals("")){
		           	   				actual_end_date_result="01-01-1753";
		           	   			}else {
		           	   				actual_end_date_result=actual_end_date_selected;
		           	   			}  
		           	   
		           	 String mDate_Time_Now=Utils.sdf.format(new Date());
		           	 
		           	 	String currrent_Qty_string=mCurrentQty_Edt.getText().toString();
		           	 	float currrent_Qty_float=(Float.parseFloat(currrent_Qty_string));
		           	 	String currrent_Qty_result = String.format("%.2f", currrent_Qty_float);
		           	 			if(currrent_Qty_result.equals("0.00")){
		           		 
								}else {
//						         	 new transaction row added with display_flag "Y" and syn_flag to "N"			
					            	   db.add_Project_Status_Record(new Status_Model(project_id,site_id,work_master_id,task_name,
					            			   										percentage_completion,total_qty,units,
												            			   			completed_qty_result,est_start_date_result,
												            			   			est_end_date_result,actual_start_date_result,
												            			   			actual_end_date_result,"N",mDate_Time_Now,
												            			   			mTransaction_datetime_txv.getText().toString(),
												            			   			"Y",remarks,work_Task_Type_Id,main_Task_Id,
												            			   			main_Task_Name,link_To_Bill,sum_Entered_Qty_result));
//						               prevous transtion row  display_flag update to "N"
						               status_model.setDisplay_Flag("N");
						               db.Update_Project_Status_Row(status_model);  
						               Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
//						               clear the fields after saving
						               clearfields();
								}

		            	   
		                  }  		                     
		             
		               db.Update_Project_Status_Row(status_model);  
		          	

		              
		               
		               
					  }
				
				}

				
			});
			
		}

		private void clearfields() {
			// TODO Auto-generated method stub
			String current_qty=mCurrentQty_Edt.getText().toString();
			float completed_qty_float=((Float.parseFloat(mCompletedQty_txv.getText().toString()))+(Float.parseFloat(current_qty)));
			String completed_qty_result=String.format("%.2f", completed_qty_float);
			mCompletedQty_txv.setText(completed_qty_result);
			mCurrentQty_Edt.setText("0.00");
		}

	 private void percentage_Completion() {
			// TODO Auto-generated method stub
		 float total = Float.parseFloat(mTotalQty_txv.getText().toString());
		 float score = Float.parseFloat(mTotalCompletedQty_txv.getText().toString());
		
		 float  percentage = (score * 100/ total);
	percentage_completion=String.format("%.2f", percentage);
	System.out.println("work % completed"+percentage_completion);
		}

	 private void updateBtnClick() {
			// TODO Auto-generated method stub
	mUpdate_Btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub						
					
					
//					calculations for total_completed_qty
					 if((mCurrentQty_Edt.getText().length()==0)) {
						mCurrentQty_Edt.setError("Please fill the field");
						
					  }	else {
						  String current_qty=mCurrentQty_Edt.getText().toString();
//							Toast.makeText(getApplicationContext(), "u"+mCurrentStock_txv.getText().toString(), Toast.LENGTH_SHORT).show();
							float total_completed_qty_float=((Float.parseFloat(mCompletedQty_txv.getText().toString()))+(Float.parseFloat(current_qty)));
									if(total_completed_qty_float<=(Float.parseFloat(mTotalQty_txv.getText().toString()))){
										totalCompleted_Qty_result = String.format("%.2f", total_completed_qty_float);
							
										mTotalCompletedQty_txv.setText(totalCompleted_Qty_result);

							
										System.out.println("total completed_qty is"+totalCompleted_Qty_result);
									}else {
										Toast toast = Toast.makeText(Status_Status_Description.this,"Total Completed Qty cannot be greater than the Total Qty", Toast.LENGTH_SHORT);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();
										mCurrentQty_Edt.setText("");
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
	private void getData() {
		// TODO Auto-generated method stub
		SharedPreferences siteId_pref1 = getSharedPreferences("selected_SiteId_UserSite_MyPrefs", Context.MODE_PRIVATE);		 
		 selected_SiteID = siteId_pref1.getInt("selected_SiteId_UserSite_KEY_PREF", 0);			
			
		work_master_id=getIntent().getIntExtra("Send_mSubTaskID", 0); 
		 	
	    task_name=getIntent().getStringExtra("Send_mSubTaskName");	    
	   	
	    est_startDate=getIntent().getStringExtra("Send_mEST_Start_Date");	    
	   	
	    est_endDate=getIntent().getStringExtra("Send_mEST_End_Date");	  
	   	
	    total_qty=getIntent().getStringExtra("Send_mTotal_Qty");		   
	   	
	    units=getIntent().getStringExtra("Send_mUnits");	    
	   	
	    completed_qty=getIntent().getStringExtra("Send_mCompleted_Qty");	
	   

//	    setting task_name to task_name field
	    mPresent_TaskName_Txv.setText(task_name);
	    mEST_StartDate_txv.setText(est_startDate);
	    mEST_EndDate_txv.setText(est_endDate);
	    mActual_StartDate_txv.setText(Utils.sdf.format(new Date()));

	    if(total_qty==null){
		
		mTotalQty_txv.setText("0.00");
	    }
	    else{	    	
	    	
	    	mTotalQty_txv.setText(total_qty);
	    }
	    if(completed_qty==null){
			
			mCompletedQty_txv.setText("0.00");
		    }
		    else{	    	
		    	
		    	mCompletedQty_txv.setText(completed_qty);
		 }
	   mCurrentQty_Edt.setText("0.00");
	    
		mTotalQty_Units_txv.setText(units);
		mCompletedQty_Units_txv.setText(units);		
		mCurrentQty_Units_txv.setText(units);
		mTotalCompletedQty_Units_txv.setText(units);
//		calculations
		String current_qty=mCurrentQty_Edt.getText().toString();
		float totalCompleted_Qty_float=((Float.parseFloat(mCompletedQty_txv.getText().toString()))+(Float.parseFloat(current_qty)));
		totalCompleted_Qty_result = String.format("%.2f", totalCompleted_Qty_float);
		
		mTotalCompletedQty_txv.setText(totalCompleted_Qty_result);

		System.out.println("iiiiiiiiiii"+totalCompleted_Qty_result);
		
	

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
		final Dialog dialog = new Dialog(Status_Status_Description.this);
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
				
                	db = new DatabaseHandler(Status_Status_Description.this); 
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
        mTransaction_datetime_txv.setText((String.valueOf(mDay)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mYear))+" "+(String.valueOf(mHour)+":"+String.valueOf(mMinute)+":"+String.valueOf(mSecond)));

	}

	private void focusEdtText() {
		// TODO Auto-generated method stub
		mCurrentQty_Edt.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
				  if (hasFocus) {					  
						  
					  mCurrentQty_Edt.setText("");
					   } 
					 
					   else {
						  
						 
					   }
			}
		});

	}

	

	



	
	private void backBtnClick() {
		// TODO Auto-generated method stub
		mBackImg.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Status_Status_Description.this,StatusTabs.class);
				startActivity(i);
				finish();
			}
		});
		
		
	}
	
	private void getIds() {
		// TODO Auto-generated method stub	

		mPresent_TaskName_Txv=(TextView)findViewById(R.id.task_name_header_status_status_description);		

		mStartDate_Title_txv=(TextView)findViewById(R.id.startdate_txv_status_status_description);
		mEndDate_Title_txv=(TextView)findViewById(R.id.enddate_txv_status_status_description);
		mEST_Title_txv=(TextView)findViewById(R.id.est_title_txv_status_status_description);
		mEST_StartDate_txv = (TextView) findViewById(R.id.est_startdate_txv_status_status_description);		
		
		mEST_EndDate_txv=(TextView)findViewById(R.id.est_enddate_txv_status_status_description);
		mActual_Title_txv=(TextView)findViewById(R.id.actual_title_txv_status_status_description);
		mActual_StartDate_txv=(TextView)findViewById(R.id.actual_startdate_txv_status_status_description);
		mActual_EndDate_txv=(TextView)findViewById(R.id.actual_enddate_txv_status_status_description);	
		mTotalQty_Title_txv=(TextView)findViewById(R.id.total_qty_title_txv_status_status_description);
		mTotalQty_txv=(TextView)findViewById(R.id.total_qty_txv_status_status_description);
		mTotalQty_Units_txv=(TextView)findViewById(R.id.total_qty_units_txv_status_status_description);
		mCompletedQty_Title_txv=(TextView)findViewById(R.id.completed_qty_title_txv_status_status_description);
		mCompletedQty_txv=(TextView)findViewById(R.id.completed_qty_txv_status_status_description);
		mCompletedQty_Units_txv=(TextView)findViewById(R.id.completed_qty_units_txv_status_status_description);
		mCurrentQty_Title_txv=(TextView)findViewById(R.id.current_qty_title_txv_status_status_description);
		mCurrentQty_Edt=(EditText)findViewById(R.id.current_qty_edt_status_status_description);
		mCurrentQty_Units_txv=(TextView)findViewById(R.id.current_qty_units_txv_status_status_description);
		mUpdate_Btn=(Button)findViewById(R.id.update_stock_btn_status_status_description);
		mTotalCompletedQty_Title_txv=(TextView)findViewById(R.id.total_completed_qty_title_txv_status_status_description);
		mTotalCompletedQty_txv=(TextView)findViewById(R.id.total_completed_qty_txv_status_status_description);
		mTotalCompletedQty_Units_txv=(TextView)findViewById(R.id.totalcompleted_qty_units_txv_status_status_description);
		
		mTransaction_datetime_Tiltle_txv=(TextView)findViewById(R.id.transaction_datetime_title_txv_status_status_description);
		mTransaction_datetime_txv=(TextView)findViewById(R.id.transaction_datetime_txv_status_status_description);
		
		mTransaction_date_Img=(ImageView)findViewById(R.id.transaction_datetime_calender_img_status_status_description);
	
		
		mBackImg=(ImageView)findViewById(R.id.back_img_status_status_description);
		mConfirmBtn=(Button)findViewById(R.id.confirm_btn_status_status_description);
		
	}
}