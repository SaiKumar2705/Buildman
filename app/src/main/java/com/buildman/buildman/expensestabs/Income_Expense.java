package com.buildman.buildman.expensestabs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.buildman.buildman.activity.Home;
import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.Income_Expense_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Expense_Income_Model;
import com.buildman.buildman.model.Status_Model;
import com.buildman.buildman.utils.Utils;

public class Income_Expense extends Activity {
private TextView mAccount_Name_Txv,mDate_Txv,mRevdAmt_FmPortal_Title_Txv,mRevdAmt_FmPortal_Txv;
private	Button mDate_btn,mCancel_btn,mApprove_btn,mAddNew_btn;
private	ListView mListview;
DatabaseHandler db;
private	 ArrayList<Expense_Income_Model> income_list=new ArrayList<Expense_Income_Model>();
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.income_expense);
	        db = new DatabaseHandler(this); 
//		      creating methods to write code       
		        getIds();
		        if(db.getExp_IncomeTable_Row_Count()==0){
		        	 create_DB();
		        }else{
		        	
		        }
		       
		        getList_Fm_DB();

	 }
	
	@Override
	   	public void onBackPressed() {
	   			// do something on back.
//	   		Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
	       	Intent i=new Intent(Income_Expense.this,Home.class);
	   		startActivity(i);
	   		finish();
	   	return;
	   		}	

	private void getList_Fm_DB() {
		// TODO Auto-generated method stub
		db = new DatabaseHandler(this);
		// Reading all contacts
        
        List<Expense_Income_Model> list = db.getAll_Exp_Income_List();    
        System.out.println("Reading size of list "+ list.size()); 
        LinearLayout relative = (LinearLayout) findViewById(R.id.listview_linearlayout_exp_income);
	        if(list.size()==0){
	      	
	      	  relative.setBackgroundResource(0); 
	        }else {
	      	  relative.setBackgroundResource(R.drawable.box_bottom_border);
	        }
      System.out.println("Reading size of list "+ list.size()); 
      float total_sum_amt_float =0;
        for (Expense_Income_Model cn : list) {	          
          
		income_list.add(cn);             
        }

        Income_Expense_Adapter adapter = new
        		Income_Expense_Adapter(Income_Expense.this, R.layout.income_expense_row,income_list);
	   
		mListview.setAdapter(adapter);
		
		mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                                int position, long id) {
	        	Expense_Income_Model expense_Income_Model = (Expense_Income_Model) parent.getItemAtPosition(position);
	        	final int exp_income_id=expense_Income_Model.getExp_Income_Id();
	        	String mCurrentBalc=expense_Income_Model.getCurrentBalance();
	        	String mAccount_name=expense_Income_Model.getAccount();
	        	
	        	if(mCurrentBalc.equals("1000.00")){
	        		// Create custom dialog object
					final Dialog dialog = new Dialog(view.getContext());
					// hide to default title for Dialog
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

					dialog.setContentView(R.layout.income_expense_dialog);
					
				mAccount_Name_Txv= (TextView) dialog.findViewById(R.id.account_name_txv_income_exp_dialog);
				mDate_Txv = (TextView) dialog.findViewById(R.id.date_txv_income_exp_dialog);
			    mDate_btn  = (Button) dialog.findViewById(R.id.datebtn_income_exp_dialog);
			    mRevdAmt_FmPortal_Title_Txv = (TextView) dialog.findViewById(R.id.recvd_amt_fm_portal_title_txv_income_exp_dialog);
			    mRevdAmt_FmPortal_Txv = (TextView) dialog.findViewById(R.id.recvd_amt_fm_portal_txv_income_exp_dialog);
			    mCancel_btn = (Button) dialog.findViewById(R.id.cancel_btn_income_exp_dialog);
			    mApprove_btn  = (Button) dialog.findViewById(R.id.approve_btn_income_exp_dialog);
			    mAddNew_btn  = (Button) dialog.findViewById(R.id.add_new_btn_income_exp_dialog);
			    
			    mAccount_Name_Txv.setText(mAccount_name);
//			    mRevdAmt_FmPortal_Txv.setText("")
			    mDate_Txv.setText(Utils.sdf.format(new Date()));
			    mDate_btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final Calendar c = Calendar.getInstance();
				           int mYear = c.get(Calendar.YEAR);
				           int mMonth = c.get(Calendar.MONTH);
				           int mDay = c.get(Calendar.DAY_OF_MONTH);
				 
				            // Launch Date Picker Dialog
				            DatePickerDialog dpd = new DatePickerDialog(Income_Expense.this,
				                    new DatePickerDialog.OnDateSetListener() {
				 
				                        @Override
				                        public void onDateSet(DatePicker view, int year,
				                                int monthOfYear, int dayOfMonth) {
				                            // Display Selected date in textbox
				                        	mDate_Txv.setText(dayOfMonth + "-"
				                                    + (monthOfYear + 1) + "-" + year);
				 
				                        }
				                    }, mYear, mMonth, mDay);
				            dpd.show();
					}
				});

					
			    mCancel_btn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {			
							
							dialog.dismiss();
						}
					});

					
			    mApprove_btn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Expense_Income_Model model= db.find_Exp_Inc_ID_IncomeExp(exp_income_id); 
							if(model!=null){
								model.setCurrentBalance("1000.01");
								db.Update_Exp_Income_Row(model);  
							}
							db.Update_Exp_Income_Row(model);  
							// Close the dialog				             
							dialog.dismiss();
						}
					});
				
			    mAddNew_btn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {							
							// Close the dialog				             
							dialog.dismiss();
							Intent i=new Intent(Income_Expense.this,Income_Expense_Description.class);
							startActivity(i);
							finish();
						}
					});

					// Display the dialog
					dialog.show();
	        	}else{
	        		Intent i=new Intent(Income_Expense.this,Income_Expense_Description.class);
					startActivity(i);
					finish();
	        	}
	        	
	        }
	    }); 
	}


	private void getIds() {
		// TODO Auto-generated method stub

		mListview=(ListView)findViewById(R.id.listview_exp_income);
		
	}
	 private void create_DB() {
			// TODO Auto-generated method stub
		 db = new DatabaseHandler(this);
		 db.add_Exp_Income_Record(new Expense_Income_Model("Account-1","1000.00","Cash","NA","NA","NA","NA","NA","NA","NA",
				 "NA","NA","NA","NA","NA")) ;
		 db.add_Exp_Income_Record(new Expense_Income_Model("Account-2","5000.00","Cheque","NA","NA","NA","NA","NA","NA","NA",
				 "NA","NA","NA","NA","NA")) ;
		 db.add_Exp_Income_Record(new Expense_Income_Model("Account-3","1600.00","Cash","NA","NA","NA","NA","NA","NA","NA",
				 "NA","NA","NA","NA","NA")) ;
		 db.add_Exp_Income_Record(new Expense_Income_Model("Account-4","1000.00","Cash","NA","NA","NA","NA","NA","NA","NA",
				 "NA","NA","NA","NA","NA")) ;
		 db.add_Exp_Income_Record(new Expense_Income_Model("Account-5","1550.00","Cash","NA","NA","NA","NA","NA","NA","NA",
				 "NA","NA","NA","NA","NA")) ;
		}
}
