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
import com.buildman.buildman.model.Manpower_Model;

public class Contract_Manpower_Adapter extends ArrayAdapter<Manpower_Model> {
	Context context;
	  private LayoutInflater vi;
	  public  ArrayList<Manpower_Model> contract_list;
	    
public Contract_Manpower_Adapter(Context context, int textViewResourceId,
		ArrayList<Manpower_Model> mContract_list){


super(context, textViewResourceId, mContract_list);
this.context = context;
this.contract_list=mContract_list;

}    

private class ViewHolder
{
TextView contractor_name,work_name,total_qty,qty_completed,total_qty_units,qty_completed_units;

}

@Override
public View getView(int position, View convertView, ViewGroup parent)
{
	vi = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
ViewHolder holder = null;

Log.v("ConvertView", String.valueOf(position));

if (convertView == null)
{
convertView = vi.inflate(R.layout.contract_manpower_row, null,true);

holder = new ViewHolder();
holder.contractor_name = (TextView) convertView.findViewById(R.id.contractname_contract_manpower_row);
holder.work_name = (TextView) convertView.findViewById(R.id.workname_contract_manpower_row);
holder.total_qty = (TextView) convertView.findViewById(R.id.totalqty_contract_manpower_row);
holder.qty_completed = (TextView) convertView.findViewById(R.id.qtycompleted_contract_manpower_row);
holder.total_qty_units = (TextView) convertView.findViewById(R.id.units_totalqty_contract_manpower_row);
holder.qty_completed_units = (TextView) convertView.findViewById(R.id.units_qtycompleted_contract_manpower_row);

convertView.setTag(holder);
//convertView.setTag(R.id.contractor_matl_used_row,     holder.contractor);
//convertView.setTag(R.id.qty_matl_used_row,     holder.qty);
//convertView.setTag(R.id.currentstock_matl_used_row,     holder.labour_type);

}
else
{
holder = (ViewHolder) convertView.getTag();
}
Manpower_Model app = contract_list.get(position);


holder.contractor_name.setText( app.getContractor_Name_MP_CONT());
holder.work_name.setText(app.getWork_Loc_Name_MP_CONT());
holder.total_qty.setText(app.getTotal_Qty_MP_CONT());
holder.qty_completed.setText(app.getQty_Completed_MP_CONT());
holder.total_qty_units.setText(app.getQty_Units_MP_CONT());
holder.qty_completed_units.setText("");
System.out.println("work name"+app.getWork_Loc_Name_MP_CONT());
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