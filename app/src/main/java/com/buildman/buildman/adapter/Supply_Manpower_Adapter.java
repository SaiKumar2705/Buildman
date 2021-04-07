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

public class Supply_Manpower_Adapter extends ArrayAdapter<Manpower_Model> {
	Context context;
	  private LayoutInflater vi;
	  public  ArrayList<Manpower_Model> supply_list;
	    
public Supply_Manpower_Adapter(Context context, int textViewResourceId,
		ArrayList<Manpower_Model> mSupply_list){


super(context, textViewResourceId, mSupply_list);
this.context = context;
this.supply_list=mSupply_list;

}    

private class ViewHolder
{
TextView contractor,labour_type_name,total_till_date_qty,today_qty;

}

@Override
public View getView(int position, View convertView, ViewGroup parent)
{
	vi = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
ViewHolder holder = null;

Log.v("ConvertView", String.valueOf(position));

if (convertView == null)
{
convertView = vi.inflate(R.layout.supply_manpower_row, null,true);

holder = new ViewHolder();
holder.contractor = (TextView) convertView.findViewById(R.id.contractor_supply_manpower_row);
holder.labour_type_name = (TextView) convertView.findViewById(R.id.labour_type_supply_manpower_row);
holder.total_till_date_qty = (TextView) convertView.findViewById(R.id.total_till_date_qty_supply_manpower_row);
holder.today_qty = (TextView) convertView.findViewById(R.id.today_qty_supply_manpower_row);

convertView.setTag(holder);
//convertView.setTag(R.id.contractor_matl_used_row,     holder.contractor);
//convertView.setTag(R.id.qty_matl_used_row,     holder.qty);
//convertView.setTag(R.id.currentstock_matl_used_row,     holder.labour_type);

}
else
{
holder = (ViewHolder) convertView.getTag();
}
Manpower_Model app = supply_list.get(position);


holder.contractor.setText( app.getContractor_Name_MP_SUP());
holder.labour_type_name.setText(app.getLabourType_Name_MP_SUP());
holder.total_till_date_qty.setText(app.getTotalTillDate_Qty_MP_SUP());
holder.today_qty.setText(app.getEntered_Qty_MP_SUP());

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