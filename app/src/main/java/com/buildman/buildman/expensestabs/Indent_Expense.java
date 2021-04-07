package com.buildman.buildman.expensestabs;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.buildman.buildman.activity.Home;
import com.buildman.buildman.activity.R;
import com.buildman.buildman.adapter.Indent_Expense_Adapter;
import com.buildman.buildman.database.DatabaseHandler;
import com.buildman.buildman.model.Expense_Indent_Model;

public class Indent_Expense extends Activity {
	TextView mDate_header_txv,mPayer_category_header_txv,mPayMethod_header_txv,mAmount_header_txv,mTotal_amtSum;
	Button mPlus_btn;
	ListView mListview;
	DatabaseHandler db;
	 ArrayList<Expense_Indent_Model> indent_list=new ArrayList<Expense_Indent_Model>();
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.indent_expense);
	        db = new DatabaseHandler(this); 
//		      creating methods to write code       
		        getIds();
		        getList_Fm_DB();
		        mPlusBtn_Click();
	 }
	 @Override
	   	public void onBackPressed() {
	   			// do something on back.
//	   		Toast.makeText(getApplicationContext(), "go back", Toast.LENGTH_SHORT).show();
	       	Intent i=new Intent(Indent_Expense.this,Home.class);
	   		startActivity(i);
	   		finish();
	   	return;
	   		}	

	private void getList_Fm_DB() {
		// TODO Auto-generated method stub
		db = new DatabaseHandler(this);
		// Reading all contacts
        
        List<Expense_Indent_Model> list = db.getAll_Exp_Indent_List();       
      System.out.println("Reading size of list "+ list.size()); 
      float total_sum_amt_float =0;
        for (Expense_Indent_Model cn : list) {	        	
         
            String date = cn.getDate_Exp_Indent();
            String payer=cn.getPayer_Exp_Indent();
            String category=cn.getCategory_Exp_Indent();
            String pay_method=cn.getPay_Method_Exp_Indent();
            String amt=cn.getAmount_Exp_Indent();          
//        convert string to float
            total_sum_amt_float += Float.valueOf(amt);
            Expense_Indent_Model appadd = new Expense_Indent_Model(date, payer,category,pay_method,amt);
          
		indent_list.add(appadd);             
        }
//        convert float value to String 
        String total_sum_amt_resul= "Rs "+String.format("%.2f", total_sum_amt_float);
	     
        mTotal_amtSum.setText(total_sum_amt_resul);
        Indent_Expense_Adapter adapter = new
        		Indent_Expense_Adapter(Indent_Expense.this, R.layout.income_expense_row,indent_list);
	   
		mListview.setAdapter(adapter);
		
		mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                                int position, long id) {
	        	Expense_Indent_Model indent_model = (Expense_Indent_Model) parent.getItemAtPosition(position);
	           
	            
	        	
	        	 
//	        	 Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
//	             Date currentLocalTime = cal.getTime();
//	             DateFormat date = new SimpleDateFormat("HH:mm a"); 
//	    // you can get seconds by adding  "...:ss" to it
//	       date.setTimeZone(TimeZone.getTimeZone("GMT+1:00")); 
//	                 String localTime = date.format(currentLocalTime); 
//	        	 Toast.makeText(getApplicationContext(), ""+localTime, Toast.LENGTH_LONG).show();

	        }
	    }); 
	}

	private void mPlusBtn_Click() {
		// TODO Auto-generated method stub
		mPlus_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Indent_Expense.this,Indent_Expense_Description.class);
				startActivity(i);
			}
		});
	}

	private void getIds() {
		// TODO Auto-generated method stub
		mDate_header_txv=(TextView)findViewById(R.id.date_header_exp_indent);
		mPayer_category_header_txv=(TextView)findViewById(R.id.payer_cat_header_exp_indent);
		
		mPayMethod_header_txv=(TextView)findViewById(R.id.pay_method_header_exp_indent);
		mAmount_header_txv=(TextView)findViewById(R.id.amt_header_exp_indent);
		mListview=(ListView)findViewById(R.id.listview_exp_indent);
		mPlus_btn=(Button)findViewById(R.id.add_btn_exp_indent);
		mTotal_amtSum=(TextView)findViewById(R.id.sum_amt_exp_indent);
	}
}
