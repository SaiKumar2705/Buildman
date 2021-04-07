package com.buildman.buildman.manpowertabs;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Manpower_Model;

public class Contract_Manpower_Description extends Activity {
	DatabaseHandler db;
	private TextView mWork_Loc_Name_title_Txv,mSummary,mSummary_TotalBillable_Amt_title,mSummary_TotalBillable_Amt_rs,
	mSummary_TotalBilled_Amt_title,mSummary_TotalBilled_Amt_rs,mSummary_Total_paid_title,
	mSummary_Total_paid_rs,mSummary_TotalBalance_title,mSummary_TotalBalance_rs,mContractorName_Txv,mStart_Date_Title_Txv,mStart_Date_Txv,
	mScope_of_Work_Title_Txv,mScope_of_Work_Txv,mRate_Title_Txv,mRate_Txv,mTotal_Qty_Title_Txv,mTotal_Qty_Txv,
	mCompleted_Qty_Title_Txv,mCompleted_Qty_Txv,mBillable_Amt_Title_Txv,mBillable_Amt_Txv,mBilled_Amt_Title_Txv,
	mBilled_Amt_Txv,mPaid_Title_Amt_Txv,mPaid_Amt_Txv,mBillable_Balc_Amt_Title_Txv,mBillable_Balc_Amt_Txv,
	mBilled_Balc_Amt_Title_Txv,mBilled_Balc_Amt_Txv;
	private Button mPlus_hide_show_Btn,mBack_Btn;
	private String mContractor_Name,mWork_Loc_Name,mStart_Date,mScopeOfWork,mRate,mTotal_Qty,mCompleted_Qty,mPaid_Amt;
	private int mContractor_Id;
	private NumberFormat indian_RS_Format;
	private boolean visible=false;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.contract_manpower_description);
	        db = new DatabaseHandler(this); 
//		      creating methods to write code
		        getIds();
		        getData();
		        back_Btn_Click();
		        layout_Visibility();
		        whole_project_summary_amt();
		        LinearLayout rl1 = (LinearLayout) findViewById(R.id.hide_show_contract_mp_description);
                rl1.setVisibility(View.GONE);
	 }
	 @Override
		public void onBackPressed() {
				// do something on back.
//			Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
		 Intent i=new Intent(Contract_Manpower_Description.this,ManpowerTabs.class);
		 i.putExtra("from_manpower", 1);
		startActivity(i);
		finish();
		return;
			}		
	private void whole_project_summary_amt() {
		// TODO Auto-generated method stub
		db = new DatabaseHandler(this);		
//      get data fm DB which matches contractor_name and display_flag to calculate summary for all contractors
		ArrayList<Manpower_Model> work_Loc_Name_Summary_List =db.find_ContractorID_DisplayFlag_List_MP_CONT(mContractor_Id,"Y"); 
		float  summary_Billable_Amt_float = 0 ;	 
		float  summary_Billed_Amt_float = 0 ;	 
		float  summary_Paid_Amt_float = 0 ;
	        for(int i=0;i<work_Loc_Name_Summary_List.size();i++)
	        {
	        	Manpower_Model model = work_Loc_Name_Summary_List.get(i);	        	 
	        	summary_Billable_Amt_float += (Float.parseFloat(model.getTotal_Qty_MP_CONT()))*(Float.parseFloat(model.getLab_Cont_Work_Rate_MP_CONT()));
	        	summary_Billed_Amt_float += (Float.parseFloat(model.getQty_Completed_MP_CONT()))*(Float.parseFloat(model.getLab_Cont_Work_Rate_MP_CONT()));
	        	summary_Paid_Amt_float+= Float.parseFloat(model.getPaidAmt_MP_CONT());
	        	 System.out.println("completed qty"+model.getQty_Completed_MP_CONT()+","+model.getLab_Cont_Work_Rate_MP_CONT());
	        	 System.out.println("summary billed amt"+summary_Billed_Amt_float);
	        }
	       

	        String summary_Billable_Amt_Result=String.format("%.2f", summary_Billable_Amt_float); 	       
		    String summary_Billable_Amt_Result_inRS = indian_RS_Format.format(Float.parseFloat(summary_Billable_Amt_Result));
		    mSummary_TotalBillable_Amt_rs.setText(summary_Billable_Amt_Result_inRS); 
		    
		    String summary_Billed_Amt_Result=String.format("%.2f", summary_Billed_Amt_float); 
		    String summary_Billed_Amt_Result_inRS = indian_RS_Format.format(Float.parseFloat(summary_Billed_Amt_Result));
		    mSummary_TotalBilled_Amt_rs.setText(summary_Billed_Amt_Result_inRS); 
		    
		    String summary_Paid_Amt_Result=String.format("%.2f", summary_Paid_Amt_float); 	
		    String summary_Paid_Amt_Result_inRS = indian_RS_Format.format(Float.parseFloat(summary_Paid_Amt_Result));
		    mSummary_Total_paid_rs.setText(summary_Paid_Amt_Result_inRS); 
		    
//		    calculation for remaining_balance_amt
		    float whole_Summary_Balc_Amt_float=summary_Billed_Amt_float-summary_Paid_Amt_float;
		    String whole_Summary_Balc_Amt_Result=String.format("%.2f", whole_Summary_Balc_Amt_float); 	
		    String whole_Summary_Balc_Amt_Result_inRS = indian_RS_Format.format(Float.parseFloat(whole_Summary_Balc_Amt_Result));
		    mSummary_TotalBalance_rs.setText(whole_Summary_Balc_Amt_Result_inRS);
	}
	private void layout_Visibility() {
		// TODO Auto-generated method stub
		
		mPlus_hide_show_Btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				if (visible == false){
	                visible = true;
	                 LinearLayout rl1 = (LinearLayout) findViewById(R.id.hide_show_contract_mp_description);
	                 rl1.setVisibility(View.VISIBLE);
	                 mPlus_hide_show_Btn.setBackgroundResource(R.drawable.minus);
	               
	            }
	            else {
	                visible = false;
	           	 LinearLayout rl1 = (LinearLayout) findViewById(R.id.hide_show_contract_mp_description);
	             rl1.setVisibility(View.GONE);
	             mPlus_hide_show_Btn.setBackgroundResource(R.drawable.plus);
	            
	            }
			}
		});
	}
	private void back_Btn_Click() {
		// TODO Auto-generated method stub
		mBack_Btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Contract_Manpower_Description.this,ManpowerTabs.class);
				 i.putExtra("from_manpower", 1);
					startActivity(i);	
					finish();
			}
		});
	}
	private void getData() {
		// TODO Auto-generated method stub
		mContractor_Id=getIntent().getIntExtra("contractor_id_send", 0);
		mContractor_Name=getIntent().getStringExtra("contractor_name_send");
		mWork_Loc_Name=getIntent().getStringExtra("work_loc_name_send");
		mStart_Date=getIntent().getStringExtra("start_date_send");
		mScopeOfWork=getIntent().getStringExtra("scopeOfWork_send");
		mRate=getIntent().getStringExtra("rate_send");
		mTotal_Qty=getIntent().getStringExtra("total_Qty_send");
		mCompleted_Qty=getIntent().getStringExtra("completed_Qty_send");
		mPaid_Amt=getIntent().getStringExtra("paid_amt_send");
		
//	    converting value(25000) to indian rupess(25,000.00)
	    indian_RS_Format= NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		
//		setting text to text fields
		mContractorName_Txv.setText(mContractor_Name);
		mWork_Loc_Name_title_Txv.setText(mWork_Loc_Name);
		mStart_Date_Txv.setText(mStart_Date);
		mScope_of_Work_Txv.setText(mScopeOfWork);
		
		String mRate_inRS = indian_RS_Format.format(Float.parseFloat(mRate));
		mRate_Txv.setText(mRate_inRS);
		mTotal_Qty_Txv.setText(mTotal_Qty);
		mCompleted_Qty_Txv.setText(mCompleted_Qty);
		String mPaid_Amt_inRS = indian_RS_Format.format(Float.parseFloat(mPaid_Amt));
		mPaid_Amt_Txv.setText(mPaid_Amt_inRS);	

		
//		calculating billable amt=total_qty*rate
		  float billable_amt_float=((Float.parseFloat(mTotal_Qty))*(Float.parseFloat(mRate)));
	      String billable_amt_Amt_Result=String.format("%.2f", billable_amt_float); 
	      String billable_amt_Amt_Result_inRS = indian_RS_Format.format(Float.parseFloat(billable_amt_Amt_Result));
		  mBillable_Amt_Txv.setText(billable_amt_Amt_Result_inRS); 
		  
//		calculating billed amt=completed_qty*rate
		 float billed_amt_float=((Float.parseFloat(mCompleted_Qty))*(Float.parseFloat(mRate)));
		 String billed_amt_Amt_Result=String.format("%.2f", billed_amt_float); 	
		 String billed_amt_Amt_Result_inRS = indian_RS_Format.format(Float.parseFloat(billed_amt_Amt_Result));
	     mBilled_Amt_Txv.setText(billed_amt_Amt_Result_inRS); 
	     
//		calculating billable_balance=billable_amt-paid_amt
		float billable_balc_float=(billable_amt_float)-(Float.parseFloat(mPaid_Amt));
		String billable_balc_Result=String.format("%.2f", billable_balc_float); 
		String billable_balc_Result_inRS = indian_RS_Format.format(Float.parseFloat(billable_balc_Result));
		mBillable_Balc_Amt_Txv.setText(billable_balc_Result_inRS); 
		
//		calculating billed_balance=billed_amt-paid_amt
		float billaed_balc_float=(billed_amt_float)-(Float.parseFloat(mPaid_Amt));
		String billed_balc_Result=String.format("%.2f", billaed_balc_float);
		String billed_balc_Result_inRS = indian_RS_Format.format(Float.parseFloat(billed_balc_Result));
		mBilled_Balc_Amt_Txv.setText(billed_balc_Result_inRS); 
		  
	}
	private void getIds() {
		// TODO Auto-generated method stub
		mContractorName_Txv=(TextView)findViewById(R.id.contractor_title_contract_mp_description);
		mPlus_hide_show_Btn=(Button)findViewById(R.id.layout_hide_show_plus_contract_mp_description);
		mSummary=(TextView)findViewById(R.id.summary_contract_mp_description);
		mSummary_TotalBillable_Amt_title=(TextView)findViewById(R.id.total_billable_amt_title_summary_contract_mp_description);
		mSummary_TotalBillable_Amt_rs=(TextView)findViewById(R.id.total_billable_amt_rs_summary_contract_mp_description);
		mSummary_TotalBilled_Amt_title=(TextView)findViewById(R.id.total_billed_amt_title_summary_contract_mp_description);
		mSummary_TotalBilled_Amt_rs=(TextView)findViewById(R.id.total_billed_amt_rs_summary_contract_mp_description);
		mSummary_Total_paid_title=(TextView)findViewById(R.id.totalpaid_tite_qty_summary_contract_mp_description);
		mSummary_Total_paid_rs=(TextView)findViewById(R.id.totalpaid_rs_summary_contract_mp_description);
		mSummary_TotalBalance_title=(TextView)findViewById(R.id.totalbalance_tite_qty_summary_contract_mp_description);
		mSummary_TotalBalance_rs=(TextView)findViewById(R.id.totalbalance_rs_summary_contract_mp_description);
		mWork_Loc_Name_title_Txv=(TextView)findViewById(R.id.present_work_loc_name_contract_mp_description);
		mStart_Date_Title_Txv=(TextView)findViewById(R.id.startdate_title_contract_mp_description);
		mStart_Date_Txv=(TextView)findViewById(R.id.startdate_contract_mp_description);
		mScope_of_Work_Title_Txv=(TextView)findViewById(R.id.scopeofwork_title_contract_mp_description);
		mScope_of_Work_Txv=(TextView)findViewById(R.id.scopeofwork_contract_mp_description);	
		mRate_Title_Txv=(TextView)findViewById(R.id.rate_title_contract_mp_description);
		mRate_Txv=(TextView)findViewById(R.id.rate_rs_contract_mp_description);
		mTotal_Qty_Title_Txv=(TextView)findViewById(R.id.total_qty_title_contract_mp_description);
		mTotal_Qty_Txv=(TextView)findViewById(R.id.total_qty_rs_contract_mp_description);
		mCompleted_Qty_Title_Txv=(TextView)findViewById(R.id.completed_qty_title_contract_mp_description);
		mCompleted_Qty_Txv=(TextView)findViewById(R.id.completed_qty_contract_mp_description);
		mBillable_Amt_Title_Txv=(TextView)findViewById(R.id.billable_amt_title_contract_mp_description);
		mBillable_Amt_Txv=(TextView)findViewById(R.id.billable_amt_rs_contract_mp_description);
		mBilled_Amt_Title_Txv=(TextView)findViewById(R.id.billed_amt_title_contract_mp_description);
		mBilled_Amt_Txv=(TextView)findViewById(R.id.billed_amt_rs_contract_mp_description);
		mPaid_Title_Amt_Txv=(TextView)findViewById(R.id.paid_title_contract_mp_description);
		mPaid_Amt_Txv=(TextView)findViewById(R.id.paid_rs_contract_mp_description);
		mBillable_Balc_Amt_Title_Txv=(TextView)findViewById(R.id.billable_balance_title_contract_mp_description);
		mBillable_Balc_Amt_Txv=(TextView)findViewById(R.id.billable_balance_rs_contract_mp_description);
		mBilled_Balc_Amt_Title_Txv=(TextView)findViewById(R.id.billed_balance_title_contract_mp_description);
		mBilled_Balc_Amt_Txv=(TextView)findViewById(R.id.billed_balance_rs_contract_mp_description);
		mBack_Btn=(Button)findViewById(R.id.back_Btn_contract_mp_description);
	}	 

}
