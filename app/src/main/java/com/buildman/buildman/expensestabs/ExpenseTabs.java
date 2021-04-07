package com.buildman.buildman.expensestabs;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.buildman.buildman.activity.Home;
import com.buildman.buildman.activity.Login;
import com.buildman.buildman.activity.R;
import com.buildman.buildman.activity.UserSites;

public class ExpenseTabs extends TabActivity {
    /** Called when the activity is first created. */
	 TabHost tabHost;
	
	 public final static int INCOME = 0;
	 public final static int EXPENSE = 1;
	private static final int RAISE_INDENT_EXPENSE = 2;
	 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expensetabs);
        android.app.ActionBar bar=getActionBar();
//      bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#dca905")));
//      bar.setDisplayShowHomeEnabled(false);
      getActionBar().setTitle(Html.fromHtml("<font color='#6f6f6f'>Expenses </font>"));
     tabHost = getTabHost();
//        ActionBar bar=getActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#848484")));
        // Tab for INCOME
        TabSpec incomespec = tabHost.newTabSpec("INCOME");
        // setting Title and Icon for the Tab
        incomespec.setIndicator("INCOME");
        Intent incomeIntent = new Intent(this, Income_Expense.class);
        incomespec.setContent(incomeIntent);
         
        // Tab for EXPENSE
        TabSpec expensespec = tabHost.newTabSpec("EXPENSE");        
        expensespec.setIndicator("EXPENSE");
        Intent expIntent = new Intent(this, Expense_Expense.class);
        expensespec.setContent(expIntent);
        
        // Tab for Raise Indent
        TabSpec indentspec = tabHost.newTabSpec("INDENT");        
        indentspec.setIndicator("INDENT");
        Intent indentIntent = new Intent(this, Indent_Expense.class);
        indentspec.setContent(indentIntent);
      
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(incomespec); // Adding INCOME tab
        tabHost.addTab(expensespec); // Adding EXPENSE tab
        tabHost.addTab(indentspec); // Adding Indent tab
        
       
// styling tabs strip before selection after selection
        
        TabWidget widget = tabHost.getTabWidget();
        for(int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);

            // Look for the title view to ensure this is an indicator and not a divider.
            TextView tv = (TextView)v.findViewById(android.R.id.title);
            tv.setTextSize(getResources().getDimension(R.dimen.tab_textview));
            if(tv == null) {
                continue;
            }
            v.setBackgroundResource(R.drawable.tab_indicator);
        }

        System.out.println(""+tabHost.getCurrentTab());
      
        int type = 0;
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("from_expense")) {
                type = getIntent().getExtras().getInt("from_expense");
                switch (type) {
                case INCOME:
                	tabHost.setCurrentTab(0);
                case EXPENSE:
                	tabHost.setCurrentTab(1);
                case RAISE_INDENT_EXPENSE:
                	tabHost.setCurrentTab(2);
               
                default:
                	tabHost.setCurrentTab(1);
                }
                if(type==0){
                	tabHost.setCurrentTab(0);
                }else if (type==1) {
                	tabHost.setCurrentTab(1);
				}else if (type==2) {
					tabHost.setCurrentTab(2);
				}
            }
        }
        

    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.expenses_menu, menu);
       
        return super.onCreateOptionsMenu(menu);
    }
 
  
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        super.onOptionsItemSelected(item);
 
        switch(item.getItemId()){
        case R.id.home_expense_menu:
            break;        

           case R.id.house_expense_menu:              
           	Intent i_home=new Intent(ExpenseTabs.this,Home.class);
             	 startActivity(i_home);
             	  finish();
		           break;
		           
          
		           
           case R.id.switchsite_expense_menu:                
           	Intent i_switchsite=new Intent(ExpenseTabs.this,UserSites.class);
             	 startActivity(i_switchsite);
             	  finish();
		           break;
		           
       
	        case R.id.logout_expense_menu:
	        	Intent i=new Intent(ExpenseTabs.this,Login.class);
	        	startActivity(i);
	        	  finish();

		           break;
           case R.id.syn_expense_menu:   	
                   	
               break;

          
            }
            return true;
 
    }  

	
}
