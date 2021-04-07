package com.buildman.buildman.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.buildman.buildman.activity.R;
import com.buildman.buildman.model.Expense_Income_Model;

public class Income_Expense_Adapter extends ArrayAdapter<Expense_Income_Model> {
	Context context;
	  private LayoutInflater vi;
	  public  ArrayList<Expense_Income_Model> income_list;
	    
public Income_Expense_Adapter(Context context, int textViewResourceId,
		ArrayList<Expense_Income_Model> income_list){


super(context, textViewResourceId, income_list);
this.context = context;
this.income_list=income_list;

}    

private class ViewHolder
{
TextView account_name,symbol,amount_type,current_balance;

}

@Override
public View getView(int position, View convertView, ViewGroup parent)
{
	vi = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
ViewHolder holder = null;

Log.v("ConvertView", String.valueOf(position));

if (convertView == null)
{
convertView = vi.inflate(R.layout.income_expense_row, null,true);

holder = new ViewHolder();
holder.account_name = (TextView) convertView.findViewById(R.id.account_name_income_expense_row);
holder.symbol = (TextView) convertView.findViewById(R.id.issues_symbol_income_expense_row);
holder.amount_type = (TextView) convertView.findViewById(R.id.amount_type_income_expense_row);
holder.current_balance=(TextView)convertView.findViewById(R.id.current_balance_income_expense_row);

convertView.setTag(holder);
}
else
{
holder = (ViewHolder) convertView.getTag();
}
Expense_Income_Model app = income_list.get(position);
Log.e("get contractorll", ""+app.getDate());
Log.e("get qtyssssss", ""+app.getCategory());
Log.e("get current stockkkkk", ""+app.getPayer());
Log.e("get requiredd stockkkkk", ""+app.getPay_Method());

holder.account_name.setText( app.getAccount());
holder.amount_type.setText(app.getAmount_Type());
holder.current_balance.setText(app.getCurrentBalance());

if(app.getCurrentBalance().equals("1000.00")){
	holder.symbol.setText("!");
}else{
	holder.symbol.setText("");	
}
	
convertView.setBackgroundResource(R.color.white);
//if ( position % 2 == 0)
//{
//	convertView.setBackgroundResource(R.color.gray);
//}
//else{
//	convertView.setBackgroundResource(R.color.white);
//}
return convertView;
}


}