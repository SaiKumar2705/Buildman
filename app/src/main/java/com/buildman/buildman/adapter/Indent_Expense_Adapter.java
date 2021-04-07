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
import com.buildman.buildman.model.Expense_Indent_Model;

public class Indent_Expense_Adapter extends ArrayAdapter<Expense_Indent_Model> {
	Context context;
	  private LayoutInflater vi;
	  public  ArrayList<Expense_Indent_Model> indent_list;
	    
public Indent_Expense_Adapter(Context context, int textViewResourceId,
		ArrayList<Expense_Indent_Model> indent_list){


super(context, textViewResourceId, indent_list);
this.context = context;
this.indent_list=indent_list;

}    

private class ViewHolder
{
TextView date,payer,category,pay_method,amt;

}

@Override
public View getView(int position, View convertView, ViewGroup parent)
{
	vi = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
ViewHolder holder = null;

Log.v("ConvertView", String.valueOf(position));

if (convertView == null)
{
convertView = vi.inflate(R.layout.expense_expense_row, null,true);

holder = new ViewHolder();
holder.date = (TextView) convertView.findViewById(R.id.date_income_expense_row);
holder.payer = (TextView) convertView.findViewById(R.id.payer_income_expense_row);
holder.category=(TextView)convertView.findViewById(R.id.category_income_expense_row);
holder.pay_method = (TextView) convertView.findViewById(R.id.pay_method_income_expense_row);
holder.amt = (TextView) convertView.findViewById(R.id.amt_income_expense_row);

convertView.setTag(holder);


}
else
{
holder = (ViewHolder) convertView.getTag();
}
Expense_Indent_Model app = indent_list.get(position);

Log.e("get caterogy of indent", ""+app.getCategory_Exp_Indent());
Log.e("get current stockkkkk", ""+app.getPayer_Exp_Indent());
Log.e("get requiredd stockkkkk", ""+app.getPay_Method_Exp_Indent());

holder.date.setText( app.getDate_Exp_Indent());
holder.payer.setText(app.getPayer_Exp_Indent());
holder.category.setText(app.getCategory_Exp_Indent());
holder.pay_method.setText(app.getPay_Method_Exp_Indent());
holder.amt.setText(app.getAmount_Exp_Indent());

if ( position % 2 == 0)
{
	convertView.setBackgroundResource(R.color.gray);
}
else{
	convertView.setBackgroundResource(R.color.white);
}
return convertView;
}



}
